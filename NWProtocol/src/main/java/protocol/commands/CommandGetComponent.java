package protocol.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Команда getcomponent
 * Возвращает перечень разрешенных компонентов программы, команда выполняется в несколько этапов:
 * 1.	getcomponent при успешном выполнении возвращает GCOMPONENTи ожидает передачи данных в формате TString (массив строк)
 * 2.	Передача переменной количества параметров в списке TString (массив строк) передается числовым значением.
 * 3.	Передача параметров в формате TString (массив строк)
 * Наименования параметров:
 * ID_TERMINAL = Идентификатор терминала, обязательный параметр;
 * LOGIN = Выданный логин, обязательный параметр;
 * <p>
 * В случае неправильного написание наименования параметров, параметр будет проигнорирован, и заполнен значением по умолчанию.
 * В случае не заполнения одного из обязательных параметров сервер вернет ошибку выполнения команды.  Параметры могут быть
 * перечислены в любой последовательности.
 * 4.	Далее сервер возвращает число количества строк в возвращаемом параметре TString (массив строк)
 * 5.	После возвращает значение TString (массив строк) с заполненными данными, которые представляются в виде значений:
 * - 1_НАЗВАНИЕ_ЗНАЧЕНИЯ=1_ЗНАЧЕНИЕ;
 * В случае успешного выполнения команды возвращается 200 ОК, в случае возникновения какой либо ошибки выводится сообщение 500 ERROR.
 * По завершению работы команды происходит отключение от сервера.
 */
public class CommandGetComponent extends AbstractCommand {
    /**
     * первый ответ
     */
    public static final String firstResponse = "GCOMPONENT";
    private static final Logger logger = LoggerFactory.getLogger(CommandGetComponent.class);
    String terminalId = "";

    /**
     * попытатся распарсить данные команды
     *
     * @param commandData
     */
    public static CommandGetComponent tryParseCommand(String commandData) {
        CommandGetComponent ret = null;
        boolean flOK = false;

        UserAuthenticationData uad = new UserAuthenticationData();
        flOK = Parser.parseUserAndPassword(commandData, uad);

        String _terminalId = Parser.getParametrData(commandData, "ID_TERMINAL");
        flOK = flOK && (_terminalId != null);
        if (flOK) {
            ret = new CommandGetComponent();
            ret.setUserNameAndPass(uad);
            ret.terminalId = _terminalId;
        }
        return ret;
    }

    @Override
    public void doWorck(ArrayList<String> result, Connection connectionToTerminalDB, Connection connectionToWorkingDB) throws SQLException {
        int terminalID = GetTerminalID(connectionToTerminalDB, terminalId);
        String SQLText = " SELECT * from COMPONENT where ID_TERMINAL= ? ";

        PreparedStatement ps = connectionToTerminalDB.prepareStatement(SQLText);
        ps.setInt(1, terminalID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            result.add(rs.getString("ID_COMPONENT"));

        }
        ps.close();


        ////Возвращает перечень разрешенных компонентов программы
//        function TDM1.GetComponent(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string):string;
//        var
//        ID_TERMINAL:integer;
//        Str:TstringList;
//        begin
//        Result:='500 ERROR';
//        GET_USLUGA:=TStringList.Create;
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN
//        Str:=TStringList.Create;
//        sqlNew.Close;
//        sqlNew.SQL.Clear;
//        //Проверка корректности подключения терминала подключение возможно только после открытия смены
//        if (TerminalID(DATA)>0) THEN
//        BEGIN
//        //Получим внутренний ID терминала
//        ID_TERMINAL:=GetTermnalID(DATA.Values['ID_TERMINAL']);
//
//        sqlNew.SQL.Add('SELECT * from COMPONENT where ID_TERMINAL=:ID_TERMINAL');
//        sqlNew.ParamByName('ID_TERMINAL').asInteger:=ID_TERMINAL;
//
//        try
//        sqlNew.Transaction.StartTransaction;
//        sqlNew.ExecQuery;
//        sqlNew.Transaction.CommitRetaining;
//        while not sqlNew.EOF do
//        begin
//        GET_USLUGA.Add(Trim(sqlNew.FieldByName('ID_COMPONENT').asString));
//        sqlNew.Next;
//        end;
//        Result:='200 OK';
//        Exit;
//        except
//        sqlNew.SQL.SaveToFile('C:\ErrorComponent.txt');
//        Result:='500 Error COMPONENT';
//        exit;
//        end;
//
//        END ELSE BEGIN Result:='500 Not open smena'; Exit; END;
//        Str.Free;
//        END ELSE Result:='500 Error connect FIB';
//        end;
    }
}


