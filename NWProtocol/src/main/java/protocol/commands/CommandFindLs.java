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
 * Команда findls
 *        Выполняет функции поиска данных по единому лицевому счету
 *        1.	findls при успешном выполнении возвращает FINDLS и ожидает передачи данных в формате TString (массив строк)
 *        2.	Передача параметров в формате TString (массив строк)
 *        Наименования параметров:
 *        ID_TERMINAL = Идентификатор терминала, обязательный параметр;
 *        LOGIN = Выданный логин, обязательный параметр;
 *        LS = Единый лицевой счет абонента, обязательный параметр;
 *
 *        Количество передаваемых параметров – три. В случае неправильного написание наименования параметров,
 *        параметр будет проигнорирован, и заполнен значением по умолчанию. В случае не заполнения одного из обязательных
 *        параметров сервер вернет ошибку выполнения команды.  Параметры могут быть перечислены в любой последовательности.
 *
 *        3.	Далее сервер возвращает число количества строк в возвращаемом параметре TString (массив строк);
 *        4.	После возвращает значение TString (массив строк) с заполненными данными, которые представляются в структуре:
 *        •	DTM  - Актуальная дата начислений
 *        •	USLUGANAME - Наименование услуги (Либо указание на счетчик и его номер)
 *        •	IDEN_SHET – Если это прибор учета то отображается его номер, если нет счетчика то 0
 *        •	USLUGA – Код услуги согласно справочника Видов услуг (VIDUSLUGI)*
 *        •	POKAZ_PRED – Если это прибор учета, то отображаются предыдущие показания если нет прибора то 0
 *        •	POKAZ_TEK – Если это прибор учета,  то отображаются текущие показания если нет прибора то 0
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
 *        •	CITY – Код города
 *        Значение данных выделяется в отдельную строку. Параметры разделяются вертикальной чертой “|”  и записываются в
 *        строгом порядке указанном выше.
 *
 *        * - все значения дополнительных справочников  можно получить командой gettable
 */
public class CommandFindLs extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(CommandFindLs.class);

    /**
     * первый ответ
     */
    public static final String firstResponse = "";

    /**
     * попытатся распарсить данные команды
     * @param commandData
     */
    public static CommandFindLs tryParseCommand(String commandData) {
        CommandFindLs ret = null;
        boolean flOK = false;

        UserAuthenticationData uad = new UserAuthenticationData();
        flOK = Parser.parseUserAndPassword(commandData, uad);

        String _lsStr = Parser.getParametrData(commandData, "LS");

        flOK = flOK && (_lsStr != null);

        if (flOK) {
            ret = new CommandFindLs();
            ret.setUserNameAndPass(uad);
            ret.ls = Integer.parseInt(_lsStr);
        }
        return ret;

        ////*//////////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'findls') then
//        begin
//        AContext.Connection.Socket.WriteLn('FINDLS',TEncoding.UTF8);
//        AContext.Connection.Socket.ReadStrings(Str,3,TEncoding.UTF8);
//        Results:=DM1.GetFindLS(Str,AContext.Connection.Socket.Binding.PeerIP,LOGIN,PASSWD,DB,DB_WORK);
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
//        end

    }

    int ls;

    @Override
    public void doWorck(ArrayList<String> result, Connection connectionToTerminalDB, Connection connectionToWorkingDB) throws SQLException {
        String SQLText = " SELECT * FROM GET_USLUGA(?) ";
        PreparedStatement ps = connectionToWorkingDB.prepareStatement(SQLText);
        ps.setInt(1, ls);
        ResultSet rs = ps.executeQuery();

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


////Поиск по единому лицевому счету
//        function TDM1.GetFindLS(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string;DB_WORK:string):string;
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
//        IF ConnectFIBWORK(USER,PASSWD,DB_WORK) THEN
//        BEGIN
//        LS:=StrToIntDef(trim(DATA.VALUES['LS']),0);
//        if LS>0 then
//        BEGIN
//        ///Попробуем вытащить данные во внешнюю переменную GET_USLUGA:TstringList;
//        sqlFreeWork.Close;
//        sqlFreeWork.SelectSQL.Text:='SELECT * FROM GET_USLUGA(:LS)';
//        sqlFreeWork.ParamByName('LS').asInteger:=LS;
//        try
//        sqlFreeWork.open;
//        Result:='200 OK';
//        except
//        Result:='500 Error FINDLS (GET_USLUGA)';
//        exit;
//        end;
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
//        end;
//        END ELSE BEGIN Result:='500 Error connect FIBWORK'; END;
//        END ELSE BEGIN Result:='500 Not open smena'; Exit; END;
//        END ELSE Result:='500 error connect FIB';
//        end;
//
    }
}



