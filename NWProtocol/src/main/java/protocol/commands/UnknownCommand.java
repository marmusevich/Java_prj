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


    /**
     * первый ответ
     */
    public static final String firstResponse = "UnknownCommand";

    /**
     * попытатся распарсить данные команды
     * @param commandData
     */
    public static UnknownCommand tryParseCommand(String commandData) {

        return null;
    }



    @Override
    public void doWorck(ArrayList<String> result, Connection connectionToTerminalDB, Connection connectionToWorkingDB) {
        result.add("Unknown command");
    }
}
