package protocol.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Команда getdata
 * Выполняет процедуру получения данных по л/с поставщика услуги и коду организации
 * 1.	getdata при успешном выполнении возвращает GDATA и ожидает ввода следующего параметра
 * 2.	Передача переменной количества параметров в списке TString (массив строк) передается числовым значением.
 * 3.	Передача параметров в формате TString (массив строк)
 * Наименования параметров:
 * ID_TERMINAL = Идентификатор терминала, обязательный параметр;
 * LOGIN = Выданный логин, обязательный параметр;
 * LS = Лицевой счет поставщика услуги, обязательный параметр;
 * KOD_ORG = Код организации согласно справочника, обязательный параметр;
 * <p>
 * В случае неправильного написание наименования параметров, параметр будет проигнорирован, и заполнен значением по умолчанию.
 * В случае не заполнения одного из обязательных параметров сервер вернет ошибку выполнения команды.  Параметры могут быть
 * перечислены в любой последовательности.
 * <p>
 * 4.	Далее сервер возвращает число количества строк в возвращаемом параметре TString (массив строк)
 * 5.	После возвращает значение TString (массив строк) с заполненными данными, которые представляются в структуре:
 * •	DTM  - Актуальная дата начислений
 * •	USLUGANAME - Наименование услуги (Либо указание на счетчик и его номер)
 * •	IDEN_SHET – Если это прибор учета то отображается его номер, если нет счетчика то 0
 * •	USLUGA – Код услуги согласно справочника Видов услуг (VIDUSLUGI)*
 * •	POKAZ_PRED – Если это прибор учета то отображаются предыдущие показания если нет прибора то 0
 * •	POKAZ_TEK – Если это прибор учета  то отображаются текущие показания если нет прибора то 0
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
 * Значение данных выделяется в отдельную строку. Параметры разделяются вертикальной чертой “|”  и
 * записываются в строгом порядке указанном выше.
 * <p>
 * * - все значения дополнительных справочников  можно получить командой gettable;
 */
public class CommandGetData extends AbstractCommand {
    /**
     * первый ответ
     */
    public static final String firstResponse = "GDATA";
    private static final Logger logger = LoggerFactory.getLogger(CommandGetData.class);
    String lsStr = "";
    String kod_org = "";

    /**
     * попытатся распарсить данные команды
     *
     * @param commandData
     */
    public static CommandGetData tryParseCommand(String commandData) {
        CommandGetData ret = null;
        boolean flOK = false;

        UserAuthenticationData uad = new UserAuthenticationData();
        flOK = Parser.parseUserAndPassword(commandData, uad);

        String _lsStr = Parser.getParametrData(commandData, "LS");
        String _kod_org = Parser.getParametrData(commandData, "KOD_ORG");
        flOK = flOK && (_lsStr != null) && (_kod_org != null);

        if (flOK) {
            ret = new CommandGetData();
            ret.setUserNameAndPass(uad);
            ret.lsStr = _lsStr;
            ret.kod_org = _kod_org;
        }
        return ret;
    }

    @Override
    public void doWorck(ArrayList<String> result, Connection connectionToTerminalDB, Connection connectionToWorkingDB) throws SQLException {
        String SQLText = " SELECT LS, NAME, KOD_ORG FROM LS_SHET WHERE NAME =? and KOD_ORG=? ";
        PreparedStatement ps = connectionToWorkingDB.prepareStatement(SQLText);
        ps.setString(1, lsStr);
        ps.setString(2, kod_org);
        ResultSet rs = ps.executeQuery();

        //todo rs.next(); -->  ...= rs.get... - нельзя получатьб просто так, вдрух нет результата
        rs.next();
        int ls = rs.getInt("LS");
        rs.close();
        ps.close();

        SQLText = " SELECT * FROM GET_USLUGA(?) WHERE KOD_POLUCH=? ";
        ps = connectionToWorkingDB.prepareStatement(SQLText);
        ps.setInt(1, ls);
        ps.setString(2, kod_org);
        rs = ps.executeQuery();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        while (rs.next()) {
            result.add(
                    dateFormat.format(rs.getDate("DTM")) + "|" +
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

//            String column = null;
//            try {
//                column = new String(rs.getBytes("USLUGANAME"), "windows-1251");
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//
//            System.out.println(column);

        }
        ps.close();

        ////Получение данных по л/с поставщика услуг
//        function TDM1.GetData(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string;DB_WORK:string):string;
//        var
//        LS:integer;
//        begin
//        Result:='500 ERROR';
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN
//        //Проверка корректности подключения терминала подключение возможно только после открытия смены
//        if (TerminalID(Data)>0)THEN
//        BEGIN
//        //Проведем подключение к базе WORKING
//        if ConnectFIBWORK(USER,PASSWD,DB_WORK) then
//        BEGIN
//        //Создадим запрос поиска к базе, сначала получим единый счет по подключению
//        sqlFreeWork.Close;
//        sqlFreeWork.SelectSQL.Text:='SELECT LS, NAME, KOD_ORG FROM LS_SHET WHERE NAME =:LS_DBF and KOD_ORG=:KOD_ORG';
//        sqlFreeWork.ParamByName('LS_DBF').asString:=trim(DATA.VALUES['LS']);
//        sqlFreeWork.ParamByName('KOD_ORG').asString:=trim(DATA.VALUES['KOD_ORG']);
//        try
//        sqlFreeWork.Open;
//        except
//        Result:='500 Error select LS';
//        Exit;
//        end;
//
//        try
//        LS:=sqlFreeWork.FieldByName('LS').asInteger;
//        ///Попробуем вытащить данные во внешнюю переменную GET_USLUGA:TstringList;
//        sqlFreeWork.Close;
//        sqlFreeWork.SelectSQL.Text:='SELECT * FROM GET_USLUGA(:LS) WHERE KOD_POLUCH=:KOD_ORG';
//        sqlFreeWork.ParamByName('LS').asInteger:=LS;
//        sqlFreeWork.ParamByName('KOD_ORG').asString:=trim(DATA.VALUES['KOD_ORG']);
//        sqlFreeWork.open;
//        GET_USLUGA:=TStringList.Create;
//        while not sqlFreeWork.EOF do
//        begin
//        GET_USLUGA.Add(FormatDateTime('dd.mm.yyyy hh:mm:ss',sqlFreeWork.fieldByName('DTM').AsDateTime)+'|'+
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
//        except
//        Result:='500 Error GET_USLUGA';
//        exit;
//        end;
//        END ELSE BEGIN Result:='500 Error connect work database'; Exit; END;
//        END ELSE BEGIN Result:='500 Not open smena'; Exit; END;
//        END ELSE Result:='500 Error connect to FIB';
//        end;
//
    }
}


