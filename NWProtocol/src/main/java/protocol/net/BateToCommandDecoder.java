package protocol.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.TooLongFrameException;
import io.netty.util.ByteProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.commands.AbstractCommand;
import protocol.commands.Parser;

import java.nio.charset.Charset;
import java.util.List;


//@ChannelHandler.Sharable
class BateToCommandDecoder extends ByteToMessageDecoder {
    private static final Logger logger = LoggerFactory.getLogger(BateToCommandDecoder.class);

    private final Parser parser = new Parser();
    private final Charset charset;

    private final LineBasedFrameDecoder lineBasedDecoder;

    public BateToCommandDecoder(Charset charset) {
        if (charset == null) {
            throw new NullPointerException("charset");
        }
        this.charset = charset;

        lineBasedDecoder = new LineBasedFrameDecoder(32384, true, false);

    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        logger.info("decode readerIndex({}) readableBytes({})", in.readerIndex(), in.readableBytes());
        Object decoded = decode(ctx, in);
        if (decoded != null) {
            out.add(decoded);
            //in.readByte();
            //in.discardReadBytes();
            in.skipBytes(in.readableBytes());
            logger.info("skipBytes readerIndex({}) readableBytes({})", in.readerIndex(), in.readableBytes());
            in.release();
        }
    }

    //TODO сейчас работаем на строках, воможно надо работать с байтами
    private Object decode(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {
        AbstractCommand cmd = null;



        ByteBuf msg = lineBasedDecoder_decode(ctx, buffer);
        while( msg != null){
            String msgString = msg.toString(charset);

            logger.info("msgString= " + msgString);
            msg = lineBasedDecoder_decode(ctx, buffer);
        }





//        ByteBuf msg = buffer.retainedSlice(buffer.readerIndex(), buffer.readableBytes());
//
//        // версия со строками
//        String msgString = msg.toString(charset);
//        // получить имя команды
//        String cammandName = parser.getCammandName(msgString);
//        //построить команду
//        if (cammandName != null) {
//            cmd = parser.tryParseCommand(cammandName, msgString);
//        }
        return cmd;
    }



    //-------------------------------------------------------------------
    // from class LineBasedFrameDecoder
    //-------------------------------------------------------------------

    /** Maximum length of a frame we're willing to decode.  */
    private final int maxLength = 32384;
    /** Whether or not to throw an exception as soon as we exceed maxLength. */
    private final boolean failFast = true;
    private final boolean stripDelimiter = false;
    /** True if we're discarding input because we're already over maxLength.  */
    private boolean discarding;
    private int discardedBytes;
    //Create a frame out of the {@link ByteBuf} and return it.
    //@param   ctx             the {@link ChannelHandlerContext} which this {@link ByteToMessageDecoder} belongs to
    //@param   buffer          the {@link ByteBuf} from which to read data
    //@return  frame           the {@link ByteBuf} which represent the frame or {@code null} if no frame could   be created.
    protected ByteBuf lineBasedDecoder_decode(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {
        final int eol = findEndOfLine(buffer);
        if (!discarding) {
            if (eol >= 0) {
                final ByteBuf frame;
                final int length = eol - buffer.readerIndex();
                final int delimLength = buffer.getByte(eol) == '\r'? 2 : 1;

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
                final int delimLength = buffer.getByte(eol) == '\r'? 2 : 1;
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
        ctx.fireExceptionCaught(
                new TooLongFrameException(
                        "frame length (" + length + ") exceeds the allowed maximum (" + maxLength + ')'));
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
        return i;
    }

}
