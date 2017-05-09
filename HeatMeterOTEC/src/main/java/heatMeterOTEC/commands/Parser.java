package heatMeterOTEC.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * парсить данные в каманду
 */
public class Parser {
    private static final Logger logger = LoggerFactory.getLogger(Parser.class);

    /**
     * распарсить имя команды
     *
     * @param commandData
     * @return
     */
    public static String getCammandName(String commandData) {
        String cd = commandData.toUpperCase();
        return getParametrData(cd, "CMD");
    }

    /**
     * вернуть первый ответ
     *
     * @param commandName имя команды
     * @return
     */
    public static String getFirstResponse(String commandName) {
        String ret = null;

        switch (commandName.toUpperCase()) {
            case "STOP_SERVER":
                ret = StopServerCommand.firstResponse;
                break;

            case "STATISTIC_SERVER":
                ret = StatisticServerCommand.firstResponse;
                break;

            case "DATE": // получить время
                ret = CommandData.firstResponse;
                break;

            default: // неопознаная командв
                ret = UnknownCommand.firstResponse;
        }
        return ret;
    }

    /**
     * распарсить и проверить имя пользователя и пароль
     *
     * @param commandData
     * @return - true если удалось получить
     */
    public static boolean parseUserAndPassword(String commandData, UserAuthenticationData uad) {
        boolean flOK = false;
        uad.name = getParametrData(commandData, AbstractCommand.UserParametrName);
        uad.pass = getParametrData(commandData, AbstractCommand.PassParametrName);
        flOK = uad.name != null && uad.pass != null;
        return flOK;
    }


    /**
     * Пытается распарсить команду
     *
     * @param commandName имя команды
     * @param commandData данные команды
     * @return распарсеную команду
     */
    public static AbstractCommand tryParseCommand(String commandName, String commandData) {
        AbstractCommand ret = null;
        //logger.info("commandName ({}) commandData = '{}'", commandName, commandData);

        switch (commandName.toUpperCase()) {
            case "STOP_SERVER":
                ret = StopServerCommand.tryParseCommand(commandData);
                break;

            case "STATISTIC_SERVER":
                ret = StatisticServerCommand.tryParseCommand(commandData);
                break;

            case "DATE": // получить время
                ret = CommandData.tryParseCommand(commandData);
                break;



            default: // неопознаная командв
                ret = UnknownCommand.tryParseCommand(commandData);
        }
        return ret;
    }


    /**
     * получить данные одного параметра
     *
     * @param commandData
     * @param parametrName
     * @return
     */
    public static String getParametrData(String commandData, String parametrName) {
        int index = commandData.indexOf(parametrName);
        if (index > -1) {
            int begin = commandData.indexOf("=", index);
            if (begin > -1) {
                int end = commandData.indexOf("\n", begin);
                if (end > -1 && end > begin) {
                    return commandData.substring(begin + 1, end).trim();
                }
            }
        }
        return null;
    }
}

