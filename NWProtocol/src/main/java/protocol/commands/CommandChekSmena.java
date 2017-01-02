package protocol.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Команда cheksmena
 * Команда выполняет проверку наличия открытой смены по данному ID_TERMINAL и LOGIN
 * 1.	checksmena при успешном выполнении возвращает CHKSMENA и ожидает передачи данных в формате TString (массив строк)
 * 2.	Передача параметров в формате TString (массив строк)
 * Наименования параметров:
 * ID_TERMINAL = Идентификатор терминала, обязательный параметр;
 * LOGIN = Выданный логин, обязательный параметр;
 * Количество передаваемых параметров – два. В случае неправильного написание наименования параметров, параметр будет проигнорирован, и заполнен значением по умолчанию. В случае не заполнения одного из обязательных параметров сервер вернет ошибку выполнения команды.  Параметры могут быть перечислены в любой последовательности.
 * <p>
 * 3.	Передача переменной количества параметров в списке TString (массив строк) передается числовым значением.
 * 4.	Передает переменную в формате TString (массив строк) со следующими записями:
 * - ADRESS = Адрес терминала или кассы;
 * - TERMINAL = Наименование терминала;
 * - SMENA = Количество открытых смен
 * - SMENA_ID = Идентификатор смены
 * - DATA_N=Дата начала смены
 * - BANK_ID=Идентификатор банка
 * - BANK_NAME=Наименование банка
 * - FILIAL_ID=Идентификатор филиала
 * - FILIAL=Наименование филиала
 * 5.	Возвращение результата выполнения команды
 * В случае успешного выполнения команды возвращается 200 ОК, в случае возникновения какой либо ошибки выводится сообщение 500 ERROR. По завершению работы команды происходит отключение от сервера.
 */


/**
 * Created by lexa on 08.12.2016.
 */
public class CommandChekSmena extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(CommandChekSmena.class);

    /**
     * первый ответ
     */
    public static final String firstResponse = "CHKSMENA";

    /**
     * попытатся распарсить данные команды
     * @param commandData
     */
    public static CommandChekSmena tryParseCommand(String commandData) {
        CommandChekSmena ret = null;
        boolean flOK = false;

        UserAuthenticationData uad = new UserAuthenticationData();
        flOK = Parser.parseUserAndPassword(commandData, uad);

        if (flOK) {
            ret = new CommandChekSmena();
            ret.setUserNameAndPass(uad);
        }
        return ret;
    }

    @Override
    public void doWorck(ArrayList<String> result, Connection connectionToTerminalDB, Connection connectionToWorkingDB) throws SQLException {
        String SQLText =
                "SELECT  TERMINAL.ID KASSA_ID, " +
                        " TERMINAL.terminal_id , " +
                        " TERMINAL.addres ADDRES, " +
                        " users.login USERS, " +
                        " TERMINAL.FILIAL_ID, " +
                        " filial.NAME filial, " +
                        " bank.name BANK, " +
                        " bank.id BANK_ID, " +
                        " USERS.FIO, " +
                        " (SELECT count(*) FROM SMENA WHERE DATA_K is null and SMENA.id_terminal = TERMINAL.ID) SMENA, " +
                        " (SELECT distinct(smena.id) FROM SMENA WHERE DATA_K is null and SMENA.id_terminal = TERMINAL.ID) smena_id, " +
                        " (SELECT distinct(smena.data_n) FROM SMENA WHERE DATA_K is null and SMENA.id_terminal = TERMINAL.ID) data_n, " +
                        " TERMINAL.RS_DEBET RS_DEBET, " +
                        " TERMINAL.RS_CREDIT RS_CREDIT " +
                        "    FROM TERMINAL " +
                        "  left join filial on (filial.id=terminal.filial_id) " +
                        "  left join BANK on (BANK.id=terminal.bank_id) " +
                        "  left join users on (USERS.bank=terminal.bank_id and USERS.login = ?) " +
                        "  WHERE " +
                        " TERMINAL.TERMINAL_ID=? AND " +
                        " terminal.BANK_ID =(SELECT USERS.bank FROM USERS WHERE LOGIN=?) ";

        PreparedStatement ps = connectionToTerminalDB.prepareStatement(SQLText);
        ps.setString(1, userAuthenticationData.name);
        ps.setString(2, userAuthenticationData.pass);
        ps.setString(3, userAuthenticationData.name);


        ResultSet rs = ps.executeQuery();
        SimpleDateFormat dateFormat = new SimpleDateFormat("'200' dd.MM.yyyy HH:mm:ss");
        while (rs.next()) {
            result.add("ADDRES=" + rs.getString("ADDRES"));
            result.add("KASSA_ID=" + rs.getString("KASSA_ID"));
            result.add("TERMINAL=" + rs.getString("TERMINAL"));
            result.add("SMENA=" + rs.getString("SMENA"));
            result.add("SMENA_ID=" + rs.getString("SMENA_ID"));
            result.add("BANK_NAME=" + rs.getString("BANK"));
            result.add("DATA_N=" + dateFormat.format(rs.getDate("DATA_N")));
            result.add("BANK_ID=" + rs.getString("BANK_ID"));
            result.add("FILIAL_ID=" + rs.getString("FILIAL_ID"));
            result.add("FILIAL=" + rs.getString("FILIAL"));
            result.add("FIO=" + rs.getString("FIO"));
            result.add("RS_DEBET=" + rs.getString("RS_DEBET"));
            result.add("RS_CREDIT=" + rs.getString("RS_CREDIT"));
        }

    }
}


