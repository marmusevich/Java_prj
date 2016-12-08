package protocol.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import protocol.commands.AbstractCommand;
import protocol.commands.Parser;

import java.nio.charset.Charset;
import java.util.List;


//@ChannelHandler.Sharable
class BateToCommandDecoder extends ByteToMessageDecoder {

    private final Parser parser = new Parser();
    private final Charset charset;


    public BateToCommandDecoder(Charset charset) {
        if (charset == null) {
            throw new NullPointerException("charset");
        }
        this.charset = charset;
    }


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        Object decoded = decode(ctx, in);
        if (decoded != null) {
            out.add(decoded);
            in.skipBytes(in.readableBytes());
        }
    }

    private Object decode(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {

        ByteBuf msg = buffer.retainedSlice(buffer.readerIndex(), buffer.readableBytes());

        //TODO сейчас работаем на строках, воможно надо работать с байтами
        // версия со строками
        String msgString = msg.toString(charset);

        // получить имя команды
        String cammandName = parser.getCammandName(msgString);

        //построить команду
        AbstractCommand cmd = null;
        if (cammandName != null) {
            cmd = parser.tryParseCommand(cammandName, msgString);
        }
        return cmd;
    }
}
