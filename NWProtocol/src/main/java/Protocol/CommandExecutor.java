package Protocol;

import Protocol.Commands.AbstractCommand;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 
 */
 public final class CommandExecutor implements Runnable
{

    private final BlockingQueue<AbstractCommand> commandQueue;
    private final ExecutorService threadPool;
    private int   threadPoolSize;

	
    public CommandExecutor( int threadPoolSize )
    {
        this.threadPoolSize = threadPoolSize;
        this.threadPool     = Executors.newFixedThreadPool( threadPoolSize );
        this.commandQueue   = new LinkedBlockingQueue();
        
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
    public void addCommandToProcess( AbstractCommand сommand )
    {
        if ( сommand  != null )
        {
            this.commandQueue.add( сommand );
        }
    }    
    
    @Override
    public void run()
    {
        while ( true ) //isActive
        {
                // Получаем следующую сессию для обработки
                //AbstractCommand сommand = (AbstractCommand) this.commandQueue.take();
                
				//сommand.execute();
				
                // Здесь происходит обработка игровых сообщений
                // получаем пакет
                //packet = session.getReadPacketQueue().take();

                // далее получаем и обрабатываем данные из него
                //data =  packet.getData();
        }
    }    
}	
	
