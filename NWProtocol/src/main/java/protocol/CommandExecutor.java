package protocol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.bd.DBContext;
import protocol.commands.AbstractCommand;

/**
 * поток исполнения команд
 */
public final class CommandExecutor implements Runnable {
    private DBContext dbContext;

    private static final Logger logger = LoggerFactory.getLogger(CommandExecutor.class);


    public CommandExecutor() {
        //TODO здесь както с базой решить подключится к базе или в сервере команд
        dbContext = null;

    }

    @Override
    public void run() {
        //while (true) //isActive
        {
            //TODO  както с потоком разобратся, если поток проснулся что ли
            try {
                AbstractCommand command = Server.getCommandExecutorContext().getCommandToDo();
                command.execute(dbContext);
                command.sendResult();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

