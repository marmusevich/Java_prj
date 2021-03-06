package protocol.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Команда lsfindadr
 * Выполняет функции поиска данных по адресу абонента и возвращает список единых лицевых счетов, соответствующих
 * заданному поиску по адресу
 * 1.	lsfindadr при успешном выполнении возвращает LSFINDADR и ожидает передачи данных в формате TString (массив строк)
 * 2.	Передача переменной количества параметров в списке TString (массив строк) передается числовым значением.
 * 3.	Передача параметров в формате TString (массив строк)
 * Наименования параметров:
 * ID_TERMINAL = Идентификатор терминала, обязательный параметр;
 * LOGIN = Выданный логин, обязательный параметр;
 * CITY = Код города из справочника городов [CITY], обязательный параметр;
 * STREET = Код улицы из справочника улиц [STREET] , обязательный параметр;
 * HOME = Номер дома, обязательный параметр;
 * KORP = Корпус дома, обязательный параметр (в случае отсутствия указывать пустое значение);
 * KV = Номер квартиры, обязательный параметр (в случае отсутствия указывать пустое значение);
 * <p>
 * В случае неправильного написание наименования параметров, параметр будет проигнорирован, и заполнен значением по умолчанию.
 * В случае не заполнения одного из обязательных параметров сервер вернет ошибку выполнения команды.
 * Параметры могут быть перечислены в любой последовательности.
 * <p>
 * 4.	Далее сервер возвращает число количества строк в возвращаемом параметре TString (массив строк)
 * 5.	После возвращает значение TString (массив строк) с заполненными данными, которые представляются в структуре:
 * •	LS  - Номер единого лицевого счета
 * •	FIO – Фамилия И.О.  абонента зарегистрированного за услугой поставщиком услуг
 * Значение данных выделяется в отдельную строку. Параметры разделяются вертикальной чертой “|”  и записываются в
 * строгом порядке указанном выше.
 */
public class CommandLsFindAdr extends AbstractCommand {
    /**
     * первый ответ
     */
    public static final String firstResponse = "LSFINDADR";
    private static final Logger logger = LoggerFactory.getLogger(CommandLsFindAdr.class);
    int city = -1;
    String street = "";
    String home = "";
    String korp = "";
    String kv = "";

