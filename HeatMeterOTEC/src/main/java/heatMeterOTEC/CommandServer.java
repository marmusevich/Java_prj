package heatMeterOTEC;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import heatMeterOTEC.commands.AbstractCommand;
import heatMeterOTEC.commands.ErrorFactory;

import java.util.concurrent.*;


/**
 * сервер исполнения команд
 */
public class CommandServer {

    private static final Logger logger = LoggerFactory.getLogger(CommandServer.class);
    private final LinkedBlockingQueue<AbstractCommand> commandQueue;
    private final ExecutorService threadPool;
    private int threadPoolSize;
    private int blockingQueueCapacity;
    private long commandAdTimeout;


    public CommandServer(int threadPoolSize, int blockingQueueCapacity, long commandAdTimeout) {
        this.threadPoolSize = threadPoolSize;
        this.blockingQueueCapacity = blockingQueueCapacity;
        this.commandAdTimeout = commandAdTimeout;

        final ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat("CommandExecutor-%d")
                .setDaemon(false)
                .build();
        threadPool = Executors.newFixedThreadPool(this.threadPoolSize, threadFactory);

        commandQueue = new LinkedBlockingQueue<AbstractCommand>(this.blockingQueueCapacity);

    }

    // добавление сесси в очередь на обработку
    public void addCommandToProcess(AbstractCommand сommand) {
        if (сommand != null) {
            logger.trace("addCommandToProcess");
            try {
                if (!commandQueue.offer(сommand, this.commandAdTimeout, TimeUnit.MILLISECONDS)) {
                    logger.info(" commandQueue.size() = {}}", commandQueue.size());
                    сommand.sendError(ErrorFactory.Error.Timeout);
                } else {

                }
                // разбудить поток или создать
                CommandExecutor ce = new CommandExecutor(commandQueue);
                threadPool.submit(ce);

            } catch (InterruptedException e) {
                e.printStackTrace();

                logger.error(" commandQueue.offer = Exception ", e);
            }
        }
    }


    /**
     * остановить сервер команд
     *
     * @param shutdownNow
     */
    public void stop(boolean shutdownNow) {
        logger.trace(" stop shutdownNow={}", shutdownNow);
        if (shutdownNow)
            threadPool.shutdownNow();
        else
            threadPool.shutdown();
    }


    //для теста
    @Override
    public void finalize() {
        logger.info("finalize");
    }
}