package protocol.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * парсить данные в каманду
 */
public class Parser {
    private static final Logger logger = LoggerFactory.getLogger(Parser.class);

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

        //logger.info("commandName ({}) commandData = '{}'", commandName, commandData);


        switch (commandName.toUpperCase()) {
            case "STOP_SERVER":
                ret = parseStopServer(commandData);
                break;

            case "STATISTIC_SERVER":
                ret = parseStatisticServer(commandData);
                break;

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
        int index = commandData.indexOf(parametrName);
        if(index > -1)
        {
            int begin = commandData.indexOf("=", index);
            if(begin > -1){
                int end = commandData.indexOf("\n", begin);
                if(end > -1 && end > begin) {
                    return commandData.substring(begin+1, end).trim() ;
                }
            }
        }
        return null;
    }

    private String getUserName(String commandData){
        return getParametrData(commandData,"ID_TERM");
    }
    private String getUserPass(String commandData ){
        return getParametrData(commandData,"PASSWORD");
    }
    private boolean checkIsEmptyUserAndPassword(String userName, String userPass){
        return userName != null &&  userPass != null;
    }


    //TODO реализация парсинга комманд
    //TODO парсить имя и пароль пользователя

    // impliment
    // todo реализация парсинга для каждой команды
    private AbstractCommand parseStopServer(String commandData) {
        AbstractCommand ret = null;

        boolean flOK = false;

        String userName = getUserName(commandData);
        String userPass = getUserPass(commandData);;
        flOK = checkIsEmptyUserAndPassword(userName, userPass);

        if(flOK){
            ret = new StopServerCommand();
            ret.setUserNameAndPass(userName, userPass);
        }
        return ret;
    }

    private AbstractCommand parseStatisticServer(String commandData) {
        AbstractCommand ret = null;

        boolean flOK = false;

        String userName = getUserName(commandData);
        String userPass = getUserPass(commandData);;
        flOK = checkIsEmptyUserAndPassword(userName, userPass);

        if(flOK){
            ret = new StatisticServerCommand();
            ret.setUserNameAndPass(userName, userPass);
        }

        return ret;
    }

    private AbstractCommand parseData(String commandData) {
        AbstractCommand ret = null;

        boolean flOK = false;

        String userName = getUserName(commandData);
        String userPass = getUserPass(commandData);;
        flOK = checkIsEmptyUserAndPassword(userName, userPass);

        if(flOK){
            ret = new CommandData();
            ret.setUserNameAndPass(userName, userPass);
        }
        return ret;
    }

}





//
//LOGIN='SYSDBA';
//        PASSWD='qazwsx12';
//
////DB='91.197.16.103:TERMINAL';
////DB_WORK='91.197.16.103:WORKING';
//
//        DB='192.168.0.202:TERMINAL';
//        DB_WORK='192.168.0.202:WORKING';
////DB_WORK='192.168.0.202:PLATEGKA';
//        var
//        Results, LCmd:string;
//        Str:TStringList;
//        counts:integer;
//        scrstream: TMemoryStream;
//        begin
//        PATHPRG:=ExtractFilePath(Application.ExeName);
//        Str:=TStringList.Create;
//
//        with AContext.Connection do
//        try
//        section.Enter;
//        Inc(processed, 1);
//        try
//        LCmd := Trim(Socket.ReadLn(TEncoding.UTF8));
////*///////////////////////////////////////////////////////////////////////////
//        //AContext.Connection.IOHandler.
//        if SameText(trim(LCmd), 'process') then
//        begin
//        Socket.WriteLn('Connect: '+IntToStr(processed),TEncoding.UTF8);
//        end
//
////*///////////////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'quit') then
//        begin
//        Socket.WriteLn('200 Good bye',TEncoding.UTF8);
//        end
////////////////////////////////////////////////////////////////////////////
//        else
//        begin
//        AContext.Connection.Socket.WriteLn('Error command :(',TEncoding.UTF8);
//        end;
//        except
//        {on e: EIdException do
//        begin
//        AContext.Connection.Socket.Close;
//        AContext.Connection.Disconnect;
//
//        Str.Add('Error: '+#13+Str.Values['ID_TERMINAL']+#13+Str.Values['LOGIN']+#13+Lcmd+' '+e.Message);
//        Str.SaveToFile('C:\WMProtocol_EIdException.txt');
//        end; // on EIdException
//        on e: Exception do
//        begin
//        Str.Add('Error: '+#13+Str.Values['ID_TERMINAL']+#13+Str.Values['LOGIN']+#13+Lcmd+' '+e.Message);
//        Str.SaveToFile('C:\WMProtocol_Exception.txt');
//        end; // on Exception  }
//        end;
//        finally
//        section.Leave;
//        Disconnect;
//        end;
//        Str.Free;












