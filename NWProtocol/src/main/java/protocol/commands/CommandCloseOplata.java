package protocol.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Команда closeoplata
 * Выполняет закрытие процедуры оплаты. Значение.
 * 4.	closeoplata при успешном выполнении возвращает CLOSEOPL
 * 5.	Возвращение результата выполнения команды
 * В случае успешного выполнения команды возвращается 200 ОК, в случае возникновения какой либо ошибки выводится сообщение 500 ERROR.
 * По завершению работы команды происходит отключение от сервера.
 * Наименования параметров:
 * ID_TERMINAL = Идентификатор терминала, обязательный параметр;
 * LOGIN = Выданный логин, обязательный параметр;
 * <p>
 * Количество передаваемых параметров – два. В случае неправильного написания наименования параметров, параметр будет проигнорирован,
 * и заполнен значением по умолчанию. В случае не заполнения одного из обязательных параметров сервер вернет ошибку выполнения команды.
 * Параметры могут быть перечислены в любой последовательности.
 * 6.	Возвращение результата выполнения команды
 * В случае успешного выполнения команды возвращается 200 ОК, в случае возникновения какой либо ошибки выводится сообщение 500 ERROR. По завершению работы команды происходит отключение от сервера.
 */
public class CommandCloseOplata extends AbstractCommand {
    /**
     * первый ответ
     */
    public static final String firstResponse = "CLOSEOPL";
    private static final Logger logger = LoggerFactory.getLogger(CommandCloseOplata.class);

    /**
     * попытатся распарсить данные команды
     *
     * @param commandData
     */
    public static CommandCloseOplata tryParseCommand(String commandData) {
        CommandCloseOplata ret = null;
        boolean flOK = false;

        UserAuthenticationData uad = new UserAuthenticationData();
        flOK = Parser.parseUserAndPassword(commandData, uad);

        if (flOK) {
            ret = new CommandCloseOplata();
            ret.setUserNameAndPass(uad);
        }
        return ret;
    }


    @Override
    public void doWorck(ArrayList<String> result, Connection connectionToTerminalDB, Connection connectionToWorkingDB) throws SQLException {

        String SQLText = "select gen_id(GEN_SETCURRENCE, 1) from rdb$database";
        try {
            connectionToTerminalDB.setAutoCommit(false);
            PreparedStatement ps = connectionToTerminalDB.prepareStatement(SQLText);
            ResultSet rs = ps.executeQuery();
            connectionToTerminalDB.commit();

        } catch (SQLException e) {
            connectionToTerminalDB.rollback();
            connectionToTerminalDB.setAutoCommit(true);
            throw e;
        } finally {
            connectionToTerminalDB.setAutoCommit(true);
        }
    }
}

