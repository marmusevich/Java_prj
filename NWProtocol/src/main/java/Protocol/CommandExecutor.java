package protocol;

import protocol.commands.AbstractCommand;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 
 */
 public final class CommandExecutor implements Runnable
{
    private final BlockingQueue<Session> commandQueue;
    private final ExecutorService threadPool;
    private int   threadPoolSize;

	
    public CommandExecutor( int threadPoolSize )
    {
        this.threadPoolSize = threadPoolSize;
        this.threadPool     = Executors.newFixedThreadPool( threadPoolSize );
        this.commandQueue   = new LinkedBlockingQueue<Session>();
        
        initThreadPool();
    }
    
    private void initThreadPool()
    {
        for ( int i = 0; i < this.threadPoolSize; i++ )
        {
            this.threadPool.execute( this );
        }
    }
    
    // добавление сесси в очередь на обработку
    public void addCommandToProcess(AbstractCommand сommand, ChannelHandlerContext ctx )
    {
        if ( сommand != null && ctx != null)
        {
            this.commandQueue.add( new Session(сommand, ctx)  );
        }
    }    
    
    @Override
    public void run()
    {
        while ( true ) //isActive
        {
                // Получаем следующую сессию для обработки
            try {
                Session session = this.commandQueue.take();
                session.execute();
                session.sendResult();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }    
}	
	
