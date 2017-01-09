package protocol.net_old;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

/**
 * Created by asus on 04.12.2016.
 */
class old_NetServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    private static final Logger logger = LoggerFactory.getLogger(old_NetServerChannelInitializer.class);

    private final SslContext sslCtx;
    private  Charset netCharset;
    public old_NetServerChannelInitializer(SslContext sslCtx, Charset netCharset ) {
        this.netCharset = netCharset;
        this.sslCtx = sslCtx;
    }

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        if (sslCtx != null) {
            pipeline.addLast(sslCtx.newHandler(ch.alloc()));
        }
        //pipeline.addLast(new LoggingHandler(LogLevel.ERROR));

        pipeline.addLast(new old_BateToCommandDecoder(netCharset));
        pipeline.addLast(new StringEncoder(netCharset));
        pipeline.addLast(new old_CommandToStringEncoder());
        pipeline.addLast(new old_CommandHandler());
    }


    @Override
    public void finalize() {
        logger.trace("finalize");
    }
}
