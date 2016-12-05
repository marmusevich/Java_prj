package protocol.commands;

/**
 *
 */
public class Parser {

    /**
     * Default constructor
     */
    public Parser() {
    }

    /**
     * распарсить имя команды
     *
     * @param commandData
     * @return
     */

    public String getCammandName(String commandData) {
        // TODO implement here
        return "DATE";
    }


    /**
     * Пытается распарсить команду
     *
     * @param cammandName имя команды
     * @param commandData данные команды
     * @return распарсеную команду
     */
    public AbstractCommand tryParseCommand(String cammandName, String commandData) {
        AbstractCommand ret = null;
        switch (cammandName.toUpperCase()) {
            case "DATE": // получить время
                ret = parseGetData(commandData);
                break;

            default: // неопознаная командв
                ret = new UnknownCommand();
        }


        return ret;
    }

    // impliment
    // реализация парсинга для каждой команды
    private AbstractCommand parseGetData(String commandData) {
        AbstractCommand ret = null;

        ret = new CommandGetData();

        return ret;
    }


}