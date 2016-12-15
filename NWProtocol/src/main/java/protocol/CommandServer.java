package protocol;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.commands.AbstractCommand;

import java.util.concurrent.*;


//TODO управление потоками?

/**
 * Created by lexa on 05.12.2016.
 */
public class CommandServer {

    private final BlockingQueue<AbstractCommand> commandQueue;
    private final ExecutorService threadPool;
    private int threadPoolSize;

    private static final Logger logger = LoggerFactory.getLogger(CommandServer.class);


    public CommandServer(int threadPoolSize) {
        this.threadPoolSize = threadPoolSize;

        final ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat("CommandExecutor-%d")
                .setDaemon(false)
                .build();


        threadPool = Executors.newCachedThreadPool(threadFactory);
        commandQueue = new LinkedBlockingQueue<AbstractCommand>();

        initThreadPool();
    }

    private void initThreadPool() {
//        for (int i = 0; i < this.threadPoolSize; i++) {
//            threadPool.submit(new CommandExecutor());
//            //TODO здесь както с базой решить или в конструкторе
//        }
    }

    // добавление сесси в очередь на обработку
    public void addCommandToProcess(AbstractCommand сommand) {
        if (сommand != null) {
            commandQueue.add(сommand);
            threadPool.submit(new CommandExecutor());
            //TODO разбудить поток ?
            logger.trace("addCommandToProcess");
        }
    }

    public AbstractCommand getCommandToDo() throws InterruptedException {
        //TODO безопасное извлечение
        logger.trace("getCommandToDo");
        return this.commandQueue.take();
    }

    public void stop(boolean shutdownNow) {
        //TODO остановить пул потоков
        logger.trace(" stop shutdownNow={}", shutdownNow);
        if(shutdownNow)
            threadPool.shutdownNow();
        else
            threadPool.shutdown();
    }
}