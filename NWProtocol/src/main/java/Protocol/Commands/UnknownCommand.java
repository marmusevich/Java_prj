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
    public void executeImpl(DBContext dbContext) {
        result = new String[]{"Unknown command"};
        System.out.println(result);
    }

}