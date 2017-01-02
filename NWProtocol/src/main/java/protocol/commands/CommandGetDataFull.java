package protocol.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *Команда getdatafull
 *        Выполняет процедуру получения данных по л/с поставщика услуги (аналогична команде getdata но без привязки к коду организации)
 *        Выполняет поиск как по л/с поставщика услуги так и по единому л/с
 *        1.	getdatafull при успешном выполнении возвращает GDATAFULL и ожидает ввода следующего параметра
 *        2.	Передача переменной количества параметров в списке TString (массив строк) передается числовым значением.
 *        3.	Передача параметров в формате TString (массив строк)
 *        Наименования параметров:
 *        ID_TERMINAL = Идентификатор терминала, обязательный параметр;
 *        LOGIN = Выданный логин, обязательный параметр;
 *        LS = Лицевой счет поставщика услуги, обязательный параметр;
 *
 *        В случае неправильного написание наименования параметров, параметр будет проигнорирован, и заполнен значением по умолчанию.
 *        В случае не заполнения одного из обязательных параметров сервер вернет ошибку выполнения команды.
 *        Параметры могут быть перечислены в любой последовательности.
 *
 *        4.	Далее сервер возвращает число количества строк в возвращаемом параметре TString (массив строк)
 *        5.	После возвращает значение TString (массив строк) с заполненными данными, которые представляются в структуре:
 *        •	DTM  - Актуальная дата начислений
 *        •	USLUGANAME - Наименование услуги (Либо указание на счетчик и его номер)
 *        •	IDEN_SHET – Если это прибор учета то отображается его номер, если нет счетчика то 0
 *        •	USLUGA – Код услуги согласно справочника Видов услуг (VIDUSLUGI)*
 *        •	POKAZ_PRED – Если это прибор учета, то отображаются предыдущие показания если нет прибора то 0
 *        •	POKAZ_TEK – Если это прибор учета  то отображаются текущие показания если нет прибора то 0
 *        •	FIO – Фамилия И.О.  абонента зарегистрированного за услугой поставщиком услуг
 *        •	TARIF – Тариф за оказанную услугу
 *        •	KOPLATE – Сумма к оплате
 *        •	ADDRESS – Адрес абонента зарегистрированного за услугой
 *        •	NS – Единый номер лицевого счета
 *        •	LS_POLUCH – лицевой счет поставщика услуг.
 *        •	KOD_POLUCH – код поставщика услуг согласно справочника организаций (ORGANIZATION)*
 *        •	ORGANIZATION – Наименование организации поставщика услуг
 *        •	MFO – МФО поставщика услуг
 *        •	OKPO – ОКПО поставщика услуг
 *        •	BANK – Наименование банка поставщика услуг.
 *        •	R_SHET – Расчетный счет поставщика услуг
 *        Значение данных выделяется в отдельную строку. Параметры разделяются вертикальной чертой “|”  и
 *        записываются в строгом порядке указанном выше.
 *        * - все значения дополнительных справочников  можно получить командой gettable;
*/
public class CommandGetDataFull extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(CommandGetDataFull.class);

    /**
     * первый ответ
     */
    public static final String firstResponse = "GDATAFULL";

    /**
     * попытатся распарсить данные команды
     * @param commandData
     */
    public static CommandGetDataFull tryParseCommand(String commandData) {
        CommandGetDataFull ret = null;
        boolean flOK = false;

        UserAuthenticationData uad = new UserAuthenticationData();
        flOK = Parser.parseUserAndPassword(commandData, uad);

        String _lsStr = Parser.getParametrData(commandData, "LS");

        flOK = flOK && (_lsStr != null);

        if (flOK) {
            ret = new CommandGetDataFull();
            ret.setUserNameAndPass(uad);
            ret.lsStr = _lsStr;
        }
        return ret;
    }

    String lsStr = "";

    @Override
    public void doWorck(ArrayList<String> result, Connection connectionToTerminalDB, Connection connectionToWorkingDB) throws SQLException {
        String SQLText = " select distinct ls from LS_SHET where NAME =:? or LS =:? ";

        PreparedStatement ps = connectionToWorkingDB.prepareStatement(SQLText);
        ps.setString(1, lsStr);
        ps.setString(1, lsStr);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int ls = rs.getInt("LS");
        rs.close();
        ps.close();

        SQLText = " SELECT * FROM GET_USLUGA(?) ";
        ps = connectionToWorkingDB.prepareStatement(SQLText);
        ps.setInt(1, ls);
        rs = ps.executeQuery();


        SimpleDateFormat dateFormat = new SimpleDateFormat("'200' dd.MM.yyyy HH:mm:ss");


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
        }



////Получение всех возможных данных по л/с поставщика и единому л/с
//        function TDM1.GetDataFull(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string;DB_WORK:string):string;
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
//        sqlFreeWork1.Close;
//        sqlFreeWork1.SelectSQL.Text:='select distinct ls from LS_SHET where NAME =:LS_DBF or LS =:LS_DBF';
//        sqlFreeWork1.ParamByName('LS_DBF').asString:=trim(DATA.VALUES['LS']);
//        try
//        sqlFreeWork1.Open;
//        except
//        Result:='500 Error select LS';
//        Exit;
//        end;
//        GET_USLUGA:=TStringList.Create;
//        while not sqlFreeWork1.EOF do
//        begin
//        try
//        LS:=sqlFreeWork1.FieldByName('LS').asInteger;
//        ///Попробуем вытащить данные во внешнюю переменную GET_USLUGA:TstringList;
//        sqlFreeWork.Close;
//        sqlFreeWork.SelectSQL.Text:='SELECT * FROM GET_USLUGA(:LS)';
//        sqlFreeWork.ParamByName('LS').asInteger:=LS;
//        sqlFreeWork.open;
//        except
//        Result:='500 Error GET_USLUGA';
//        exit;
//        end;
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
//        sqlFreeWork1.Next;
//        END; //FreeWork1
//        Result:='200 OK';
//        Exit;
//        END ELSE BEGIN Result:='500 Error connect work database'; Exit; END;
//        END ELSE BEGIN Result:='500 Not open smena'; Exit; END;
//        END ELSE Result:='500 Error connect to FIB';
//        end;
//
    }
}
