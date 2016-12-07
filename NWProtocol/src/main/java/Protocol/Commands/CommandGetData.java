package protocol.commands;

import protocol.bd.DBContext;

import java.util.Date;

/**
 * получить дату с сервера
 */
public class CommandGetData extends AbstractCommand {

    /**
     */
    public CommandGetData() {
    }

    /**
     * Выполнить команду
     * здесь реализовать логику
     */
    @Override
    public void executeImpl(DBContext dbContext) {
        // поигратся потом с фарматом
        String str = new Date().toString();
        result = new String[]{str};
    }

}