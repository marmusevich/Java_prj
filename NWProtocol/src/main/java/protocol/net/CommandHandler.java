package protocol.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.Server;
import protocol.commands.AbstractCommand;

/**
 * Created by asus on 04.12.2016.
 */
class CommandHandler extends SimpleChannelInboundHandler<AbstractCommand>{
    private static final Logger logger = LoggerFactory.getLogger(CommandHandler.class);

    @Override
    public void channelRead0(ChannelHandlerContext ctx, AbstractCommand сommand) throws Exception {
        сommand.setChannelHandlerContext( ctx );
        Server.getCommandServer().addCommandToProcess(сommand);

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception
    {
        logger.info("channelInactive {}", ctx.pipeline().channel().remoteAddress().toString());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception
    {
        logger.info("channelActive {}", ctx.pipeline().channel().remoteAddress().toString());
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    }

    //для теста
    @Override
    public void finalize() {
        logger.info("finalize");
    }

}
