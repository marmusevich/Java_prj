package protocol;

import protocol.bd.DBContext;
import protocol.commands.AbstractCommand;

/**
 * поток исполнения команд
 */
public final class CommandExecutor implements Runnable {
    DBContext dbContext;


    public CommandExecutor() {
        //TODO здесь както с базой решить подключится к базе или в сервере команд
        dbContext = null;

    }

    @Override
    public void run() {
        while (true) //isActive
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

