package protocol.net;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;

import java.nio.charset.Charset;

/**
 * Created by asus on 04.12.2016.
 */
class NetServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final SslContext sslCtx;
    private  Charset netCharset;
    public NetServerChannelInitializer(SslContext sslCtx, Charset netCharset ) {
        this.netCharset = netCharset;
        this.sslCtx = sslCtx;
    }

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        if (sslCtx != null) {
            pipeline.addLast(sslCtx.newHandler(ch.alloc()));
        }
        pipeline.addLast(new LoggingHandler(LogLevel.INFO));

        pipeline.addLast(new BateToCommandDecoder(netCharset));
        pipeline.addLast(new StringEncoder(netCharset));
        pipeline.addLast(new CommandToStringEncoder());
        pipeline.addLast(new CommandHandler());
    }
}
