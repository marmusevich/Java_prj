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
    public void doWorck(DBContext dbContext) {
        result.add("Unknown command");
    }

}