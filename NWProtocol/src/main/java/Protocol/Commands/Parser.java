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
                ret = parseData(commandData);
                break;

            case "CHEKSMENA": //
                ret = parseChekSmena(commandData);
                break;

            case "CHKUPDATE": //
                ret = parseChkUpdate(commandData);
                break;

            case "CLOSEOPLATA": //
                ret = parseCloseOplata(commandData);
                break;

            case "FINDADR": //
                ret = parseFindAdr(commandData);
                break;

            case "FINDLS": //
                ret = parseFindLs(commandData);
                break;

            case "GETADDRES": //
                ret = parseGetAddres(commandData);
                break;

            case "GETADDRTERM": //
                ret = parseGetAddrTerm(commandData);
                break;

            case "GETCOMPONENT": //
                ret = parseGetComponent(commandData);
                break;

            case "GETCOUNTER": //
                ret = parseGetCounter(commandData);
                break;

            case "GETDATA": //
                ret = parseGetData(commandData);
                break;

            case "GETDATAFULL": //
                ret = parseGetDataFull(commandData);
                break;

            case "GETKOMISIYA": //
                ret = parseGetKomisiya(commandData);
                break;

            case "GETOPLATA": //
                ret = parseGetOplata(commandData);
                break;

            case "GETOPLATALS": //
                ret = parseGetOplatals(commandData);
                break;

            case "GETOPLATASMENA": //
                ret = parseGetOplataSmena(commandData);
                break;

            case "GETREESTR": //
                ret = parseGetReestr(commandData);
                break;

            case "GETREPORTLIST": //
                ret = parseGetReportList(commandData);
                break;

            case "GETSMENA": //
                ret = parseGetSmena(commandData);
                break;

            case "GETSOSTAV": //
                ret = parseGetSostav(commandData);
                break;

            case "GETTABLE": //
                ret = parseGetTable(commandData);
                break;

            case "LSFINDADR": //
                ret = parseLsFindAdr(commandData);
                break;

            case "SETCOUNTER": //
                ret = parseSetCounter(commandData);
                break;

            case "SETCURRENCE": //
                ret = parseSetCurrence(commandData);
                break;

            case "SETDATA": //
                ret = parseSetData(commandData);
                break;

            case "SETERRORMSG": //
                ret = parseSetErrorMsg(commandData);
                break;

            case "SETSTORNO": //
                ret = parseSetStorno(commandData);
                break;

            case "STARTOPLATA": //
                ret = parseStartOplata(commandData);
                break;

            case "STARTSMEN": //
                ret = parseStartSmen(commandData);
                break;

            case "STOPSMEN": //
                ret = parseStopSmen(commandData);
                break;

            case "UPDATEPRG": //
            ret = parseUpdatePrg(commandData);
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
    private AbstractCommand parseData(String commandData) {
        AbstractCommand ret = null;
        ret = new CommandData();
        return ret;
    }

    private AbstractCommand parseChekSmena(String commandData) {
        AbstractCommand ret = null;
        ret = new CommandData();
        return ret;
    }

    private AbstractCommand parseChkUpdate(String commandData) {
        AbstractCommand ret = null;
        ret = new CommandChkUpdate();
        return ret;
    }

    private AbstractCommand parseCloseOplata(String commandData) {
        AbstractCommand ret = null;
        ret = new CommandCloseOplata();
        return ret;
    }

    private AbstractCommand parseFindAdr(String commandData) {
        AbstractCommand ret = null;
        ret = new CommandFindAdr();
        return ret;
    }

    private AbstractCommand parseFindLs(String commandData) {
        AbstractCommand ret = null;
        ret = new CommandFindLs();
        return ret;
    }

    private AbstractCommand parseGetAddres(String commandData) {
        AbstractCommand ret = null;
        ret = new CommandGetAddres();
        return ret;
    }

    private AbstractCommand parseGetAddrTerm(String commandData) {
        AbstractCommand ret = null;
        ret = new CommandGetAddrTerm();
        return ret;
    }

    private AbstractCommand parseGetComponent(String commandData) {
        AbstractCommand ret = null;
        ret = new CommandGetComponent();
        return ret;
    }

    private AbstractCommand parseGetCounter(String commandData) {
        AbstractCommand ret = null;
        ret = new CommandGetCounter();
        return ret;
    }

    private AbstractCommand parseGetData(String commandData) {
        AbstractCommand ret = null;
        ret = new CommandGetData();
        return ret;
    }

    private AbstractCommand parseGetDataFull(String commandData) {
        AbstractCommand ret = null;
        ret = new CommandGetDataFull();
        return ret;
    }

    private AbstractCommand parseGetKomisiya(String commandData) {
        AbstractCommand ret = null;
        ret = new CommandGetKomisiya();
        return ret;
    }

    private AbstractCommand parseGetOplata(String commandData) {
        AbstractCommand ret = null;
        ret = new CommandGetOplata();
        return ret;
    }

    private AbstractCommand parseGetOplatals(String commandData) {
        AbstractCommand ret = null;
        ret = new CommandGetOplatals();
        return ret;
    }

    private AbstractCommand parseGetOplataSmena(String commandData) {
        AbstractCommand ret = null;
        ret = new CommandGetOplataSmena();
        return ret;
    }

    private AbstractCommand parseGetReestr(String commandData) {
        AbstractCommand ret = null;
        ret = new CommandGetReestr();
        return ret;
    }

    private AbstractCommand parseGetReportList(String commandData) {
        AbstractCommand ret = null;
        ret = new CommandGetReportList();
        return ret;
    }

    private AbstractCommand parseGetSmena(String commandData) {
        AbstractCommand ret = null;
        ret = new CommandGetSmena();
        return ret;
    }

    private AbstractCommand parseGetSostav(String commandData) {
        AbstractCommand ret = null;
        ret = new CommandGetSostav();
        return ret;
    }

    private AbstractCommand parseGetTable(String commandData) {
        AbstractCommand ret = null;
        ret = new CommandGetTable();
        return ret;
    }

    private AbstractCommand parseLsFindAdr(String commandData) {
        AbstractCommand ret = null;
        ret = new CommandLsFindAdr();
        return ret;
    }

    private AbstractCommand parseSetCounter(String commandData) {
        AbstractCommand ret = null;
        ret = new CommandSetCounter();
        return ret;
    }

    private AbstractCommand parseSetCurrence(String commandData) {
        AbstractCommand ret = null;
        ret = new CommandSetCurrence();
        return ret;
    }

    private AbstractCommand parseSetData(String commandData) {
        AbstractCommand ret = null;
        ret = new CommandSetData();
        return ret;
    }

    private AbstractCommand parseSetErrorMsg(String commandData) {
        AbstractCommand ret = null;
        ret = new CommandSetErrorMsg();
        return ret;
    }

    private AbstractCommand parseSetStorno(String commandData) {
        AbstractCommand ret = null;
        ret = new CommandSetStorno();
        return ret;
    }

    private AbstractCommand parseStartOplata(String commandData) {
        AbstractCommand ret = null;
        ret = new CommandStartOplata();
        return ret;
    }

    private AbstractCommand parseStartSmen(String commandData) {
        AbstractCommand ret = null;
        ret = new CommandStartSmen();
        return ret;
    }

    private AbstractCommand parseStopSmen(String commandData) {
        AbstractCommand ret = null;
        ret = new CommandStopSmen();
        return ret;
    }

    private AbstractCommand parseUpdatePrg(String commandData) {
        AbstractCommand ret = null;
        ret = new CommandUpdatePrg();
        return ret;
    }
}