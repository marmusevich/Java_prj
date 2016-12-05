package protocol.commands;

import io.netty.channel.ChannelHandlerContext;
import protocol.bd.DBContext;

/**
 * Абстастная команда
 * все остальные команды наследовать от нее
 */
public abstract class AbstractCommand {

    protected ChannelHandlerContext ctx;
    /**
     */
    public AbstractCommand() {
    }

    /**
     * устоновить сонтекст конала
     * @param ctx
     * @return
     */
    public AbstractCommand setChannelHandlerContext(ChannelHandlerContext ctx) {
        this.ctx = ctx;
        return this;
    }




    private String userName;
    private String userPass;
    public void setUserNameAndPass(String userName, String userPass){
        this.userName = userName;
        this.userPass = userPass;
    }
    public boolean checkUserNameAndPass(DBContext dbContext){
        //TODO имя пользователя и пароль, проверка, как отправить ошибку аунтификацию
        return true;
    }


    /**
     * Выполнить команду
     * здесь реализовать логику
     */
    public abstract void execute(DBContext dbContext);

    /**
     * Вернуть результат
     * @return набор строк результата
     */
    public abstract String[] getResult();

    /**
     * отправить результат если он есть
     */
    public void sendResult() {
        //TODO проверить а активно ли соеденение
        //TODO  убивать не активные каналы
        if (ctx != null)
            ctx.writeAndFlush(this);
    }
}