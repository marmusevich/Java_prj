package protocol.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.Server;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * Created by asus on 20.12.2016.
 */
public class StopServerCommand extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(StopServerCommand.class);

    @Override
    public void doWorck(ArrayList<String> result, Connection connection) {
        logger.info("StopServerCommand");
        //todo проверка на то что с локальной машины пришла команда
        Server.stop();
    }
}
