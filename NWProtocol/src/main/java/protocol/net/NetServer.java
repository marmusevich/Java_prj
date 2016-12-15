package protocol.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

/**
 *
 */
public final class NetServer {

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private SslContext sslCtx;

    private static final Logger logger = LoggerFactory.getLogger(NetServer.class);


    /**
     * иницилизировать, количество потоков на усмотрение системы
     */
    public NetServer() {
        this(0,0);
    }

    /**
     * иницилизировать, количество потоков задать
     */
    public NetServer(int bossThreads, int workerThreads) {
        if(bossThreads == 0)
            bossGroup = new NioEventLoopGroup();
        else
            bossGroup = new NioEventLoopGroup(bossThreads);

        if(workerThreads == 0)
            workerGroup = new NioEventLoopGroup();
        else
            workerGroup = new NioEventLoopGroup(workerThreads);
    }

    public void ConfigureSSL(boolean SSL) throws Exception {
        // Configure SSL.
        if (SSL) {
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
        } else {
            sslCtx = null;
        }
    }

    public void start(int PORT, Charset netCharset) throws Exception {
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    //.option(ChannelOption.SO_BACKLOG,128) // количество одновременных подключени
//                    .childOption(ChannelOption.SO_TIMEOUT,128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true) // проверить а соеденение активно ли?
                    .childHandler(new NetServerChannelInitializer(sslCtx, netCharset ));

//            int i = 10 / 0;

            b.bind(PORT).sync().channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public void stop() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}