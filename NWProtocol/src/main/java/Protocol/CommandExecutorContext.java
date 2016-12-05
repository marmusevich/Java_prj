package protocol;

import io.netty.channel.ChannelHandlerContext;
import protocol.commands.AbstractCommand;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by lexa on 05.12.2016.
 */
public class CommandExecutorContext {

    private final BlockingQueue<Session> commandQueue;
    private final ExecutorService threadPool;
    private int threadPoolSize;


    public CommandExecutorContext(int threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
        this.threadPool = Executors.newFixedThreadPool(threadPoolSize);
        this.commandQueue = new LinkedBlockingQueue<Session>();

        initThreadPool();
    }

    private void initThreadPool() {
        for (int i = 0; i < this.threadPoolSize; i++) {
            this.threadPool.execute(new CommandExecutor());
        }
    }

    // добавление сесси в очередь на обработку
    public void addCommandToProcess(AbstractCommand сommand, ChannelHandlerContext ctx) {
        if (сommand != null && ctx != null) {
            this.commandQueue.add(new Session(сommand, ctx));
        }
    }

    public Session getCommandSession() throws InterruptedException {
        return this.commandQueue.take();
    }


}
