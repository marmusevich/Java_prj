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
    public static String getCammandName(String commandData) {
        String cd = commandData.toUpperCase();
        return getParametrData(cd,"CMD");
    }

    /**
     * вернуть первый ответ
     * @param commandName имя команды
     * @return
     */
    public static String getFirstResponse(String commandName) {
        String ret = "";

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

            case "CHEKSMENA": //
                ret = CommandChekSmena.firstResponse;
                break;

            case "CHKUPDATE": //
                ret = CommandChkUpdate.firstResponse;
                break;

            case "CLOSEOPLATA": //
                ret = CommandCloseOplata.firstResponse;
                break;

            case "FINDADR": //
                ret = CommandFindAdr.firstResponse;
                break;

            case "FINDLS": //
                ret = CommandFindLs.firstResponse;
                break;

            case "GETADDRES": //
                ret = CommandGetAddres.firstResponse;
                break;

            case "GETADDRTERM": //
                ret = CommandGetAddrTerm.firstResponse;
                break;

            case "GETCOMPONENT": //
                ret = CommandGetComponent.firstResponse;
                break;

            case "GETCOUNTER": //
                ret = CommandGetCounter.firstResponse;
                break;

            case "GETDATA": //
                ret = CommandGetData.firstResponse;
                break;

            case "GETDATAFULL": //
                ret = CommandGetDataFull.firstResponse;
                break;

            case "GETKOMISIYA": //
                ret = CommandGetKomisiya.firstResponse;
                break;

            case "GETOPLATA": //
                ret = CommandGetOplata.firstResponse;
                break;

            case "GETOPLATALS": //
                ret = CommandGetOplatals.firstResponse;
                break;

            case "GETOPLATASMENA": //
                ret = CommandGetOplataSmena.firstResponse;
                break;

            case "GETREESTR": //
                ret = CommandGetReestr.firstResponse;
                break;

            case "GETREPORTLIST": //
                ret = CommandGetReportList.firstResponse;
                break;

            case "GETSMENA": //
                ret = CommandGetSmena.firstResponse;
                break;

            case "GETSOSTAV": //
                ret = CommandGetSostav.firstResponse;
                break;

            case "GETTABLE": //
                ret = CommandGetTable.firstResponse;
                break;

            case "LSFINDADR": //
                ret = CommandLsFindAdr.firstResponse;
                break;

            case "SETCOUNTER": //
                ret = CommandSetCounter.firstResponse;
                break;

            case "SETCURRENCE": //
                ret = CommandSetCurrence.firstResponse;
                break;

            case "SETDATA": //
                ret = CommandSetData.firstResponse;
                break;

            case "SETERRORMSG": //
                ret = CommandSetErrorMsg.firstResponse;
                break;

            case "SETSTORNO": //
                ret = CommandSetStorno.firstResponse;
                break;

            case "STARTOPLATA": //
                ret = CommandStartOplata.firstResponse;
                break;

            case "STARTSMEN": //
                ret = CommandStartSmen.firstResponse;
                break;

            case "STOPSMEN": //
                ret = CommandStopSmen.firstResponse;
                break;

            case "UPDATEPRG": //
                ret = CommandUpdatePrg.firstResponse;
                break;


//            default: // неопознаная командв
//                ret = new UnknownCommand.firstResponse;
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

        uad.name = getUserName(commandData);
        uad.pass = getUserPass(commandData);
        flOK = checkIsEmptyUserAndPassword(uad);
        return flOK;
    }


    /**
     * Пытается распарсить команду
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

            case "CHEKSMENA": //
                ret = CommandChekSmena.tryParseCommand(commandData);
                break;

            case "CHKUPDATE": //
                ret = CommandChkUpdate.tryParseCommand(commandData);
                break;

            case "CLOSEOPLATA": //
                ret = CommandCloseOplata.tryParseCommand(commandData);
                break;

            case "FINDADR": //
                ret = CommandFindAdr.tryParseCommand(commandData);
                break;

            case "FINDLS": //
                ret = CommandFindLs.tryParseCommand(commandData);
                break;

            case "GETADDRES": //
                ret = CommandGetAddres.tryParseCommand(commandData);
                break;

            case "GETADDRTERM": //
                ret = CommandGetAddrTerm.tryParseCommand(commandData);
                break;

            case "GETCOMPONENT": //
                ret = CommandGetComponent.tryParseCommand(commandData);
                break;

            case "GETCOUNTER": //
                ret = CommandGetCounter.tryParseCommand(commandData);
                break;

            case "GETDATA": //
                ret = CommandGetData.tryParseCommand(commandData);
                break;

            case "GETDATAFULL": //
                ret = CommandGetDataFull.tryParseCommand(commandData);
                break;

            case "GETKOMISIYA": //
                ret = CommandGetKomisiya.tryParseCommand(commandData);
                break;

            case "GETOPLATA": //
                ret = CommandGetOplata.tryParseCommand(commandData);
                break;

            case "GETOPLATALS": //
                ret = CommandGetOplatals.tryParseCommand(commandData);
                break;

            case "GETOPLATASMENA": //
                ret = CommandGetOplataSmena.tryParseCommand(commandData);
                break;

            case "GETREESTR": //
                ret = CommandGetReestr.tryParseCommand(commandData);
                break;

            case "GETREPORTLIST": //
                ret = CommandGetReportList.tryParseCommand(commandData);
                break;

            case "GETSMENA": //
                ret = CommandGetSmena.tryParseCommand(commandData);
                break;

            case "GETSOSTAV": //
                ret = CommandGetSostav.tryParseCommand(commandData);
                break;

            case "GETTABLE": //
                ret = CommandGetTable.tryParseCommand(commandData);
                break;

            case "LSFINDADR": //
                ret = CommandLsFindAdr.tryParseCommand(commandData);
                break;

            case "SETCOUNTER": //
                ret = CommandSetCounter.tryParseCommand(commandData);
                break;

            case "SETCURRENCE": //
                ret = CommandSetCurrence.tryParseCommand(commandData);
                break;

            case "SETDATA": //
                ret = CommandSetData.tryParseCommand(commandData);
                break;

            case "SETERRORMSG": //
                ret = CommandSetErrorMsg.tryParseCommand(commandData);
                break;

            case "SETSTORNO": //
                ret = CommandSetStorno.tryParseCommand(commandData);
                break;

            case "STARTOPLATA": //
                ret = CommandStartOplata.tryParseCommand(commandData);
                break;

            case "STARTSMEN": //
                ret = CommandStartSmen.tryParseCommand(commandData);
                break;

            case "STOPSMEN": //
                ret = CommandStopSmen.tryParseCommand(commandData);
                break;

            case "UPDATEPRG": //
                ret = CommandUpdatePrg.tryParseCommand(commandData);
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

    //------------------------------------------------------------------------------------------------------------------

    private static String getUserName(String commandData) {
        return getParametrData(commandData, AbstractCommand.UserParametrName);
    }

    private static String getUserPass(String commandData) {
        return getParametrData(commandData, AbstractCommand.PassParametrName);
    }

    private static boolean checkIsEmptyUserAndPassword(UserAuthenticationData uad) {
        return uad.name != null && uad.pass != null;
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
