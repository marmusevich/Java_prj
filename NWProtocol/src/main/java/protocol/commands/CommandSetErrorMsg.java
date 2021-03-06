package protocol.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Команда seterrormsg
 * Добавляет записи в таблицу TERMINAL_ERROR ошибок, команда выполняется в несколько этапов:
 * 1.	seterrormsg при успешном выполнении возвращает SETERRORMSG ожидает передачи данных в формате TString (массив строк)
 * 2.	Передача переменной количества параметров в списке TString (массив строк) передается числовым значением.
 * 3.	Передача параметров в формате TString (массив строк)
 * Наименования параметров:
 * ID_TERMINAL = Идентификатор терминала, обязательный параметр;
 * LOGIN = Выданный логин, обязательный параметр;
 * ERROR_MSG = Описание ошибки;
 * <p>
 * В случае неправильного написание наименования параметров, параметр будет проигнорирован, и заполнен значением по умолчанию.
 * В случае не заполнения одного из обязательных параметров сервер вернет ошибку выполнения команды.
 * Параметры могут быть перечислены в любой последовательности.
 * В случае успешного выполнения команды возвращается 200 ОК, в случае возникновения какой либо ошибки выводится сообщение 500 ERROR.
 * По завершению работы команды происходит отключение от сервера.
 */
public class CommandSetErrorMsg extends AbstractCommand {
    /**
     * первый ответ
     */
    public static final String firstResponse = "SETERRORMSG";
    private static final Logger logger = LoggerFactory.getLogger(CommandSetErrorMsg.class);
    String error_msg = "";

    /**
     * попытатся распарсить данные команды
     *
     * @param commandData
     */
    public static CommandSetErrorMsg tryParseCommand(String commandData) {
        CommandSetErrorMsg ret = null;
        boolean flOK = false;

        UserAuthenticationData uad = new UserAuthenticationData();
        flOK = Parser.parseUserAndPassword(commandData, uad);

        String _error_msg = Parser.getParametrData(commandData, "ERROR_MSG");

        flOK = flOK && (_error_msg != null);

        if (flOK) {
            ret = new CommandSetErrorMsg();
            ret.setUserNameAndPass(uad);
            ret.error_msg = _error_msg;
        }

        return ret;
/////////////////////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'seterrormsg') then
//        begin
//        AContext.Connection.Socket.WriteLn('SETERRORMSG');
//        counts:=StrToIntDef(AContext.Connection.Socket.ReadLn(TEncoding.UTF8),1);
//        AContext.Connection.Socket.ReadStrings(Str,counts,TEncoding.UTF8);
//        {Подключение функции проверки и занесения данных в базу}
//        Results:=DM1.SetErrorMsg(Str,AContext.Connection.Socket.Binding.PeerIP,LOGIN,PASSWD,DB);
//        AContext.Connection.Socket.WriteLn(Results,TEncoding.UTF8);
//        end
//
    }

    @Override
    public void doWorck(ArrayList<String> result, Connection connectionToTerminalDB, Connection connectionToWorkingDB) throws SQLException {
        String SQLText = " INSERT INTO TERMINAL_ERRORS (ID_TERMINAL ,ERROR_MSG)" +
                " VALUES (?, ?) ";

        int id_term = GetTerminalIDAndCheckSmenaIsOpen(connectionToTerminalDB);
        PreparedStatement ps = connectionToTerminalDB.prepareStatement(SQLText);
        ps.setInt(1, id_term);
        ps.setString(1, error_msg);
        int countChangeString = ps.executeUpdate();
        if (countChangeString != -1) { // ok

        } else { //error
            result.add("500 Error insert record");
        }
        ps.close();

        //todo как возращать результат для сетерных команд


        ////Добавление записи в базу ошибок
//        function TDM1.SetErrorMsg(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string):string;
//        var
//        Str:TStringList;
//        ID_TERMINAL, i:integer;
//        begin
//        Result:='500 ERROR';
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN //Проверка подключения к базе
//        Str:=TStringList.Create;
//        try
//        ID_TERMINAL:=GetTermnalID(DATA.Values['ID_TERMINAL']);
//        //Определим значение генератора пачек денег кешкодера
//        sqlNew.Transaction.StartTransaction;
//        sqlNew.Close;
//        sqlNew.SQL.Clear;
//        sqlNew.SQL.Add('INSERT INTO TERMINAL_ERRORS (ID_TERMINAL ,ERROR_MSG)');
//        sqlNew.SQL.Add('VALUES');
//        sqlNew.SQL.Add('(:ID_TERMINAL, :ERROR_MSG)');
//        sqlNew.ParamByName('ID_TERMINAL').AsInteger:=ID_TERMINAL;
//        sqlNew.ParamByName('ERROR_MSG').asString:=trim(DATA.VALUES['ERROR_MSG']);
//        sqlNew.Transaction.StartTransaction;
//        sqlNew.ExecQuery;
//        Result:='200 OK';
//        sqlNew.Transaction.CommitRetaining;
//        except
//        sqlNew.Transaction.RollbackRetaining;
//        Str.Add('except errors params');
//        for i:=0 to sqlNew.ParamCount-1 do
//        begin
//        Str.Add(sqlNew.ParamName(i)+'='+sqlNew.Params[i].AsString);
//        end;
//        end;
//        //***************************************************************
//        Str.Free;
//        END ELSE Result:='500 Error connet to FIB'; //Проверка подключения к базе
//
//        end;
//
//
    }
}



