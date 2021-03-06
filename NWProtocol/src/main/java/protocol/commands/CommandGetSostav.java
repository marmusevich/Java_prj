package protocol.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Команда getsostav
 * Возвращает список состава услуг согласно заданному параметру вида услуг (USLUGA)
 * 1.	getsostav при успешном выполнении возвращает GSOSTAV
 * 2.	Возвращение результата выполнения команды
 * В случае успешного выполнения команды возвращается 200 ОК, в случае возникновения какой либо ошибки выводится сообщение 500 ERROR.
 * По завершению работы команды происходит отключение от сервера.
 * Наименования параметров:
 * ID_TERMINAL = Идентификатор терминала, обязательный параметр;
 * LOGIN = Выданный логин, обязательный параметр;
 * USLUGA= Код услуги по которой нужно получить состав услуг, обязательный параметр;
 * KOD_ORG= Код организации по которой нужно получить состав услуг, обязательный параметр;
 * <p>
 * <p>
 * В случае неправильного написания наименования параметров, параметр будет проигнорирован, и заполнен значением по умолчанию.
 * В случае не заполнения одного из обязательных параметров сервер вернет ошибку выполнения команды.
 * Параметры могут быть перечислены в любой последовательности.
 * 3.	Далее сервер возвращает число количества строк в возвращаемом параметре TString (массив строк)
 * 4.	После возвращает значение TString (массив строк) с заполненными данными, которые представляются в структуре:
 * •	USLUGA – Код услуги в которой включен данный состав
 * •	NAME  - Наименование состава услуг
 * •	TIP_RASHETA – Код типа расчета по составу услуг
 * •	ZNACH – Значение коэффициента расчета состава услуг
 * •	KOD_ORG – Код организации для которой выполняется расчет состава услуг, код берется из справочника ORGANIZATION;
 * •	DOP_USLUGA – Код дополнительной услуги;
 * •	DOP_ORGANIZATION  – Код дополнительной организации для которой выполняется расчет состава услуг,
 * код берется из справочника ORGANIZATION;
 * <p>
 * Значение данных выделяется в отдельную строку. Параметры разделяются вертикальной чертой “|”
 * и записываются в строгом порядке указанном выше.
 * 5.	Возвращение результата выполнения команды
 * В случае успешного выполнения команды возвращается 200 ОК, в случае возникновения какой либо ошибки выводится сообщение 500 ERROR.
 * По завершению работы команды происходит отключение от сервера.
 */
public class CommandGetSostav extends AbstractCommand {
    /**
     * первый ответ
     */
    public static final String firstResponse = "GSOSTAV";
    private static final Logger logger = LoggerFactory.getLogger(CommandGetSostav.class);
    String usluga = "";
    String kod_org = "";

    /**
     * попытатся распарсить данные команды
     *
     * @param commandData
     */
    public static CommandGetSostav tryParseCommand(String commandData) {
        CommandGetSostav ret = null;
        boolean flOK = false;

        UserAuthenticationData uad = new UserAuthenticationData();
        flOK = Parser.parseUserAndPassword(commandData, uad);

        String _usluga = Parser.getParametrData(commandData, "USLUGA");
        String _kod_org = Parser.getParametrData(commandData, "KOD_ORG");

        flOK = flOK && (_usluga != null) && (_kod_org != null);

        if (flOK) {
            ret = new CommandGetSostav();
            ret.setUserNameAndPass(uad);
            ret.usluga = _usluga;
            ret.kod_org = _kod_org;
        }

        return ret;
////*//////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'getsostav') then
//        begin
//        AContext.Connection.Socket.WriteLn('GSOSTAV',TEncoding.UTF8);
//        AContext.Connection.Socket.ReadStrings(Str,4,TEncoding.UTF8);
//        {Подключение функции проверки и занесения данных в базу}
//        Results:=DM1.GetSostav(Str,AContext.Connection.Socket.Binding.PeerIP,LOGIN,PASSWD,DB);
//        if Results = '200 OK' then
//        begin
//        AContext.Connection.Socket.WriteLn(IntToStr(GET_USLUGA.Count),TEncoding.UTF8);
//        AContext.Connection.Socket.WriteBufferOpen;
//        AContext.Connection.Socket.Write(GET_USLUGA,true,TEncoding.UTF8);
//        AContext.Connection.Socket.WriteBufferClose;
//        AContext.Connection.Socket.WriteLn('200 OK',TEncoding.UTF8);
//        GET_USLUGA.Free;
//        end
//        else
//        begin
//        AContext.Connection.Socket.WriteLn(Results,TEncoding.UTF8);
//        end;
//        AContext.Connection.Socket.Close;
//        end
    }

