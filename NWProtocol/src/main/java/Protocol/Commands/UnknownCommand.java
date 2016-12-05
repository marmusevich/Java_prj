package protocol.commands;

import protocol.bd.DBContext;

/**
 *
 */
public class UnknownCommand extends AbstractCommand {

    /**
     * Default constructor
     */
    public UnknownCommand() {
    }

    /**
     * Выполнить команду
     * здесь реализовать логику
     */
    @Override
    public void execute(DBContext dbContext) {

    }

    /**
     * Вернуть результат
     *
     * @return набор строк результата
     */
    @Override
    public String[] getResult() {
        return new String[]{"Unknown command"};
    }
}