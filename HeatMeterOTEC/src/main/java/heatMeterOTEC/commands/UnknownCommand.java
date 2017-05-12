package heatMeterOTEC.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 */
public class UnknownCommand extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(UnknownCommand.class);

    {
        mCommandType = UnknownCommand.class.toString();
    }

    @Override
    public void doWorck(ArrayList<String> result, Connection connection) throws SQLException {
        result.add("Unknown command");
    }

    @Override
    public String toString() {
        return super.toString() + " --> " ;
    }

}
