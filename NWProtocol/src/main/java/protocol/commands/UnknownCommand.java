package protocol.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.bd.DBContext;

/**
 *
 */
public class UnknownCommand extends AbstractCommand {

    private static final Logger logger = LoggerFactory.getLogger(UnknownCommand.class);

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