package protocol.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Команда date:
 * Возвращает текущую дату и время сервера в формате 200 ДД.ММ.ГГГГ ЧЧ:ММ:СС, где:
 * 200 Код успешного выполнения команды
 * ДД - день с лидирующем нулем;
 * ММ – месяц с лидирующим нулем ;
 * ГГГГ – год , в четырехзначном представлении;
 */
public class CommandData extends AbstractCommand {
    /**
     * первый ответ
     */
    public static final String firstResponse = "";
    private static final Logger logger = LoggerFactory.getLogger(CommandData.class);

    /**
     * попытатся распарсить данные команды
     *
     * @param commandData
     */
    public static CommandData tryParseCommand(String commandData) {
        CommandData ret = null;
        boolean flOK = false;

        UserAuthenticationData uad = new UserAuthenticationData();
        flOK = Parser.parseUserAndPassword(commandData, uad);

        if (flOK) {
            ret = new CommandData();
            ret.setUserNameAndPass(uad);
        }
        return ret;
    }

    @Override
    public void doWorck(ArrayList<String> result, Connection connectionToTerminalDB, Connection connectionToWorkingDB) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("'200' dd.MM.yyyy HH:mm:ss");
        result.add(dateFormat.format(new Date()));
    }
}