    /**
     * попытатся распарсить данные команды
     *
     * @param commandData
     */
    public static CommandLsFindAdr tryParseCommand(String commandData) {
        CommandLsFindAdr ret = null;
        boolean flOK = false;

        UserAuthenticationData uad = new UserAuthenticationData();
        flOK = Parser.parseUserAndPassword(commandData, uad);

        String _city = Parser.getParametrData(commandData, "CITY");
        String _street = Parser.getParametrData(commandData, "STREET");
        String _home = Parser.getParametrData(commandData, "HOME");
        String _korp = Parser.getParametrData(commandData, "KORP");
        String _kv = Parser.getParametrData(commandData, "KV");

        flOK = flOK && (_city != null);

        if (flOK) {
            ret = new CommandLsFindAdr();
            ret.setUserNameAndPass(uad);

            ret.city = Integer.parseInt(_city);
            if (_street != null)
                ret.street = _street;
            if (_home != null)
                ret.home = _home;
            if (_korp != null)
                ret.korp = _korp;
            if (_kv != null)
                ret.kv = _kv;
        }
        return ret;

////*///////////////////////////////////////////////////////////////////////
//        //прочитаем: код города, код улицы, номер дома, корпус, квартиру.
//        else if SameText(trim(LCmd), 'lsfindadr') then
//        begin
//        AContext.Connection.Socket.WriteLn('LSFINDADR',TEncoding.UTF8);
//        counts:=StrToIntDef(AContext.Connection.Socket.ReadLn(TEncoding.UTF8),1);
//
//        AContext.Connection.Socket.ReadStrings(Str,counts,TEncoding.UTF8);
//        Results:=DM1.GetLSFindAdr(Str,AContext.Connection.Socket.Binding.PeerIP,LOGIN,PASSWD,DB,DB_WORK);
//        if Results = '200 OK' then
//        begin
//        AContext.Connection.Socket.WriteLn(IntToStr(GET_USLUGA.Count),TEncoding.UTF8);
//        AContext.Connection.Socket.WriteBufferOpen;
//        AContext.Connection.Socket.Write(GET_USLUGA,false,TEncoding.UTF8);
//        AContext.Connection.Socket.WriteBufferClose;
//        AContext.Connection.Socket.WriteLn('200 OK');
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

    @Override
    public void doWorck(ArrayList<String> result, Connection connectionToTerminalDB, Connection connectionToWorkingDB) throws SQLException {
        String SQLText = " SELECT LS, FIO FROM SHETA WHERE CYTI= ? ";
        SQLText += " and ( ? = '' or street_nom = ? ) ";
        SQLText += " and ( ? = '' or HOME = ? ) ";
        SQLText += " and ( ? = '' or KORP = ? ) ";
        SQLText += " and ( ? = '' or KV = ? ) ";

        PreparedStatement ps = connectionToWorkingDB.prepareStatement(SQLText);
        ps.setInt(1, city);
        ps.setString(2, street);
        ps.setString(3, street);
        ps.setString(4, home);
        ps.setString(5, home);
        ps.setString(6, korp);
        ps.setString(7, korp);
        ps.setString(8, kv);
        ps.setString(9, kv);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            result.add(rs.getString("LS") + "|" + rs.getString("FIO"));
        }
        ps.close();


////Получение списа единых л/с по адресу
//        function TDM1.GetLSFindAdr(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string;DB_WORK:string):string;
//        var
//        LS:integer;
//        begin
//        Result:='500 ERROR';
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN
//        //Проверка корректности подключения терминала подключение возможно только после открытия смены
//        if (TerminalID(DATA)>0)THEN
//        BEGIN
//        //Проведем подключение к базе WORKING
//        if ConnectFIBWORK(USER,PASSWD,DB_WORK) then
//        BEGIN
//        //Создадим запрос поиска в базе по адресу
//        sqlFreeWork.Close;
//        sqlFreeWork.SelectSQL.Clear;
//        sqlFreeWork.SelectSQL.Add('SELECT LS, FIO FROM SHETA WHERE CYTI=:CITY');
//        sqlFreeWork.ParamByName('CITY').asInteger:=StrToIntDef(trim(DATA.VALUES['CITY']),3);
//
//        if trim(DATA.VALUES['STREET'])<>'' then  begin
//        sqlFreeWork.SelectSQL.Add(' and street_nom=:STREET');
//        sqlFreeWork.ParamByName('STREET').asString:=trim(DATA.VALUES['STREET']);
//        end;
//        if trim(DATA.VALUES['HOME'])<>'' then  begin
//        sqlFreeWork.SelectSQL.Add(' and HOME=:HOME');
//        sqlFreeWork.ParamByName('HOME').asString:=trim(DATA.VALUES['HOME']);
//        end;
//        if trim(DATA.VALUES['KORP'])<>'' then  begin
//        sqlFreeWork.SelectSQL.Add(' and KORP=:KORP');
//        sqlFreeWork.ParamByName('KORP').asString:=trim(DATA.VALUES['KORP']);
//        end;
//        if trim(DATA.VALUES['KV'])<>'' then begin
//        sqlFreeWork.SelectSQL.Add(' and KV=:KV');
//        sqlFreeWork.ParamByName('KV').asString:=trim(DATA.VALUES['KV']);
//        end;
//        {      sqlFreeWork.SelectSQL.Text:='SELECT LS, FIO FROM SHETA WHERE CYTI=:CITY and STREET_NOM=:STREET and HOME=:HOME and KORP=:KORP and KV=:KV';
//        sqlFreeWork.ParamByName('KV').asString:=DATA.VALUES['KV'];}
//        try
//
//        sqlFreeWork.Open;
//        GET_USLUGA:=TStringList.Create;
//        except
//        sqlFreeWork.SelectSQL.SaveToFile('C:\ErrorLSFINDADR.txt');
//        Result:='500 Error LSFINDADR';
//        Exit;
//        end;
//        while not sqlFreeWork.EOF do
//        begin
//        GET_USLUGA.Add(sqlFreeWork.fieldByName('LS').asString+'|'+
//        sqlFreeWork.FieldByName('FIO').asString);
//        sqlFreeWork.Next;
//        end;
//        Result:='200 OK';
//        Exit;
//        END ELSE BEGIN Result:='500 Error connect work database'; END;
//        END ELSE BEGIN Result:='500 Not open smena';  Exit; END;
//        END ELSE Result:='500 Error connect FIB';
//        end;
//
    }
}



