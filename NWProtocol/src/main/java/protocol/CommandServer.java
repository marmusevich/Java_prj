package protocol;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.commands.AbstractCommand;

import java.io.Closeable;
import java.util.concurrent.*;


//TODO управление потоками?

/**
 * Created by lexa on 05.12.2016.
 */
public class CommandServer implements Closeable {

    private final LinkedBlockingQueue<AbstractCommand> commandQueue;
    private final ExecutorService threadPool;
    private int threadPoolSize;

    private static final Logger logger = LoggerFactory.getLogger(CommandServer.class);


    public CommandServer(int threadPoolSize) {
        this.threadPoolSize = threadPoolSize;

        final ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat("CommandExecutor-%d")
                .setDaemon(false)
                .build();
        threadPool = Executors.newFixedThreadPool(this.threadPoolSize, threadFactory);

        commandQueue = new LinkedBlockingQueue<AbstractCommand>();

    }

    // добавление сесси в очередь на обработку
    public void addCommandToProcess(AbstractCommand сommand) {
        if (сommand != null) {
            commandQueue.add(сommand);
            CommandExecutor ce = new CommandExecutor(commandQueue, null);
            threadPool.submit(ce);
            //TODO разбудить поток ?
            logger.trace("addCommandToProcess");
        }
    }

    public void stop(boolean shutdownNow) {
        //TODO остановить пул потоков
        logger.trace(" stop shutdownNow={}", shutdownNow);
        if(shutdownNow)
            threadPool.shutdownNow();
        else
            threadPool.shutdown();
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