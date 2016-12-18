package protocol.commands;

import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.bd.DBContext;

import java.io.Closeable;
import java.util.ArrayList;

/**
 * Абстастная команда
 * все остальные команды наследовать от нее
 */
public abstract class AbstractCommand implements Closeable {

    private static final Logger logger = LoggerFactory.getLogger(AbstractCommand.class);

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
        logger.trace("checkUserNameAndPass");
        return true;
    }


    /**
     * Выполнить команду
     * здесь реализовать логику
     */
    final public void execute(DBContext dbContext){
        //TODO проверить аунтификацию, не выполнять команду
        logger.trace("execute{}", ctx.pipeline().channel().remoteAddress().toString());
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
        logger.trace("sendResult {}", ctx.pipeline().channel().remoteAddress().toString());

        if (ctx != null)
            ctx.writeAndFlush(this);
    }

    @Override
    public void finalize() {
        logger.info("finalize");

    }

    @Override
    public void close() {
        logger.info("close");

    }

}