//////////////////Общие фцнкции
//        procedure TDM1.ErrorHandler1FIBErrorEvent(Sender: TObject;
//        ErrorValue: EFIBError; KindIBError: TKindIBError; var DoRaise: Boolean);
//        var
//        STR:TStringList;
//        Lasterror: string;
//        FKindIBError: string;
//        begin
//        STR:=TStringList.Create;
//        STR.Add(#13#10 + '===== ErrorHandler FIBErrorEvent =====');
//        STR.Add('Sender.ClassName = ' + Sender.ClassName);
//        STR.Add('Sender.Name = ' + (Sender as TComponent).Name);
//        if Sender is TFIBQuery then
//        STR.Add('Owner.Name = ' +
//        (Sender as TFIBQuery).Owner.Name);
//        if Sender is TpFIBStoredProc then
//        STR.Add('Sender.StoredProcName = ' +
//        (Sender as TpFIBStoredProc).StoredProcName);
//        STR.Add('ConstraintName = ' +
//        ErrorHandler1.ConstraintName);
//        STR.Add('ExceptionNumber = ' +
//        IntToStr(ErrorHandler1.ExceptionNumber));
//        case ErrorHandler1.LastError of
//        keNoError: Lasterror := 'keNoError';
//        keException: Lasterror := 'keException';
//        keForeignKey: Lasterror := 'keForeignKey';
//        keSecurity: Lasterror := 'keSecurity';
//        keLostConnect: Lasterror := 'keLostConnect';
//        keCheck: Lasterror := 'keCheck';
//        keUniqueViolation: Lasterror := 'keUniqueViolation';
//        keOther: Lasterror := 'keOther';
//        else
//        Lasterror := 'Undefined';
//        end;
//        STR.Add('Lasterror = ' + Lasterror);
//        STR.Add('SQLCode = ' + IntToStr(ErrorValue.SQLCode));
//        STR.Add('IBErrorCode = ' +
//        IntToStr(ErrorValue.IBErrorCode));
//        STR.Add('Message = ' + ErrorValue.Message);
//        STR.Add('IBMessage = ' + ErrorValue.IBMessage);
//        STR.Add('SQLMessage = ' + ErrorValue.SQLMessage);
//        case KindIBError of
//        keNoError: FKindIBError := 'keNoError';
//        keException: FKindIBError := 'keException';
//        keForeignKey: FKindIBError := 'keForeignKey';
//        keSecurity: FKindIBError := 'keSecurity';
//        keLostConnect: Lasterror := 'keLostConnect';
//        keCheck: FKindIBError := 'keCheck';
//        keUniqueViolation: FKindIBError := 'keUniqueViolation';
//        keOther: FKindIBError := 'keOther';
//        else
//        FKindIBError := 'Undefined';
//        end;
//        STR.Add('KindIBError = ' + FKindIBError);
//        STR.SaveToFile('C:\KindIBError.txt');
//        STR.Free;
////  DoRaise := False;
//        end;
//
//        function TDM1.GetTermnalID(ID_TERMINAL: string):integer;
//        begin
//        Result:=0;
//        sqlFreeReturn.Close;
//        sqlFreeReturn.SelectSQL.Text:='SELECT ID FROM TERMINAL WHERE terminal_id=:ID_TERMINAL';
//        sqlFreeReturn.ParamByName('ID_TERMINAL').asString:=ID_TERMINAL;
//        sqlFreeReturn.Open;
//        Result:=sqlFreeReturn.FieldByName('ID').asInteger;
//        end;
//
//        function TDM1.ConnectFIB(USER:string;PASSWD:string;DB:string):boolean;
//        begin
//        FIB.Connected:=false;
//        FIB.DBParams.Clear;
//        FIB.DBParams.Add('user_name='+USER);
//        FIB.DBParams.Add('password='+PASSWD);
//        FIB.DBParams.Add('lc_ctype=WIN1251');
//        FIB.DBName:=DB;
//        try
//        FIB.Connected:=true;
//        FIBTransaction.Active:=true;
//        //Определения вывода значений даты и времени
//        SysUtils.ShortDateFormat:='dd.mm.yyyy';
//        SysUtils.DateSeparator:='.';
//        SysUtils.ShortTimeFormat:='hh:mm:ss';
//        SysUtils.TimeSeparator:=':';
//        Result:=true;
//        except
//        Result:=false;
//        end;
//        end;
//
//        function TDM1.ConnectFIBWORK(USER:string;PASSWD:string;DB_WORK:string):boolean;
//        begin
//        FIBWORKS.Connected:=false;
//        FIBWORKS.DBParams.Clear;
//        FIBWORKS.DBParams.Add('user_name='+USER);
//        FIBWORKS.DBParams.Add('password='+PASSWD);
//        FIBWORKS.DBParams.Add('lc_ctype=WIN1251');
//        FIBWORKS.DBName:=DB_WORK;
//        try
//        FIBWORKS.Connected:=true;
//        FIBTransaction_working.Active:=true;
//        //Определения вывода значений даты и времени
//        SysUtils.ShortDateFormat:='dd.mm.yyyy';
//        SysUtils.DateSeparator:='.';
//        SysUtils.ShortTimeFormat:='hh:mm:ss';
//        SysUtils.TimeSeparator:=':';
//        Result:=true;
//        except
//        Result:=false;
//        end;
//        end;
//
////Получает ID терминала с проверкой на открытую смену
//        function TDM1.TerminalID(DATA:TStringList):integer;
//        begin
//        sqlFreeReturn.Close;
//        sqlFreeReturn.SelectSQL.Text:='SELECT ADDRES,ID, BANK_ID FROM TERMINAL WHERE TERMINAL_ID=:ID_TERMINAL AND BANK_ID =(SELECT BANK FROM USERS WHERE LOGIN=:LOGIN) AND (SELECT count(*) FROM SMENA WHERE DATA_K is null and SMENA.id_terminal=TERMINAL.ID)>0';
//        sqlFreeReturn.ParamByName('ID_TERMINAL').asString:=DATA.VALUES['ID_TERMINAL'];
//        sqlFreeReturn.ParamByName('LOGIN').asString:=DATA.VALUES['LOGIN'];
//        try
//        sqlFreeReturn.Open;
//        //Определения вывода значений даты и времени
//        SysUtils.ShortDateFormat:='dd.mm.yyyy';
//        SysUtils.DateSeparator:='.';
//        SysUtils.ShortTimeFormat:='hh:mm:ss';
//        SysUtils.TimeSeparator:=':';
//        Result:=sqlFreeReturn.FieldByName('ID').asInteger;
//        except
//        Result:=0
//        end;
//        end;
/////////////////////////////////////////////////////////////
//
/////////////////////////////////////////////////////////////////////////////////////////////////////
////Функция проверки на правильность даты строковой переменной
//        function IsDate(str: string): Boolean;
//        var
//        dt: TDateTime;
//        begin
//        Result := True;
//        try
//        dt := StrToDateTime(str);
//        except
//        Result := False;
//        end;
//        end;
//
//        {//////////////////////////////Замена подстроки/////////////////////////////////////////}
//        function ReplaceSub(str, sub1, sub2: string): string;
//        var
//        aPos: Integer;
//        rslt: string;
//        begin
//        aPos := Pos(sub1, str);
//        rslt := '';
//        while (aPos <> 0) do
//        begin
//        rslt := rslt + Copy(str, 1, aPos - 1) + sub2;
//        Delete(str, 1, aPos + Length(sub1) - 1);
//        aPos := Pos(sub1, str);
//        end;
//        Result := rslt + str;
//        end;
//
//        {//////////////////////////////////////////////////////////////////////////////////////}
