package protocol.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;

/**
 * Команда startsmen
 * Выполняет идентификацию открытия смены и присвоению текущей смене уникального индекса, запускается при включении
 * терминала или открытия кассы, команда выполняется в несколько этапов:
 * 1.	startsmen при успешном выполнении возвращает WSTART и ожидает передачи данных в формате TString (массив строк)
 * 2.	Передача параметров в формате TString (массив строк)
 * Наименования параметров:
 * ID_TERMINAL = Идентификатор терминала, обязательный параметр;
 * LOGIN = Выданный логин, обязательный параметр;
 * <p>
 * В случае неправильного написание наименования параметров, параметр будет проигнорирован, и заполнен значением по умолчанию.
 * В случае не заполнения одного из обязательных параметров сервер вернет ошибку выполнения команды.
 * Параметры могут быть перечислены в любой последовательности.
 * <p>
 * 3.	Возвращение результата выполнения команды
 * В случае успешного выполнения команды возвращается 200 ОК, в случае возникновения какой либо ошибки выводится сообщение 500 ERROR.
 * По завершению работы команды происходит отключение от сервера.
 */
public class CommandStartSmen extends AbstractCommand {
    /**
     * первый ответ
     */
    public static final String firstResponse = "WSTART";
    private static final Logger logger = LoggerFactory.getLogger(CommandStartSmen.class);

    /**
     * попытатся распарсить данные команды
     *
     * @param commandData
     */
    public static CommandStartSmen tryParseCommand(String commandData) {
        CommandStartSmen ret = null;
        boolean flOK = false;

        UserAuthenticationData uad = new UserAuthenticationData();
        flOK = Parser.parseUserAndPassword(commandData, uad);

        if (flOK) {
            ret = new CommandStartSmen();
            ret.setUserNameAndPass(uad);
        }

        return ret;
//////////////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'startsmen') then
//        begin
//        AContext.Connection.Socket.WriteLn('WSTART',TEncoding.UTF8);
//        AContext.Connection.Socket.ReadStrings(Str,2,TEncoding.UTF8);
//        AContext.Connection.Socket.WriteLn(DM1.StartSmen(Str,AContext.Connection.Socket.Binding.PeerIP,LOGIN,PASSWD,DB),TEncoding.UTF8);
////    AContext.Connection.Socket.WriteLn('200 OK');
//        end
    }


    @Override
    public void doWorck(ArrayList<String> result, Connection connectionToTerminalDB, Connection connectionToWorkingDB) throws SQLException {
        String SQLText = "select count(id) COUNTS from smena where id_terminal=? and data_k is null ";
        PreparedStatement ps = connectionToTerminalDB.prepareStatement(SQLText);
        int id_term = GetTerminalIDAndCheckSmenaIsOpen(connectionToTerminalDB);
        ps.setInt(1, id_term);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int counts = rs.getInt("COUNTS");
        rs.close();
        ps.close();


        if (counts == 0) {
            SQLText = "SELECT * FROM TERMINAL WHERE terminal_ID=? and BANK_ID=(SELECT BANK FROM USERS WHERE LOGIN=?)";
            ps = connectionToTerminalDB.prepareStatement(SQLText);
            ps.setString(1, userAuthenticationData.name);
            ps.setString(2, userAuthenticationData.pass);
            rs = ps.executeQuery();
            rs.last();
            int recCount = rs.getRow();
            rs.beforeFirst();
            rs.next();
            int smena = rs.getInt("ID");
            rs.close();
            ps.close();

            if (recCount > 0) {
                SQLText = " INSERT INTO SMENA (DATA_N, ID_TERMINAL) VALUES (?, ?) ";
                ps = connectionToTerminalDB.prepareStatement(SQLText);
                ps.setDate(1, (Date) new java.util.Date());
                ps.setInt(2, smena);
                int countChangeString = ps.executeUpdate();
                if (countChangeString != -1) { // ok

                } else { //error
                    result.add("500 Error insert record");
                }

                //todo как возращать результат для сетерных команд
            }
        }


        ////Открытие смены
//        function TDM1.StartSmen(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string):string;
//        begin
//        Result:='500 ERROR';
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN
//
//        sqlNew.Close;
//        sqlNew.SQL.Text:='select count(id) COUNTS from smena where id_terminal=:ID_TERMINAL and data_k is null';
//        sqlNew.ParamByName('ID_TERMINAL').asInteger:=GetTermnalID(DATA.Values['ID_TERMINAL']);
//        sqlNew.ExecQuery;
//
//        if sqlNew.FieldByName('COUNTS').AsInteger=0 then
//        begin
//
//        sqlFreeReturn.Close;
//        sqlFreeReturn.SelectSQL.Text:='SELECT * FROM TERMINAL WHERE terminal_ID=:ID_TERMINAL and BANK_ID=(SELECT BANK FROM USERS WHERE LOGIN=:LOGIN)';
//        sqlFreeReturn.ParamByName('ID_TERMINAL').asString:=trim(DATA.Values['ID_TERMINAL']);
//        sqlFreeReturn.ParamByName('LOGIN').asString:=trim(DATA.Values['LOGIN']);
//        sqlFreeReturn.Open;
//
//
//        if sqlFreeReturn.RecordCount>0 then
//        begin
//        sqlNew.Close;
//        sqlNew.SQL.Clear;
//        sqlNew.SQL.Add('INSERT INTO SMENA (DATA_N, ID_TERMINAL) VALUES');
//        sqlNew.SQL.Add('(:DATA_N, :ID_TERMINAL)');
//        sqlNew.ParamByName('DATA_N').AsDateTime:=Now();
//        sqlNew.ParamByName('ID_TERMINAL').asInteger:=sqlFreeReturn.FieldByName('ID').asInteger;
//        //sqlNew.ParamByName('ID_LOGIN').asInteger:=ID_LOGIN;
//        //sqlNew.SQL.SaveToFile('C:\123.txt');
//        try
//        sqlNew.Transaction.StartTransaction;
//        sqlNew.ExecQuery;
//        sqlNew.Transaction.CommitRetaining;
//        Result:='200 OK';
//        except
//        Result:='500 Error insert record';
//        Exit;
//        end;
//        end
//        else begin Result:='500 Users not found'; Exit; end;
//        end else Result:='400  smen opened'; //Конец проверки условия открытой смены
//        END ELSE result:='500 Error connect FIB';
//        end;
//
    }
}



