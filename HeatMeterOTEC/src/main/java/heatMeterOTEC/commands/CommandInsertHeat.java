package heatMeterOTEC.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 */
public class CommandInsertHeat extends AbstractCommand {
    /**
     * первый ответ
     */
    public static final String firstResponse = "SETERRORMSG";
    private static final Logger logger = LoggerFactory.getLogger(CommandInsertHeat.class);
    String error_msg = "";

    /**
     * попытатся распарсить данные команды
     *
     * @param commandData
     */
    public static CommandInsertHeat tryParseCommand(String commandData) {
        CommandInsertHeat ret = null;
        boolean flOK = false;

        UserAuthenticationData uad = new UserAuthenticationData();
        flOK = Parser.parseUserAndPassword(commandData, uad);

        String _error_msg = Parser.getParametrData(commandData, "ERROR_MSG");

        flOK = flOK && (_error_msg != null);

        if (flOK) {
            ret = new CommandInsertHeat();
            ret.setUserNameAndPass(uad);
            ret.error_msg = _error_msg;
        }

        return ret;

    }

    @Override
    public void doWorck(ArrayList<String> result, Connection connection) throws SQLException {
//        String SQLText = " INSERT INTO TERMINAL_ERRORS (ID_TERMINAL ,ERROR_MSG)" +
//                " VALUES (?, ?) ";
//
//        int id_term = GetTerminalIDAndCheckSmenaIsOpen(connection);
//        PreparedStatement ps = connection.prepareStatement(SQLText);
//        ps.setInt(1, id_term);
//        ps.setString(1, error_msg);
//        int countChangeString = ps.executeUpdate();
//        if (countChangeString != -1) { // ok
//
//        } else { //error
//            result.add("500 Error insert record");
//        }
//        ps.close();

        //todo как возращать результат для сетерных команд

    }
}



