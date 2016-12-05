package protocol;

import protocol.commands.AbstractCommand;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by lexa on 05.12.2016.
 */
public class CommandServer {

    private final BlockingQueue<AbstractCommand> commandQueue;
    private final ExecutorService threadPool;
    private int threadPoolSize;


    public CommandServer(int threadPoolSize) {
        this.threadPoolSize = threadPoolSize;

        this.threadPool = Executors.newFixedThreadPool(threadPoolSize);
        this.commandQueue = new LinkedBlockingQueue<AbstractCommand>();

        initThreadPool();
    }

    private void initThreadPool() {
        for (int i = 0; i < this.threadPoolSize; i++) {
            this.threadPool.submit(new CommandExecutor());
            //TODO здесь както с базой решить или в конструкторе
        }
    }

    // добавление сесси в очередь на обработку
    public void addCommandToProcess(AbstractCommand сommand) {
        if (сommand != null) {
            this.commandQueue.add(сommand);
            //TODO разбудить поток ?
            //this.threadPool.invokeAll();
        }
    }

    public AbstractCommand getCommandToDo() throws InterruptedException {
        //TODO безопасное извлечение
        return this.commandQueue.take();
    }

    public void stop() {
        //TODO остановить пул потоков
        //this.commandQueue.
    }

}
