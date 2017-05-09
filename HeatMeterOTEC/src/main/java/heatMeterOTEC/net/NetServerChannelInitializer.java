package heatMeterOTEC.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.concurrent.ConcurrentHashMap;

/**
 * настройка обработчиков данных
 */
class NetServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    private static final Logger logger = LoggerFactory.getLogger(NetServerChannelInitializer.class);
    private final SslContext sslCtx;
    ConcurrentHashMap<ChannelHandlerContext, CommandStateDescriptor> decodetCommands;
    private Charset netCharset;

    public NetServerChannelInitializer(SslContext sslCtx, Charset netCharset, ConcurrentHashMap<ChannelHandlerContext, CommandStateDescriptor> decodetCommands) {
        this.netCharset = netCharset;
        this.sslCtx = sslCtx;
        this.decodetCommands = decodetCommands;
    }

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        if (sslCtx != null) {
            pipeline.addLast(sslCtx.newHandler(ch.alloc()));
        }
        pipeline.addLast(new LoggingHandler(LogLevel.DEBUG));

        pipeline.addLast("1", new BateToCommandDecoder(netCharset, decodetCommands));
        pipeline.addLast("2", new CommandToBateEncoder(netCharset));
        pipeline.addLast("3", new CommandHandler(decodetCommands));
    }


    @Override
    public void finalize() {
        logger.trace("finalize");
    }
}
