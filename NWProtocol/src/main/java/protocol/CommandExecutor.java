package protocol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.bd.DBContext;
import protocol.commands.AbstractCommand;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * поток исполнения команд
 */
public final class CommandExecutor implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(CommandExecutor.class);
    private DBContext dbContext;
    private LinkedBlockingQueue<AbstractCommand> commandQueue;

    public CommandExecutor(LinkedBlockingQueue<AbstractCommand> commandQueue) {
        this.commandQueue = commandQueue;
    }

    /**
     * вывод имени текущего потока
     */
    public void printMsg() {
        Thread t = Thread.currentThread();
        String name = t.getName();
        logger.info("Thread name=" + name);
    }


    @Override
    public void run() {
        AbstractCommand command = null;
        try {
            command = commandQueue.take();
            //Thread.sleep(100);
            command.execute();
        } catch (InterruptedException e) {
            //todo вывести клиенту ошибку
//            if(command != null)
//                command.sendError(ErrorFactory.Error.Timeout);
            logger.error(" CommandExecutor ", e);
        }
    }


    //для теста
    @Override
    public void finalize() {
        //logger.info("finalize");
    }
}

