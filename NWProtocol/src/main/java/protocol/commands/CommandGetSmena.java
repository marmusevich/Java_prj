package protocol.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Команда getsmena
 *         Выполняет процедуру получения данных о сменах начиная от указанной даты, в формате TString (массив строк)
 *         1.	getsmena при успешном выполнении возвращает GSMENA и ожидает передачи данных в формате TString (массив строк)
 *         2.	Передача переменной количества параметров в списке TString (массив строк) передается числовым значением.
 *         3.	Передача параметров в формате TString (массив строк)
 *         Наименования параметров:
 *         ID_TERMINAL = Идентификатор терминала, обязательный параметр;
 *         LOGIN = Выданный логин, обязательный параметр;
 *         DATA_N = Дата начала выбора смен;
 *
 *         В случае неправильного написание наименования параметров, параметр будет проигнорирован, и заполнен значением
 *         по умолчанию. В случае не заполнения одного из обязательных параметров сервер вернет ошибку выполнения команды.
 *         Параметры могут быть перечислены в любой последовательности.
 *
 *         4.	Далее сервер возвращает число количества строк в возвращаемом параметре TString (массив строк)
 *         5.	После возвращает значение TString (массив строк) с заполненными данными, которые представляются в
 *         виде значений разделенными вертикальной чертой “|”, (Значение|Значение1|Значение2 и т.д.)
 */
public class CommandGetSmena extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(CommandGetSmena.class);


    /**
     * первый ответ
     */
    public static final String firstResponse = "GSMENA";

    /**
     * попытатся распарсить данные команды
     * @param commandData
     */
    public static CommandGetSmena tryParseCommand(String commandData) {
        CommandGetSmena ret = null;
        boolean flOK = false;

        UserAuthenticationData uad = new UserAuthenticationData();
        flOK = Parser.parseUserAndPassword(commandData, uad);

        String _id_terminal = Parser.getParametrData(commandData, "ID_TERMINAL");
        String _data_n = Parser.getParametrData(commandData, "DATA_N");

        flOK = flOK && (_id_terminal != null) && (_data_n != null);

        if (flOK) {
            ret = new CommandGetSmena();
            ret.setUserNameAndPass(uad);
            ret.id_terminal = _id_terminal;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            ret.data_n = dateFormat.parse(_data_n, new ParsePosition(0)); //todo поотенциально проблема распарсивания
        }

        return ret;
////*//////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'getsmena') then
//        begin
//        AContext.Connection.Socket.WriteLn('GSMENA');
//        counts:=StrToIntDef(AContext.Connection.Socket.ReadLn(TEncoding.UTF8),1);
//        AContext.Connection.Socket.ReadStrings(Str,counts,TEncoding.UTF8);
//        {Подключение функции проверки и занесения данных в базу}
//        Results:=DM1.GetSmena(Str,AContext.Connection.Socket.Binding.PeerIP,LOGIN,PASSWD,DB);
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

    String id_terminal = "";
    java.util.Date data_n = null;

    @Override
    public void doWorck(ArrayList<String> result, Connection connectionToTerminalDB, Connection connectionToWorkingDB) throws SQLException {
        String SQLText =
                " select ID, DATA_N, DATA_K, (select count(id) from oplata " +
                        " where oplata.kod_smen=smena.id) from smena where " +
                        "   SMENA.id_terminal=(SELECT ID FROM terminal where TERMINAL_ID=:ID_TERMINAL) " +
                        "   and  SMENA.DATA_N>=:DATA_N" +
                        "    group by ID, DATA_N, DATA_K" +
                        "    having (select count(id) from oplata where oplata.kod_smen=smena.id)>0 " +
                        "    order by ID";

        PreparedStatement ps = connectionToTerminalDB.prepareStatement(SQLText);
        ps.setString(1, id_terminal);
        ps.setDate(2, (Date) data_n);
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


        ////Получение номеров смены по начиная с указанной даты
//        function TDM1.GetSmena(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string):string;
//        var
//        S:string;
//        i:integer;
//        begin
//        Result:='500 ERROR';
//        GET_USLUGA:=TStringList.Create;
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN //Проверка подключения к базе
//        //Проверка корректности подключения терминала подключение возможно только после открытия смены
//        //if (TerminalID(DATA)>0)THEN
//        //BEGIN
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN
//        try
//        SysUtils.ShortDateFormat:='dd.mm.yyyy';
//        SysUtils.DateSeparator:='.';
//        SysUtils.ShortTimeFormat:='hh:mm:ss';
//        SysUtils.TimeSeparator:=':';
//        sqlFree.Close;
//        sqlFree.SelectSQL.Clear;
//        sqlFree.SelectSQL.Add('select ID, DATA_N, DATA_K, (select count(id) from oplata');
//        sqlFree.SelectSQL.Add(' where oplata.kod_smen=smena.id) from smena where');
//        sqlFree.SelectSQL.Add('   SMENA.id_terminal=(SELECT ID FROM terminal where TERMINAL_ID=:ID_TERMINAL)');
//        sqlFree.SelectSQL.Add('   and  SMENA.DATA_N>=:DATA_N');
//        sqlFree.SelectSQL.Add('    group by ID, DATA_N, DATA_K');
//        sqlFree.SelectSQL.Add('having (select count(id) from oplata where oplata.kod_smen=smena.id)>0');
//        sqlFree.SelectSQL.Add('order by ID');
//        sqlFree.ParamByName('ID_TERMINAL').AsString:=trim(DATA.VALUES['ID_TERMINAL']);
//        sqlFree.ParamByName('DATA_N').AsDateTime:=StrToDateTimeDef(trim(DATA.Values['DATA_N']),StrToDate('01.01.2012'));
//        sqlFree.open;
//
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
//        if S='' then S:=sqlFree.Fields[i].AsString else S:=S+'|'+sqlFree.Fields[i].AsString;
//        end;
//        end;
//        GET_USLUGA.Add(S);
//        sqlFree.Next;
//        end;
//        Result:='200 OK';
//        Exit;
//        except
//        Result:='500 Error GetSmena';
//        exit;
//        end;
//        END ELSE Result:='500 Error connect database'; //Подключение к базе
//        //END ELSE BEGIN Result:='500 Not open smena'; Exit; END;
//        END ELSE Result:='500 Error connect FIB' //Подключение к базе
//        end;
//
    }
}


