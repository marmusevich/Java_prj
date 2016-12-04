package Protocol.Commands;

import io.netty.channel.ChannelHandlerContext;

/**
 * Абстастная команда
 */
public abstract class AbstractCommand {

    protected ChannelHandlerContext ctx;

    {
        ctx = null;
    }

    /**
     * Default constructor
     */
    public AbstractCommand() {
    }

    /**
     * Выполнить команду
     * здесь реализовать логику
     */
    public abstract void execute();

    /**
     * Вернуть результат
     *
     * @return набор строк результата
     */
    public abstract String[] getResult();

    /**
     *  отправить результат если он есть
     */
    public void sendResult() {
        String[] res = getResult();
        if (ctx != null && res != null && res.length > 0) {
            for (String str : res) {
                ctx.write(str);
            }
        }

    }


}