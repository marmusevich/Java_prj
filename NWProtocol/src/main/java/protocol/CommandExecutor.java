package protocol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.bd.DBContext;
import protocol.commands.AbstractCommand;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * поток исполнения команд
 */
public final class CommandExecutor implements Runnable{
    private DBContext dbContext;
    private LinkedBlockingQueue<AbstractCommand> commandQueue;

    private static final Logger logger = LoggerFactory.getLogger(CommandExecutor.class);

    public CommandExecutor(LinkedBlockingQueue<AbstractCommand> commandQueue, DBContext dbContext) {
        this.dbContext = dbContext;
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

        try {
            AbstractCommand command = commandQueue.take();
            //Thread.wait(5);
            //todo Thread.sleep(10);
            Thread.sleep(10);
            //logger.info(" size() = {}}", commandQueue.size());
            command.execute(dbContext);
            command.sendResult();
        } catch (InterruptedException e) {
            e.printStackTrace();
            //todo вывести клиенту ошибку
            logger.error("  ", e);
        }
    }


    //для теста
    @Override
    public void finalize() {
        //logger.info("finalize");
    }
}

