package Protocol.Net;

import Protocol.Commands.AbstractCommand;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * Created by asus on 04.12.2016.
 */
public class CommandToBateEncoder extends MessageToMessageEncoder<AbstractCommand> {

    @Override
    protected void encode(ChannelHandlerContext ctx, AbstractCommand msg, List<Object> out) throws Exception {

    }
}
