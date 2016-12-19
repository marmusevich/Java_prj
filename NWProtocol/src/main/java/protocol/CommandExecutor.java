package protocol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.bd.DBContext;
import protocol.commands.AbstractCommand;

import java.io.Closeable;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * поток исполнения команд
 */
public final class CommandExecutor implements Runnable, Closeable {
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
        printMsg();
        try {
            AbstractCommand command = commandQueue.take();
            command.execute(dbContext);
            command.sendResult();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    //для теста
    @Override
    public void finalize() {
        logger.info("finalize");
    }
    @Override
    public void close() {
        logger.info("close");
    }
}

