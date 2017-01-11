package protocol.net;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.commands.AbstractCommand;
import protocol.commands.CommandUpdatePrg;

import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * преобразует результат команд в байты для отправки
 */
public class CommandToBateEncoder extends MessageToByteEncoder<Object> {

    private static final Logger logger = LoggerFactory.getLogger(BateToCommandDecoder.class);

    private final Charset charset;

    public CommandToBateEncoder(Charset charset) {
        if (charset == null) {
            throw new NullPointerException("charset");
        }
        this.charset = charset;
    }


    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        // действия от типа команды, или строки распарсим, или апдей отправим

        if (msg instanceof String) {
            String message = (String) msg;
            out.writeBytes(ByteBufUtil.encodeString(ctx.alloc(), CharBuffer.wrap(msg + "\n\r"), charset));
        } else if (msg instanceof AbstractCommand) {
            AbstractCommand сommand = (AbstractCommand) msg;

            if (сommand instanceof CommandUpdatePrg) {
                //todo отправить обновления
            } else { // остальные команды

                ArrayList<String> res = сommand.getResult();
                if (res != null && !res.isEmpty()) {
                    //todo переработать ответ согласно описанию
                    out.writeBytes(ByteBufUtil.encodeString(ctx.alloc(), CharBuffer.wrap( res.size() + "\n"), charset)); //+ "\n\r"
                    for (String str : res) {
                        out.writeBytes(ByteBufUtil.encodeString(ctx.alloc(), CharBuffer.wrap( str + "\n"), charset)); //+ "\n\r"
                    }
                }
            }
        } else {
            // что тут делать то
            logger.info("Неопознаный тип отправляемого сообщения");
        }
    }
}

