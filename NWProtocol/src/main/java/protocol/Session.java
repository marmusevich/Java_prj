package protocol;

import io.netty.channel.ChannelHandlerContext;
import protocol.bd.DBContext;
import protocol.commands.AbstractCommand;

/**
 * Created by asus on 04.12.2016.
 */
public class Session {

    protected AbstractCommand сommand;
    protected ChannelHandlerContext ctx;

    public Session(AbstractCommand сommand, ChannelHandlerContext ctx) {
        this.сommand = сommand;
        this.ctx = ctx;

    }


    public void execute(DBContext dbContext) {
        сommand.execute(dbContext);
    }

    /**
     * отправить результат если он есть
     */
    public void sendResult() {
        if (ctx != null)
            ctx.writeAndFlush(сommand);
    }

}
