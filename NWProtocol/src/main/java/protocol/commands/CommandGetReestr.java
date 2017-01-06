package protocol.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Команда getreestr
 *        Выполняет процедуру получения данных о реестре платежей данного платежного терминала по указанной смене, в формате TString (массив строк)
 *        1.	getreestr при успешном выполнении возвращает GREESTR и ожидает передачи данных в формате TString (массив строк)
 *        2.	Передача переменной количества параметров в списке TString (массив строк) передается числовым значением.
 *        3.	Передача параметров в формате TString (массив строк)
 *        Наименования параметров:
 *        ID_TERMINAL = Идентификатор терминала, обязательный параметр;
 *        LOGIN = Выданный логин, обязательный параметр;
 *        KOD_SMEN= Код смены;
 *
 *        В случае неправильного написание наименования параметров, параметр будет проигнорирован, и заполнен значением по умолчанию.
 *        В случае не заполнения одного из обязательных параметров сервер вернет ошибку выполнения команды.
 *        Параметры могут быть перечислены в любой последовательности.
 *
 *        4.	Далее сервер возвращает число количества строк в возвращаемом параметре TString (массив строк)
 *        5.	После возвращает значение TString (массив строк) с заполненными данными, которые представляются в виде
 *        значений разделенными вертикальной чертой “|”, (Значение|Значение1|Значение2 и т.д.)
 */
public class CommandGetReestr extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(CommandGetReestr.class);


    /**
     * первый ответ
     */
    public static final String firstResponse = "GREESTR";

    /**
     * попытатся распарсить данные команды
     * @param commandData
     */
    public static CommandGetReestr tryParseCommand(String commandData) {
        CommandGetReestr ret = null;
        boolean flOK = false;

        UserAuthenticationData uad = new UserAuthenticationData();
        flOK = Parser.parseUserAndPassword(commandData, uad);

        String _kod_smen = Parser.getParametrData(commandData, "KOD_SMEN");

        flOK = flOK && (_kod_smen != null);

        if (flOK) {
            ret = new CommandGetReestr();
            ret.setUserNameAndPass(uad);
            ret.kod_smen = _kod_smen;

        }
        return ret;


        ////*//////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'getreestr') then
//        begin
//        AContext.Connection.Socket.WriteLn('GREESTR');
//        counts:=StrToIntDef(AContext.Connection.Socket.ReadLn(TEncoding.UTF8),1);
//        AContext.Connection.Socket.ReadStrings(Str,counts,TEncoding.UTF8);
//
//        {Подключение функции проверки и занесения данных в базу}
//        Results:=DM1.GetReestr(Str,AContext.Connection.Socket.Binding.PeerIP,LOGIN,PASSWD,DB);
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

    String kod_smen = "";

    @Override
    public void doWorck(ArrayList<String> result, Connection connectionToTerminalDB, Connection connectionToWorkingDB) throws SQLException {
        String SQLText =
                " SELECT oplata.id, oplata.data, oplata.kod_smen, oplata.fio, oplata.organization, oplata.summa, oplata.storno_id, oplata.komisiya, oplata.ls, oplata.ls_poluch FROM oplata WHERE " +
                        " OPLATA.KOD_SMEN = ? ";


        // todo что за хрень параметр VERSIONS не определен
        // todo параметр KOD_SMEN в CommandGetOplataSmena был Long, теперь String
//        sqlNew.SQL.Clear;
//        sqlNew.SQL.Add('UPDATE SMENA SET VERSIONS=:VERSIONS WHERE ID=:KOD_SMEN');
//        sqlNew.ParamByName('VERSIONS').asString:=trim(DATA.Values['VERSIONS']);
//        sqlNew.ParamByName('KOD_SMEN').AsString:=trim(DATA.VALUES['KOD_SMEN']);
//        try
//        sqlNew.Transaction.StartTransaction;
//        sqlNew.ExecQuery;
//        sqlNew.Transaction.CommitRetaining;
//        except
//        Str.Add(sqlNew.SQL.Text);
//        Str.Add('VERSIONS:='+sqlNew.ParamByName('VERSIONS').asString);
//        Str.Add('KOD_SMEN:='+sqlNew.ParamByName('KOD_SMEN').asString);
//        Str.SaveToFile('C:\ErrorVersions.txt');
//        end;


        PreparedStatement ps = connectionToTerminalDB.prepareStatement(SQLText);
        ps.setString(1, kod_smen);
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
        }



////Получение реестра платежей по выбранной смене
//        function TDM1.GetReestr(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string):string;
//        var
//        S:string;
//        Str:TStringList;
//        i:integer;
//        begin
//        Str:=TStringList.Create;
//        Result:='500 ERROR';
//        GET_USLUGA:=TStringList.Create;
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN //Проверка подключения к базе
//        //Проверка корректности подключения терминала подключение возможно только после открытия смены
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN
//        try
//        sqlFree.Close;
//        sqlFree.SelectSQL.Clear;
//        sqlFree.SelectSQL.Add('SELECT oplata.id, oplata.data, oplata.kod_smen, oplata.fio, oplata.organization, oplata.summa, oplata.storno_id, oplata.komisiya, oplata.ls, oplata.ls_poluch FROM oplata');
//        sqlFree.SelectSQL.Add(' WHERE');
//        sqlFree.SelectSQL.Add('OPLATA.KOD_SMEN=:KOD_SMEN');
//        sqlFree.ParamByName('KOD_SMEN').AsString:=trim(DATA.VALUES['KOD_SMEN']);
//        sqlFree.open;
//        ////////////////////////////////////////////////////////////////////////////////////////
//        sqlNew.Close;
//        sqlNew.SQL.Clear;
//        sqlNew.SQL.Add('UPDATE SMENA SET VERSIONS=:VERSIONS WHERE ID=:KOD_SMEN');
//        sqlNew.ParamByName('VERSIONS').asString:=trim(DATA.Values['VERSIONS']);
//        sqlNew.ParamByName('KOD_SMEN').AsString:=trim(DATA.VALUES['KOD_SMEN']);
//        try
//        sqlNew.Transaction.StartTransaction;
//        sqlNew.ExecQuery;
//        sqlNew.Transaction.CommitRetaining;
//        except
//        Str.Add(sqlNew.SQL.Text);
//        Str.Add('VERSIONS:='+sqlNew.ParamByName('VERSIONS').asString);
//        Str.Add('KOD_SMEN:='+sqlNew.ParamByName('KOD_SMEN').asString);
//        Str.SaveToFile('C:\ErrorVersions.txt');
//        end;
//        /////////////////////////////////////////////////////////
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
//        Result:='500 Error +'+trim(DATA.VALUES['TABLE']);
//        exit;
//        end;
//        END ELSE Result:='500 Error connect database'; //Подключение к базе
//        END ELSE Result:='500 Error connect FIB'; //Подключение к базе
//        Str.Free;
//        end;
//
    }
}

