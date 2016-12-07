package protocol.net;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslContext;

import java.nio.charset.Charset;

/**
 * Created by asus on 04.12.2016.
 */
class NetServerChannelInitializer extends ChannelInitializer<SocketChannel> {

//    private static final BateToCommandDecoder DECODER_BATE_TO_COMMAND = new BateToCommandDecoder(Charset.forName("windows-1251"));
//    private static final StringEncoder ENCODER_STRING_TO_BATE = new StringEncoder(Charset.forName("windows-1251"));
//    private static final CommandToStringEncoder ENCODER_COMMAND_TO_STRING = new CommandToStringEncoder();
//    private static final CommandHandler SERVER_HANDLER = new CommandHandler();


    private final SslContext sslCtx;

    public NetServerChannelInitializer(SslContext sslCtx) {
        this.sslCtx = sslCtx;
    }

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        if (sslCtx != null) {
            pipeline.addLast(sslCtx.newHandler(ch.alloc()));
        }

        //pipeline.addLast(new LoggingHandler(LogLevel.INFO));

//        pipeline.addLast(DECODER_BATE_TO_COMMAND);
//
//        pipeline.addLast(ENCODER_STRING_TO_BATE);
//        pipeline.addLast(ENCODER_COMMAND_TO_STRING);
//
//        // and then business logic.
//        pipeline.addLast(SERVER_HANDLER);





        pipeline.addLast(new BateToCommandDecoder(Charset.forName("windows-1251")));
        pipeline.addLast(new StringEncoder(Charset.forName("windows-1251")));
        pipeline.addLast(new CommandToStringEncoder());
        pipeline.addLast(new CommandHandler());


    }


}
