package protocol.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.Server;
import protocol.commands.AbstractCommand;

/**
 * Created by asus on 04.12.2016.
 */
class CommandHandler extends SimpleChannelInboundHandler<AbstractCommand> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, AbstractCommand сommand) throws Exception {
        Server.getCommandExecutorContext().addCommandToProcess(сommand.setChannelHandlerContext( ctx ));

    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    }
}
