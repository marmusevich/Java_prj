package heatMeterOTEC.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import heatMeterOTEC.Server;
import heatMeterOTEC.commands.AbstractCommand;

import java.nio.charset.Charset;
import java.util.List;
import heatMeterOTEC.commands.*;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;


/**
 * получатьль команд, ставит на выполнение
 */
class CommandHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf buf = (ByteBuf)msg;
        String json = buf.toString(Charset.forName("utf-8"));
        System.out.println("json = " + json);
        AbstractCommand сommand = Parser.tryParseCommand(json);


        if (сommand != null) {
            System.out.println("сommand ->  " + сommand.toString());
            сommand.setChannelHandlerContext(ctx);
            Server.getCommandServer().addCommandToProcess(сommand);
        }

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}


//    class CommandHandler extends SimpleChannelInboundHandler< List<Object> > {
//    private static final Logger logger = LoggerFactory.getLogger(CommandHandler.class);
//
//    public CommandHandler() {
//    }
//
//    @Override
//    public void channelRead0(ChannelHandlerContext ctx, List<Object> сommand) throws Exception {
//        //if (сommand != null) {
//            //сommand.setChannelHandlerContext(ctx);
//            //Server.getCommandServer().addCommandToProcess(сommand);
//        //}
//
//        for ( Object o : сommand ) {
//            String json = o.toString();
//
//            System.out.println("json = " + json);
//
//            AbstractCommand newCommand = Parser.tryParseCommand(o.toString());
//            System.out.println("newCommand ->  " + newCommand.toString());
//
//
//        }
//    }
//
//    @Override
//    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//        logger.info("channelInactive {}", ctx.pipeline().channel().remoteAddress().toString());
//
//    }
//
//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        logger.info("channelActive {}", ctx.pipeline().channel().remoteAddress().toString());
//    }
//
//
//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx) {
//        ctx.flush();
//    }
//
//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
//    }
//
//    //для теста
//    @Override
//    public void finalize() {
//        logger.info("finalize");
//    }
//
//}
