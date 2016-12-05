package protocol;

import protocol.bd.DBContext;

/**
 *
 */
public final class CommandExecutor implements Runnable {
    DBContext dbContext;


    public CommandExecutor() {
        //подключится к базе
        dbContext = null;

    }

    @Override
    public void run() {
        while (true) //isActive
        {
            // Получаем следующую сессию для обработки
            try {
                Session session = Server.getCommandExecutorContext().getCommandSession();
                session.execute(dbContext);
                session.sendResult();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}	

