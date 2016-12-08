package protocol.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import protocol.commands.AbstractCommand;

import java.util.List;

/**
 * Created by asus on 04.12.2016.
 */
public class CommandToStringEncoder extends MessageToMessageEncoder<AbstractCommand> {

    @Override
    protected void encode(ChannelHandlerContext ctx, AbstractCommand сommand, List<Object> out) throws Exception {
        String[] res = сommand.getResult();
        if (res != null && res.length > 0) {
            for (String str : res) {
                ctx.write(str + "\n\r");
            }
        }
    }
}
