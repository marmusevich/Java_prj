package protocol.commands;

/**
 * парсить данные в каманду
 */
public class Parser {

    /**
     * Default constructor
     */
    public Parser() {
    }

    /**
     * распарсить имя команды
     * @param commandData
     * @return
     */
    public String getCammandName(String commandData) {
        //TODO распарсить имя команды
        return "DATE";
    }


    /**
     * Пытается распарсить команду
     *
     * @param commandName имя команды
     * @param commandData данные команды
     * @return распарсеную команду
     */
    public AbstractCommand tryParseCommand(String commandName, String commandData) {
        AbstractCommand ret = null;
        switch (commandName.toUpperCase()) {
            case "DATE": // получить время
                ret = parseGetData(commandData);
                break;

            default: // неопознаная командв
                ret = new UnknownCommand();
        }
        return ret;
    }

    /**
     * получить данные одного параметра
     * @param commandData
     * @param parametrName
     * @return
     */
    private String getParametrData(String commandData, String parametrName) {
        return "";
    }



    //TODO как сделать парсер для каждой команды
    //TODO или статическая функция у каждой команды или ??

    // impliment
    // реализация парсинга для каждой команды
    private AbstractCommand parseGetData(String commandData) {
        AbstractCommand ret = null;

        ret = new CommandGetData();

        return ret;
    }


}