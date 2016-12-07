package protocol.commands;

/**
 * парсить данные в каманду
 */
public class Parser {
    /**
     * распарсить имя команды
     * @param commandData
     * @return
     */
    public String getCammandName(String commandData) {
        String cd = commandData.toUpperCase();
        return getParametrData(cd,"CMD");
    }

    /**
     * Пытается распарсить команду
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
        int i = commandData.indexOf(parametrName);
        if(1 > -1)
        {
            int begin = commandData.indexOf("=", i);
            if(begin > -1){
                int end = commandData.indexOf("\n", begin);
                if(end > -1 && end > begin) {
                    return commandData.substring(begin+1, end).trim() ;
                }
            }
        }
        return null;
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