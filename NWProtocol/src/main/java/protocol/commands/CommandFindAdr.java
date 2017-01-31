package protocol.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Команда findadr
 * Выполняет функции поиска данных по адресу абонента
 * 1.	findadr при успешном выполнении возвращает FINDADR и ожидает передачи данных в формате TString (массив строк)
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
 * •	DTM  - Актуальная дата начислений
 * •	USLUGANAME - Наименование услуги (Либо указание на счетчик и его номер)
 * •	IDEN_SHET – Если это прибор учета то отображается его номер, если нет счетчика то 0
 * •	USLUGA – Код услуги согласно справочника Видов услуг (VIDUSLUGI)*
 * •	POKAZ_PRED – Если это прибор учета то отображаются предыдущие показания если нет прибора то 0
 * •	POKAZ_TEK – Если это прибор учета,  то отображаются текущие показания если нет прибора то 0
 * •	FIO – Фамилия И.О.  абонента зарегистрированного за услугой поставщиком услуг
 * •	TARIF – Тариф за оказанную услугу
 * •	KOPLATE – Сумма к оплате
 * •	ADDRESS – Адрес абонента зарегистрированного за услугой
 * •	NS – Единый номер лицевого счета
 * •	LS_POLUCH – лицевой счет поставщика услуг.
 * •	KOD_POLUCH – код поставщика услуг согласно справочника организаций (ORGANIZATION)*
 * •	ORGANIZATION – Наименование организации поставщика услуг
 * •	MFO – МФО поставщика услуг
 * •	OKPO – ОКПО поставщика услуг
 * •	BANK – Наименование банка поставщика услуг.
 * •	R_SHET – Расчетный счет поставщика услуг
 * Значение данных выделяется в отдельную строку. Параметры разделяются вертикальной чертой “|”  и записываются в
 * строгом порядке указанном выше.
 * <p>
 * * - все значения дополнительных справочников  можно получить командой gettable
 */
public class CommandFindAdr extends AbstractCommand {
    /**
     * первый ответ
     */
    public static final String firstResponse = "FINDADR";
    private static final Logger logger = LoggerFactory.getLogger(CommandFindAdr.class);
    int city = 3;
    int street = 0;
    String home = "";
    String korp = "";
    String kv = "";

    /**
     * попытатся распарсить данные команды
     *
     * @param commandData
     */
    public static CommandFindAdr tryParseCommand(String commandData) {
        CommandFindAdr ret = null;
        boolean flOK = false;

        UserAuthenticationData uad = new UserAuthenticationData();
        flOK = Parser.parseUserAndPassword(commandData, uad);

        String _city = Parser.getParametrData(commandData, "CITY");
        String _street = Parser.getParametrData(commandData, "STREET");
        String _home = Parser.getParametrData(commandData, "HOME");
        String _korp = Parser.getParametrData(commandData, "KORP");
        String _kv = Parser.getParametrData(commandData, "KV");

        flOK = flOK && (_city != null) && (_street != null) && (_home != null) && (_korp != null) && (_kv != null);

        if (flOK) {
            ret = new CommandFindAdr();
            ret.setUserNameAndPass(uad);
            ret.city = Integer.parseInt(_city);
            ret.street = Integer.parseInt(_street);
            ret.home = _home;
            ret.korp = _korp;
            ret.kv = _kv;
        }
        return ret;
    }

