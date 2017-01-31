package protocol.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Команда setcurrence
 * Выполняет процедуру регистрации пачки купюр. При вставке купюры в базу вносится ее номенклатура с указанием
 * цифрового значения текущего генератора купюроприемника. Значение генератора наращивается на единицу лишь
 * после выполнения команды startoplata.
 * 1.	setcurrence при успешном выполнении возвращает SETCUR
 * 2.	Возвращение результата выполнения команды
 * В случае успешного выполнения команды возвращается 200 ОК, в случае возникновения какой либо ошибки выводится сообщение 500 ERROR.
 * По завершению работы команды происходит отключение от сервера.
 * Наименования параметров:
 * ID_TERMINAL = Идентификатор терминала, обязательный параметр;
 * LOGIN = Выданный логин, обязательный параметр;
 * NOM = Номенклатура денежной единицы, обязательный параметр;
 * ID_CURRENCE-Код сессии купюроприемника, значение текущее значение можно получить командой startoplata
 * <p>
 * В случае неправильного написание наименования параметров, параметр будет проигнорирован, и заполнен значением по умолчанию.
 * В случае не заполнения одного из обязательных параметров сервер вернет ошибку выполнения команды.
 * Параметры могут быть перечислены в любой последовательности.
 * <p>
 * 3.	Возвращение результата выполнения команды
 * В случае успешного выполнения команды возвращается 200 ОК, в случае возникновения какой либо ошибки выводится сообщение 500 ERROR.
 * По завершению работы команды происходит отключение от сервера.
 */
public class CommandSetCurrence extends AbstractCommand {
    /**
     * первый ответ
     */
    public static final String firstResponse = "SETCUR";
    private static final Logger logger = LoggerFactory.getLogger(CommandSetCurrence.class);
    int nominal = 0;
    int id_currence = 0;

    /**
     * попытатся распарсить данные команды
     *
     * @param commandData
     */
    public static CommandSetCurrence tryParseCommand(String commandData) {
        CommandSetCurrence ret = null;
        boolean flOK = false;

        UserAuthenticationData uad = new UserAuthenticationData();
        flOK = Parser.parseUserAndPassword(commandData, uad);

        String _nominal = Parser.getParametrData(commandData, "NOM");
        String _id_currence = Parser.getParametrData(commandData, "ID_CURRENCE");

        flOK = flOK && (_nominal != null) && (_id_currence != null);

        if (flOK) {
            ret = new CommandSetCurrence();
            ret.setUserNameAndPass(uad);
            ret.nominal = Integer.parseInt(_nominal);
            ret.id_currence = Integer.parseInt(_id_currence);
        }

        return ret;
////*//////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'setcurrence') then
//        begin
//        AContext.Connection.Socket.WriteLn('SETCUR',TEncoding.UTF8);
//        AContext.Connection.Socket.ReadStrings(Str,4,TEncoding.UTF8);
//        {Подключение функции проверки и занесения данных в базу}
//        AContext.Connection.Socket.WriteLn(DM1.SetCurrence(Str,AContext.Connection.Socket.Binding.PeerIP,LOGIN,PASSWD,DB));
//        //AContext.Connection.Socket.Close;
//        end
    }

    @Override
    public void doWorck(ArrayList<String> result, Connection connectionToTerminalDB, Connection connectionToWorkingDB) throws SQLException {

        int id_term = GetTerminalIDAndCheckSmenaIsOpen(connectionToTerminalDB);

        String SQLText = "select id from smena where  id_terminal = ? " +
                "and data_n=(select max(data_n) from smena where id_terminal = ?) ";

        PreparedStatement ps = connectionToTerminalDB.prepareStatement(SQLText);
        ps.setInt(1, id_term);
        ps.setInt(2, id_term);

        ResultSet rs = ps.executeQuery();
        //todo rs.next(); -->  ...= rs.get... - нельзя получатьб просто так, вдрух нет результата
        rs.next();
        int smena = rs.getInt("ID");
        rs.close();
        ps.close();

        SQLText = "INSERT INTO CURRENCY (NOMINAL, ID_CURRENCE, SMENA) " +
                " VALUES (?, ?, ?) ";

        ps = connectionToTerminalDB.prepareStatement(SQLText);
        ps.setInt(1, nominal);
        ps.setInt(2, id_currence);
        ps.setInt(3, smena);
        int countChangeString = ps.executeUpdate();
        if (countChangeString != -1) { // ok

        } else { //error
            result.add("500 Error insert record");
        }
        ps.close();

        //todo как возращать результат для сетерных команд


////Получение идентификатора почки банкнот из Кешкодера
//        function TDM1.SetCurrence(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string):string;
//        var
//        Str:TStringList;
//        i:integer;
//        ID_TERM,SMENA:integer;
//        begin
//        Result:='500 ERROR';
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN
//        Str:=TStringList.Create;
//        //Проверка корректности подключения терминала подключение возможно только после открытия смены
//        //Если сессия открыта для данного терминала
//        ID_TERM:=TerminalID(DATA);
//        if (ID_TERM>0) THEN
//        BEGIN
//
//        sqlNew.Close;
//        sqlNew.SQL.Clear;
//        sqlNew.SQL.Add('select id from smena where  id_terminal=:ID_TERM');
//        sqlNew.SQL.Add('and data_n=(select max(data_n) from smena where id_terminal=:ID_TERM)');
//        sqlNew.ParamByName('ID_TERM').asInteger:=ID_TERM;
//        sqlNew.ExecQuery;
//        SMENA:=sqlNew.FieldByName('ID').AsInteger;
//
//        sqlNew.Close;
//        sqlNew.SQL.Clear;
//        sqlNew.SQL.Add('INSERT INTO CURRENCY (NOMINAL, ID_CURRENCE, SMENA)');
//        sqlNew.SQL.Add('VALUES');
//        sqlNew.SQL.Add('(:NOMINAL, :ID_CURRENCE, :SMENA)');
//        sqlNew.ParamByName('NOMINAL').asInteger:=StrToIntDef(trim(DATA.VALUES['NOM']),0);
//        sqlNew.ParamByName('ID_CURRENCE').asInteger:=StrToIntDef(trim(DATA.VALUES['ID_CURRENCE']),0);
//        sqlNew.ParamByName('SMENA').asInteger:=SMENA;
//        try
//        sqlNew.Transaction.StartTransaction;
//        sqlNew.ExecQuery;
//        sqlNew.Transaction.CommitRetaining;
//        Result:='200 OK';
//        except
//        Result:='500 Error insert record';//+Data.Text;
//        Str.Add('Error execute sql');
//        for i:=0 to sqlNew.ParamCount-1 do
//        begin
//        Str.Add(sqlNew.ParamName(i)+'='+sqlNew.Params[i].AsString);
//        end;
//        end;
//        END ELSE BEGIN Result:='500 Error open smen'; END;
//        Str.Free;
//        END ELSE Result:='500 Error connect FIB';
//        end;
//
    }
}


