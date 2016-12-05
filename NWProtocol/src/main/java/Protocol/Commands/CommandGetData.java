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
    public void execute(DBContext dbContext) {
        // а ничего и не делаем

        //System.out.println("        CommandGetData - execute()");
    }

    /**
     * Вернуть результат
     *
     * @return набор строк результата
     */
    @Override
    public String[] getResult() {
        // поигратся потом с фарматом
        String str = new Date().toString();
        return new String[]{str};
    }

}