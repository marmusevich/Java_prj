package protocol.commands;

import io.netty.channel.ChannelHandlerContext;
import protocol.bd.DBContext;

import java.util.ArrayList;

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
    final public void setUserNameAndPass(String userName, String userPass){
        this.userName = userName;
        this.userPass = userPass;
    }
    final public boolean checkUserNameAndPass(DBContext dbContext){
        //TODO имя пользователя и пароль, проверка, как отправить ошибку аунтификацию
        return true;
    }


    /**
     * Выполнить команду
     * здесь реализовать логику
     */
    final public void execute(DBContext dbContext){
        //TODO проверить аунтификацию, не выполнять команду
        if(checkUserNameAndPass(dbContext))
            doWorck(dbContext);
    }

    /**
     * переопределить в наследниках
     * @param dbContext
     */
    public abstract void doWorck(DBContext dbContext);


    protected ArrayList<String> result = new ArrayList<String>();
    /**
     * Вернуть результат
     * @return набор строк результата
     */
    final public ArrayList<String> getResult(){
        return result;
    }



    /**
     * отправить результат если он есть
     */
    final public void sendResult() {
        //TODO проверить а активно ли соеденение
        //TODO  убивать не активные каналы
        if (ctx != null)
            ctx.writeAndFlush(this);
    }
}