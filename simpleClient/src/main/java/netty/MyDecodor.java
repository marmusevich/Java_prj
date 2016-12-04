package netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetAddress;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;

@Sharable
public class MyDecodor  extends MessageToMessageDecoder<String> {

    int ii = 0;
    public MyDecodor() {
    }


    @Override
    protected void decode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
        System.out.println(msg +" - "+ ii);
        ii++;
        //out.add();
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Welcome to " + InetAddress.getLocalHost().getHostName());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Bye " + InetAddress.getLocalHost().getHostName());
    }


}
