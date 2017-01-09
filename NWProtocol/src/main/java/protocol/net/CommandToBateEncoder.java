package protocol.net;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.commands.AbstractCommand;

import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by asus on 09.01.2017.
 */
public class CommandToBateEncoder extends MessageToByteEncoder<AbstractCommand> {

    private static final Logger logger = LoggerFactory.getLogger(BateToCommandDecoder.class);

    private final Charset charset;

    public CommandToBateEncoder(Charset charset) {
        if (charset == null) {
            throw new NullPointerException("charset");
        }
        this.charset = charset;
    }




    @Override
    protected void encode(ChannelHandlerContext ctx, AbstractCommand сommand, ByteBuf out) throws Exception {
        // действия от типа команды, или строки распарсим, или апдей отправим

        // для строк
        ArrayList<String> res = сommand.getResult();
        if (res != null && !res.isEmpty()) {
            for (String str : res) {
                out.writeBytes(  ByteBufUtil.encodeString(ctx.alloc(), CharBuffer.wrap(str + "\n\r"), charset));
            }
        }
    }
}

