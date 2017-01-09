package protocol.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;
import io.netty.util.ByteProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.commands.AbstractCommand;
import protocol.commands.Parser;

import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


//@ChannelHandler.Sharable
class BateToCommandDecoder extends ByteToMessageDecoder {
    private static final Logger logger = LoggerFactory.getLogger(BateToCommandDecoder.class);

    private final Charset charset;
    private ConcurrentHashMap<ChannelHandlerContext, CommandStateDescriptor> decodetCommands;

    public BateToCommandDecoder(Charset charset, ConcurrentHashMap<ChannelHandlerContext, CommandStateDescriptor> decodetCommands) {
        if (charset == null) {
            throw new NullPointerException("charset");
        }
        this.charset = charset;
        this.decodetCommands = decodetCommands;
    }
    //todo течет буфер на большой нагрузке
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        Object decoded = decode(ctx, in);
        if (decoded != null) {
            out.add(decoded);
            // сбросить прочитанное
            //in.discardReadBytes();
            //logger.info("do in.refCnt() = {}", in.refCnt());
            //in.release(in.refCnt());
        }
    }

    private Object decode(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {
        AbstractCommand cmd = null;
        try {
            String msgString = lineBasedDecoder_decode(ctx, buffer).toString(charset);

            //todo наверное в цикле запрашивать строки из потока

            //проверить есть ли в decodetCommands  конкретный ctx
            if(!decodetCommands.containsKey(ctx)){
                // добавить новую
                getNewCommand(ctx, msgString);
            }
            else{
                CommandStateDescriptor csd = decodetCommands.get(ctx);
                switch (csd.state) {
                    case Empty:
                        // удалить из decodetCommands
                        decodetCommands.remove(ctx);
                        break;
                    case FirstResponseResive:
                        //- первый ответ отправлен, пеоейти к четнию количество строк, обновить decodetCommands
                        try{
                            int rowCount = Integer.parseInt(msgString);
                            csd.rowCount = rowCount;
                            csd.state = CommandStateDescriptor.CommandState.CommandlDataCountReaded;
                            decodetCommands.replace(ctx, csd);
                        }
                        catch (Exception e)
                        {
                            //todo что то делать, ошибка распарсевания
                        }
                        break;
                    case CommandlDataCountReaded:
                        //- читаем строки, если прочитали все обновить decodetCommands

                        csd.commandData += msgString;
                        csd.currentRowCount ++;
                        if(csd.currentRowCount >= csd.rowCount){
                            csd.state = CommandStateDescriptor.CommandState.CommandlDataReaded;
                            decodetCommands.replace(ctx, csd);
                            // и перейти к следущему пункту
                        }
                        else{
                            // не все прочитали
                            decodetCommands.replace(ctx, csd);
                            break;
                        }
                    case CommandlDataReaded:
                        //перейти к выполнению команды
                        cmd = Parser.tryParseCommand(csd.commandName, csd.commandData);
                        if(cmd != null){
                            csd.state = CommandStateDescriptor.CommandState.CommandExec;
                            decodetCommands.replace(ctx, csd);
                        }
                        break;
                    case CommandExec:
                        // здесь этого не должно быть, скорей всего это новая команда
                        // удалить из decodetCommands
                        decodetCommands.remove(ctx);
                        // добавить новую
                        getNewCommand(ctx, msgString);
                        break;
                }
            }
        } finally {
            //logger.info("do msg.refCnt() = {}", msg.refCnt());
            //msg.release();
            //logger.info("release msg.refCnt() = {}", msg.refCnt());
        }
        return cmd;
    }

    private void getNewCommand(ChannelHandlerContext ctx, String msgString) {
        //ели нет,( получить имя команды
        String cammandName = Parser.getCammandName(msgString);
        if (cammandName != null) {
            //значит добавить, отправить первый ответ
            String firstResponse = Parser.getFirstResponse(cammandName);
            if(firstResponse != "" ){
                CommandStateDescriptor csd = new CommandStateDescriptor();
                csd.state = CommandStateDescriptor.CommandState.FirstResponseResive;
                csd.commandName = cammandName;
                decodetCommands.putIfAbsent(ctx, csd);
                ctx.write(firstResponse );
            }
        }
    }

    private Object decode_old(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {
        AbstractCommand cmd = null;
        ByteBuf tmp = buffer.slice();
        ByteBuf msg = lineBasedDecoder_decode(ctx, tmp);
        try {
            String msgString = "";
            while (msg != null) {
                msgString = msgString + msg.toString(charset);
                // получить имя команды
                String cammandName = Parser.getCammandName(msgString);
                //ctx.write(cammandName + "\n\r"); - НЕ РАБОТАЕТ - НЕТ ПРЕОБРОЗОВАНИЯ ИЗ КОМАНДЫ
                //построить команду
                if (cammandName != null) {
                    cmd = Parser.tryParseCommand(cammandName, msgString);
                }
                if (cmd != null) { // значить все хорошо, надо скипнуть байты в исходном
                    buffer.readerIndex(tmp.readerIndex());
                    break;
                }
                // команды нет, продолжаем
                msg = lineBasedDecoder_decode(ctx, tmp);
            }
            // байты закончились и команды нет, ждем следующих байтов
        } finally {
            //logger.info("do msg.refCnt() = {}", msg.refCnt());
            msg.release();
//            logger.info("release msg.refCnt() = {}", msg.refCnt());
        }
        return cmd;
    }


    //-------------------------------------------------------------------
    // from class LineBasedFrameDecoder
    //-------------------------------------------------------------------

    //максимальная длина одного сообщения
    private final int maxLength = 32384;
    // прерывание сразу давать или когда все скипнули, false - потом
    private final boolean failFast = false;
    // не добовлять разделитель в выходную строку
    private final boolean stripDelimiter = false;
    // пропускаем байты
    private boolean discarding = false;
    // количество пропускаемых
    private int discardedBytes = 0;

    /**
     * читает одну строку
     * @param ctx
     * @param buffer
     * @return
     * @throws Exception
     */
    protected ByteBuf lineBasedDecoder_decode(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {
        final int eol = findEndOfLine(buffer);
        if (!discarding) {
            if (eol >= 0) {
                final ByteBuf frame;
                final int length = eol - buffer.readerIndex();
                final int delimLength = buffer.getByte(eol) == '\r' ? 2 : 1;

                if (length > maxLength) {
                    buffer.readerIndex(eol + delimLength);
                    fail(ctx, length);
                    return null;
                }

                if (stripDelimiter) {
                    frame = buffer.readRetainedSlice(length);
                    buffer.skipBytes(delimLength);
                } else {
                    frame = buffer.readRetainedSlice(length + delimLength);
                }

                return frame;
            } else {
                final int length = buffer.readableBytes();
                if (length > maxLength) {
                    discardedBytes = length;
                    buffer.readerIndex(buffer.writerIndex());
                    discarding = true;
                    if (failFast) {
                        fail(ctx, "over " + discardedBytes);
                    }
                }
                return null;
            }
        } else {
            if (eol >= 0) {
                final int length = discardedBytes + eol - buffer.readerIndex();
                final int delimLength = buffer.getByte(eol) == '\r' ? 2 : 1;
                buffer.readerIndex(eol + delimLength);
                discardedBytes = 0;
                discarding = false;
                if (!failFast) {
                    fail(ctx, length);
                }
            } else {
                discardedBytes += buffer.readableBytes();
                buffer.readerIndex(buffer.writerIndex());
            }
            return null;
        }
    }

    private void fail(final ChannelHandlerContext ctx, int length) {
        fail(ctx, String.valueOf(length));
    }

    private void fail(final ChannelHandlerContext ctx, String length) {
        //здесь лог вместо прерывания
        logger.error("frame length (" + length + ") exceeds the allowed maximum (" + maxLength + ")");
        ctx.fireExceptionCaught(
                new TooLongFrameException(
                        "frame length (" + length + ") exceeds the allowed maximum (" + maxLength + ")"));
    }

    /**
     * Returns the index in the buffer of the end of line found.
     * Returns -1 if no end of line was found in the buffer.
     */
    private static int findEndOfLine(final ByteBuf buffer) {
        int i = buffer.forEachByte(ByteProcessor.FIND_LF);
        if (i > 0 && buffer.getByte(i - 1) == '\r') {
            i--;
        }
        //logger.info("do buffer.refCnt() = {}", buffer.refCnt());
//        buffer.release();
//        logger.info("release buffer.refCnt() = {}", buffer.refCnt());
        return i;
    }

}
