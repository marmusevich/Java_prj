package protocol.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Команда getoplata
 *         Выполняет процедуру получения данных оплат по идентификатору платежа PAY_ID, в формате TString (массив строк)
 *         1.	getoplata при успешном выполнении возвращает GOPLATA и ожидает передачи данных в формате TString (массив строк)
 *         2.	Передача переменной количества параметров в списке TString (массив строк) передается числовым значением.
 *         3.	Передача параметров в формате TString (массив строк)
 *         Наименования параметров:
 *         ID_TERMINAL = Идентификатор терминала, обязательный параметр;
 *         LOGIN = Выданный логин, обязательный параметр;
 *         PAY_ID= Код платежа;
 *
 * В случае неправильного написание наименования параметров, параметр будет проигнорирован, и заполнен значением по умолчанию.
 * В случае не заполнения одного из обязательных параметров сервер вернет ошибку выполнения команды.
 * Параметры могут быть перечислены в любой последовательности.
 *
 *          4.	Далее сервер возвращает число количества строк в возвращаемом параметре TString (массив строк)
 *          5.	После возвращает значение TString (массив строк) с заполненными данными, которые представляются в
 *          виде значений разделенными вертикальной чертой “|”, (Значение|Значение1|Значение2 и т.д.)
 */
public class CommandGetOplata extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(CommandGetOplata.class);

    /**
     * первый ответ
     */
    public static final String firstResponse = "GOPLATA";

    /**
     * попытатся распарсить данные команды
     * @param commandData
     */
    public static CommandGetOplata tryParseCommand(String commandData) {
        CommandGetOplata ret = null;
        boolean flOK = false;

        UserAuthenticationData uad = new UserAuthenticationData();
        flOK = Parser.parseUserAndPassword(commandData, uad);

        String _pay_id = Parser.getParametrData(commandData, "PAY_ID");
        flOK = flOK && (_pay_id != null);

        if (flOK) {
            ret = new CommandGetOplata();
            ret.setUserNameAndPass(uad);
            ret.pay_id = Long.parseLong(_pay_id);
        }

        return ret;

        ////*//////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'getoplata') then
//        begin
//        AContext.Connection.Socket.WriteLn('GOPLATA');
//        counts:=StrToIntDef(AContext.Connection.Socket.ReadLn(TEncoding.UTF8),1);
//        AContext.Connection.Socket.ReadStrings(Str,counts,TEncoding.UTF8);
//        {Подключение функции проверки и занесения данных в базу}
//        Results:=DM1.GetOplata(Str,AContext.Connection.Socket.Binding.PeerIP,LOGIN,PASSWD,DB);
//        if Results = '200 OK' then
//        begin
//        AContext.Connection.Socket.WriteLn(IntToStr(GET_USLUGA.Count),TEncoding.UTF8);
//        AContext.Connection.Socket.WriteBufferOpen;
//        AContext.Connection.Socket.Write(GET_USLUGA,false,TEncoding.UTF8);
//        AContext.Connection.Socket.WriteBufferClose;
//        AContext.Connection.Socket.WriteLn('200 OK',TEncoding.UTF8);
//        GET_USLUGA.Free;
//        end
//        else
//        begin
//        AContext.Connection.Socket.WriteLn(Results,TEncoding.UTF8);
//        AContext.Connection.Socket.Close;
//        end;
//        // AContext.Connection.Socket.Close;
//        end
    }

    long pay_id = -1;

    @Override
    public void doWorck(ArrayList<String> result, Connection connectionToTerminalDB, Connection connectionToWorkingDB) throws SQLException {
        String SQLText =
                " SELECT * FROM oplata " +
                        " WHERE OPLATA.ID=? ";
        PreparedStatement ps = connectionToTerminalDB.prepareStatement(SQLText);
        ps.setLong(1, pay_id);
        ResultSet rs = ps.executeQuery();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        ResultSetMetaData rsm = rs.getMetaData();
        while (rs.next()) {
            String tmp = "";
            for(int i = 0; i <= rsm.getColumnCount(); i++) {
                if(tmp != ""){
                    // todo DATE - это тип данных фаерберд
                    if(rsm.getColumnTypeName(i) != "DATE")
                        tmp += "|" + rs.getString(i).trim();
                    else // DATE
                        tmp += "|" + dateFormat.format(rs.getDate(i));
                }
                else { // first row
                    if(rsm.getColumnTypeName(i) != "DATE")
                        tmp += rs.getString(i).trim();
                    else // DATE
                        tmp += dateFormat.format(rs.getDate(i));
                }
            }
            result.add( tmp );
        }


////Получение данных оплаты по PAY_ID
//        function TDM1.GetOplata(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string):string;
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
//        sqlFree.SelectSQL.Add('SELECT * FROM oplata');
//        sqlFree.SelectSQL.Add(' WHERE');
//        sqlFree.SelectSQL.Add('OPLATA.ID=:PAY_ID');
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
//        Result:='500 Error getoplata';
//        exit;
//        end;
//        END ELSE Result:='500 Error connect database'; //Подключение к базе
//        //END ELSE BEGIN Result:='500 Not open smena'; Exit; END;
//        END ELSE Result:='500 Error connect FIB' //Подключение к базе
//        end;
//
    }
}
