package protocol.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.ArrayList;


//Команда getdata
//        Выполняет процедуру получения данных по л/с поставщика услуги и коду организации
//        1.	getdata при успешном выполнении возвращает GDATA и ожидает ввода следующего параметра
//        2.	Передача переменной количества параметров в списке TString (массив строк) передается числовым значением.
//        3.	Передача параметров в формате TString (массив строк)
//        Наименования параметров:
//        ID_TERMINAL = Идентификатор терминала, обязательный параметр;
//        LOGIN = Выданный логин, обязательный параметр;
//        LS = Лицевой счет поставщика услуги, обязательный параметр;
//        KOD_ORG = Код организации согласно справочника, обязательный параметр;
//
//        В случае неправильного написание наименования параметров, параметр будет проигнорирован, и заполнен значением по умолчанию. В случае не заполнения одного из обязательных параметров сервер вернет ошибку выполнения команды.  Параметры могут быть перечислены в любой последовательности.
//
//        4.	Далее сервер возвращает число количества строк в возвращаемом параметре TString (массив строк)
//        5.	После возвращает значение TString (массив строк) с заполненными данными, которые представляются в структуре:
//        •	DTM  - Актуальная дата начислений
//        •	USLUGANAME - Наименование услуги (Либо указание на счетчик и его номер)
//        •	IDEN_SHET – Если это прибор учета то отображается его номер, если нет счетчика то 0
//        •	USLUGA – Код услуги согласно справочника Видов услуг (VIDUSLUGI)*
//        •	POKAZ_PRED – Если это прибор учета то отображаются предыдущие показания если нет прибора то 0
//        •	POKAZ_TEK – Если это прибор учета  то отображаются текущие показания если нет прибора то 0
//        •	FIO – Фамилия И.О.  абонента зарегистрированного за услугой поставщиком услуг
//        •	TARIF – Тариф за оказанную услугу
//        •	KOPLATE – Сумма к оплате
//        •	ADDRESS – Адрес абонента зарегистрированного за услугой
//        •	NS – Единый номер лицевого счета
//        •	LS_POLUCH – лицевой счет поставщика услуг.
//        •	KOD_POLUCH – код поставщика услуг согласно справочника организаций (ORGANIZATION)*
//        •	ORGANIZATION – Наименование организации поставщика услуг
//        •	MFO – МФО поставщика услуг
//        •	OKPO – ОКПО поставщика услуг
//        •	BANK – Наименование банка поставщика услуг.
//        •	R_SHET – Расчетный счет поставщика услуг
//        Значение данных выделяется в отдельную строку. Параметры разделяются вертикальной чертой “|”  и записываются в строгом порядке указанном выше.
//
//
//        * - все значения дополнительных справочников  можно получить командой gettable;



public class CommandGetData extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(CommandGetData.class);


    /**
     * первый ответ
     */
    public static final String firstResponse = "";

    /**
     * попытатся распарсить данные команды
     * @param commandData
     */
    public static CommandGetData tryParseCommand(String commandData) {
        CommandData ret = null;
        boolean flOK = false;

        UserAuthenticationData uad = new UserAuthenticationData();
        flOK = Parser.parseUserAndPassword(commandData, uad);

        if (flOK) {
            ret = new CommandData();
            ret.setUserNameAndPass(uad);
        }

        ////*//////////////////////////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'getdata') then
//        begin
//        AContext.Connection.Socket.WriteLn('GDATA',TEncoding.UTF8);
//        counts:=StrToIntDef(AContext.Connection.Socket.ReadLn(TEncoding.UTF8),0);
//        AContext.Connection.Socket.ReadStrings(Str,counts,TEncoding.UTF8);
//        // AContext.Connection.Disconnect;
//        Results:=DM1.GetData(Str,'0',LOGIN,PASSWD,DB,DB_WORK);
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

        return null;
    }


    @Override
    public void doWorck(ArrayList<String> result, Connection connectionToTerminalDB, Connection connectionToWorkingDB) {
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