    @Override
    public void doWorck(ArrayList<String> result, Connection connectionToTerminalDB, Connection connectionToWorkingDB) throws SQLException {
        String SQLText =
                " SELECT ID, USLUGA, NAME, TIP_RASHETA, ZNACH, KOD_ORG, DOP_USLUGA, DOP_ORGANIZATION, R_SHET, NAZNACHENIE, OKPO FROM SOSTAVUSLUG  " +
                        " WHERE USLUGA=? and KOD_ORG=? ";

        PreparedStatement ps = connectionToTerminalDB.prepareStatement(SQLText);
        ps.setString(1, usluga);
        ps.setString(2, kod_org);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            result.add(
                    rs.getString("USLUGA") + "|" +
                            rs.getString("NAME") + "|" +
                            rs.getString("TIP_RASHETA") + "|" +
                            rs.getString("ZNACH") + "|" +
                            rs.getString("KOD_ORG") + "|" +
                            rs.getString("DOP_USLUGA") + "|" +
                            rs.getString("DOP_ORGANIZATION") + "|" +
                            rs.getString("R_SHET") + "|" +
                            rs.getString("NAZNACHENIE") + "|" +
                            rs.getString("OKPO")
            );
        }
        ps.close();


////Возвращает список состава услуг
//        function TDM1.GetSostav(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string):string;
//        var
//        Str:TstringList;
//        begin
//        Result:='500 ERROR';
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN
//        Str:=TStringList.Create;
//        //Проверка корректности подключения терминала подключение возможно только после открытия смены
//        if (TerminalID(DATA)>0) THEN
//        BEGIN
//        GET_USLUGA:=TStringList.Create;
//        sqlNew.Close;
//        sqlNew.SQL.Text:='SELECT ID, USLUGA, NAME, TIP_RASHETA, ZNACH, KOD_ORG, DOP_USLUGA, DOP_ORGANIZATION, R_SHET, NAZNACHENIE, OKPO FROM SOSTAVUSLUG WHERE USLUGA='+trim(DATA.Values['USLUGA'])+' and KOD_ORG='+trim(DATA.Values['KOD_ORG']);
//        try
//        sqlNew.Transaction.StartTransaction;
//        sqlNew.ExecQuery;
//        while not sqlNew.EOF do
//        begin
//        GET_USLUGA.Append(sqlNew.fieldByName('USLUGA').asString+'|'+
//        sqlNew.fieldByName('NAME').asString+'|'+
//        sqlNew.FieldByName('TIP_RASHETA').asString+'|'+
//        sqlNew.FieldByName('ZNACH').asString+'|'+
//        sqlNew.FieldByName('KOD_ORG').asString+'|'+
//        sqlNew.FieldByName('DOP_USLUGA').asString+'|'+
//        sqlNew.FieldByName('DOP_ORGANIZATION').asString+'|'+
//        sqlNew.FieldByName('R_SHET').asString+'|'+
//        sqlNew.FieldByName('NAZNACHENIE').asString+'|'+
//        sqlNew.FieldByName('OKPO').asString
//        );
//        sqlNew.Next;
//        end;
//        sqlNew.Transaction.CommitRetaining;
//        Result:='200 OK';
//        Exit;
//        except
//        Result:='500 Error SOSTAVUSLUG';
//        exit;
//        end;
//        END ELSE BEGIN Result:='500 Not open smena'; Exit; END;
//        Str.Free;
//        END ELSE Result:='500 Error connect FIB';
//        end;
//
//
    }
}




