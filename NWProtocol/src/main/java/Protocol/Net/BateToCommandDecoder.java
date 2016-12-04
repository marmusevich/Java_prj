package Protocol.Net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by asus on 04.12.2016.
 */
class BateToCommandDecoder extends ByteToMessageDecoder {

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
        }
    }

    private Object decode(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {

        System.out.println("readerIndex = " + buffer.readerIndex() + "    readableBytes = " + buffer.readableBytes());

        ByteBuf msg = buffer.retainedSlice(buffer.readerIndex(), buffer.readableBytes());
        buffer.skipBytes(buffer.readableBytes());

        System.out.println("readerIndex = " + buffer.readerIndex() + "    readableBytes = " + buffer.readableBytes());

        System.out.println(msg.toString(charset));

        return null;
    }


}
