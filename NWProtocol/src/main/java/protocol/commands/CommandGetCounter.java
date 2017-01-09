package protocol.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Команда getcounter
 * Выполняет процедуру получения данных счетчика по идентификатору платежа PAY_ID, в формате TString (массив строк)
 * 1.	getcounter при успешном выполнении возвращает GOPLATA и ожидает передачи данных в формате TString (массив строк)
 * 2.	Передача переменной количества параметров в списке TString (массив строк) передается числовым значением.
 * 3.	Передача параметров в формате TString (массив строк)
 * Наименования параметров:
 * ID_TERMINAL = Идентификатор терминала, обязательный параметр;
 * LOGIN = Выданный логин, обязательный параметр;
 * PAY_ID= Код платежа;
 * <p>
 * В случае неправильного написание наименования параметров, параметр будет проигнорирован, и заполнен значением по умолчанию.
 * В случае не заполнения одного из обязательных параметров сервер вернет ошибку выполнения команды.
 * Параметры могут быть перечислены в любой последовательности.
 * <p>
 * 4.	Далее сервер возвращает число количества строк в возвращаемом параметре TString (массив строк)
 * 5.	После возвращает значение TString (массив строк) с заполненными данными, которые представляются в виде значений
 * разделенными вертикальной чертой “|”, (Значение|Значение1|Значение2 и т.д.)
 */
public class CommandGetCounter extends AbstractCommand {
    /**
     * первый ответ
     */
    public static final String firstResponse = "GCOUNTER";
    private static final Logger logger = LoggerFactory.getLogger(CommandGetCounter.class);
    String pay_id = "";

    /**
     * попытатся распарсить данные команды
     *
     * @param commandData
     */
    public static CommandGetCounter tryParseCommand(String commandData) {
        CommandGetCounter ret = null;
        boolean flOK = false;

        UserAuthenticationData uad = new UserAuthenticationData();
        flOK = Parser.parseUserAndPassword(commandData, uad);

        String _pay_id = Parser.getParametrData(commandData, "PAY_ID");
        flOK = flOK && (_pay_id != null);

        if (flOK) {
            ret = new CommandGetCounter();
            ret.setUserNameAndPass(uad);
            ret.pay_id = _pay_id;
        }
        return ret;
    }

    @Override
    public void doWorck(ArrayList<String> result, Connection connectionToTerminalDB, Connection connectionToWorkingDB) throws SQLException {
        String SQLText = " SELECT * FROM COUNTER WHERE COUNTER.PAY_ID= ? ";

        PreparedStatement ps = connectionToTerminalDB.prepareStatement(SQLText);
        ps.setString(1, pay_id);
        ResultSet rs = ps.executeQuery();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        ResultSetMetaData rsm = rs.getMetaData();
        while (rs.next()) {
            String tmp = "";
            for (int i = 0; i <= rsm.getColumnCount(); i++) {
                if (tmp != "") {
                    // todo DATE - это тип данных фаерберд
                    if (rsm.getColumnTypeName(i) != "DATE")
                        tmp += "|" + rs.getString(i).trim();
                    else // DATE
                        tmp += "|" + dateFormat.format(rs.getDate(i));
                } else { // first row
                    if (rsm.getColumnTypeName(i) != "DATE")
                        tmp += rs.getString(i).trim();
                    else // DATE
                        tmp += dateFormat.format(rs.getDate(i));
                }
            }
        }


        ////Получение данных счетчиков по PAY_ID
//        function TDM1.GetCounter(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string):string;
//        var
//        S:string;
//        i:integer;
//        begin
//        Result:='500 ERROR';
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN //Проверка подключения к базе
//        //Проверка корректности подключения терминала подключение возможно только после открытия смены
//        //if (TerminalID(DATA)>0)THEN
////   BEGIN
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN
//        try
//        sqlFree.Close;
//        sqlFree.SelectSQL.Clear;
//        sqlFree.SelectSQL.Add('SELECT * FROM COUNTER');
//        sqlFree.SelectSQL.Add(' WHERE');
//        sqlFree.SelectSQL.Add('COUNTER.PAY_ID=:PAY_ID');
//        sqlFree.ParamByName('PAY_ID').AsInteger:=StrToInt64Def(trim(DATA.VALUES['PAY_ID']),0);
//        sqlFree.open;
//
//        GET_USLUGA:=TStringList.Create;
//        while not sqlFree.EOF do
//        begin
//        S:='';
//        for I := 0 to sqlFree.FieldCount - 1 do
//        begin
//        if (DM1.sqlFree.Fields[i].ClassName='TFIBDateTimeField')then
//        begin
//        if S='' then S:=FormatDateTime('dd.mm.yyyy hh:mm:ss',sqlFree.Fields[i].AsDateTime) else S:=S+'|'+FormatDateTime('dd.mm.yyyy hh:mm:ss',sqlFree.Fields[i].AsDateTime);
//        end
//        else
//        begin
//        if S='' then S:=Trim(sqlFree.Fields[i].AsString) else S:=S+'|'+Trim(sqlFree.Fields[i].AsString);
//        end;
//        end;
//        GET_USLUGA.Add(trim(S));
//        sqlFree.Next;
//        end;
//        Result:='200 OK';
//        Exit;
//        except
//        Result:='500 Error getcounter';
//        exit;
//        end;
//        END ELSE Result:='500 Error connect database'; //Подключение к базе
//        //END ELSE BEGIN Result:='500 Not open smena'; Exit; END;
//        END ELSE Result:='500 Error connect FIB' //Подключение к базе
//        end;
//
//
    }
}