    @Override
    public void doWorck(ArrayList<String> result, Connection connectionToTerminalDB, Connection connectionToWorkingDB) throws SQLException {
        String SQLText = " SELECT LS FROM SHETA WHERE CYTI=? and STREET_NOM=? and HOME=? and KORP=? and KV=? ";

        PreparedStatement ps = connectionToWorkingDB.prepareStatement(SQLText);
        ps.setInt(1, city);
        ps.setInt(2, street);
        ps.setString(3, home);
        ps.setString(4, korp);
        ps.setString(5, kv);
        ResultSet rs = ps.executeQuery();

        //todo rs.next(); -->  ...= rs.get... - нельзя получатьб просто так, вдрух нет результатаrs.next();
        int ls = rs.getInt("LS");
        rs.close();
        ps.close();

        SQLText = " SELECT * FROM GET_USLUGA(?) ";
        ps = connectionToWorkingDB.prepareStatement(SQLText);
        ps.setInt(1, ls);
        rs = ps.executeQuery();

//  todo а почему здесь без формата
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
//        dateFormat.format(rs.getDate("DTM")) + "|" +

        while (rs.next()) {
            result.add(
                    rs.getString("DTM") + "|" +
                            rs.getString("USLUGANAME") + "|" +
                            rs.getString("IDEN_SHET") + "|" +
                            rs.getString("USLUGA") + "|" +
                            rs.getString("POKAZ_PRED") + "|" +
                            rs.getString("POKAZ_TEK") + "|" +
                            rs.getString("FIO") + "|" +
                            rs.getString("TARIF") + "|" +
                            rs.getString("KOPLATE") + "|" +
                            rs.getString("ADDRESS") + "|" +
                            rs.getString("NS") + "|" +
                            rs.getString("LS_POLUCH") + "|" +
                            rs.getString("KOD_POLUCH") + "|" +
                            rs.getString("ORGANIZATION") + "|" +
                            rs.getString("MFO") + "|" +
                            rs.getString("OKPO") + "|" +
                            rs.getString("BANK") + "|" +
                            rs.getString("R_SHET"));
        }

        ps.close();

        ////Поиск по адрему
//        function TDM1.GetFindAdr(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string;DB_WORK:string):string;
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
//        sqlFreeWork.SelectSQL.Text:='SELECT * FROM SHETA WHERE CYTI=:CITY and STREET_NOM=:STREET and HOME=:HOME and KORP=:KORP and KV=:KV';
//        sqlFreeWork.ParamByName('CITY').asInteger:=StrToIntDef(trim(DATA.VALUES['CITY']),3);
//        sqlFreeWork.ParamByName('STREET').asInteger:=StrToIntDef(trim(DATA.VALUES['STREET']),0);
//        sqlFreeWork.ParamByName('HOME').asString:=trim(DATA.VALUES['HOME']);
//        sqlFreeWork.ParamByName('KORP').asString:=trim(DATA.VALUES['KORP']);
//        sqlFreeWork.ParamByName('KV').asString:=trim(DATA.VALUES['KV']);
//        try
//        sqlFreeWork.Open;
//        except
//        Result:='500 Error FINDADR';
//        Exit;
//        end;
//        LS:=sqlFreeWork.FieldByName('LS').asInteger;
//        if LS>0 then
//        BEGIN
//
//        ///Попробуем вытащить данные во внешнюю переменную GET_USLUGA:TstringList;
//        sqlFreeWork.Close;
//        sqlFreeWork.SelectSQL.Text:='SELECT * FROM GET_USLUGA(:LS)';
//        sqlFreeWork.ParamByName('LS').asInteger:=LS;
//        try
//        sqlFreeWork.open;
//        except
//        Result:='500 Error FINDADR (GET_USLUGA)';
//        exit;
//        end;
//
//        GET_USLUGA:=TStringList.Create;
//        while not sqlFreeWork.EOF do
//        begin
//        GET_USLUGA.Add(sqlFreeWork.fieldByName('DTM').asString+'|'+
//        sqlFreeWork.FieldByName('USLUGANAME').asString+'|'+
//        sqlFreeWork.FieldByName('IDEN_SHET').asString+'|'+
//        sqlFreeWork.FieldByName('USLUGA').asString+'|'+
//        sqlFreeWork.FieldByName('POKAZ_PRED').asString+'|'+
//        sqlFreeWork.FieldByName('POKAZ_TEK').asString+'|'+
//        sqlFreeWork.FieldByName('FIO').asString+'|'+
//        sqlFreeWork.FieldByName('TARIF').asString+'|'+
//        sqlFreeWork.FieldByName('KOPLATE').asString+'|'+
//        sqlFreeWork.FieldByName('ADDRESS').asString+'|'+
//        sqlFreeWork.FieldByName('NS').asString+'|'+
//        sqlFreeWork.FieldByName('LS_POLUCH').asString+'|'+
//        sqlFreeWork.FieldByName('KOD_POLUCH').asString+'|'+
//        sqlFreeWork.FieldByName('ORGANIZATION').asString+'|'+
//        sqlFreeWork.FieldByName('MFO').asString+'|'+
//        sqlFreeWork.FieldByName('OKPO').asString+'|'+
//        sqlFreeWork.FieldByName('BANK').asString+'|'+
//        sqlFreeWork.FieldByName('R_SHET').asString);
//        sqlFreeWork.Next;
//        end;
//        Result:='200 OK';
//        Exit;
//        end;
//        END ELSE BEGIN Result:='500 Error connect work database'; END;
//        END ELSE BEGIN Result:='500 Not open smena';  Exit; END;
//        END ELSE Result:='500 Error connect FIB';
//        end;
//
    }
}



