package netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import io.netty.handler.codec.TooLongFrameException;

import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by asus on 03.12.2016.
 */
public class MyBateDecodor extends ByteToMessageDecoder {

    public MyBateDecodor() {
    }


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("readerIndex = "+ in.readerIndex()+"    readableBytes = "+in.readableBytes());

        ByteBuf msg =  in.retainedSlice(in.readerIndex(), in.readableBytes());
        in.skipBytes(in.readableBytes());

        System.out.println("readerIndex = "+ in.readerIndex()+"    readableBytes = "+in.readableBytes());

        System.out.println(msg.toString(Charset.forName("windows-1251")));
    }

}

