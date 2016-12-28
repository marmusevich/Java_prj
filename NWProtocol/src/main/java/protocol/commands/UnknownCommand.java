package protocol.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.ArrayList;

/**
 *
 */
public class UnknownCommand extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(UnknownCommand.class);

    @Override
    public void doWorck(ArrayList<String> result, Connection connectionToTerminalDB, Connection connectionToWorkingDB) {
        result.add("Unknown command");
    }
}