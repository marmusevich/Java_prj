package protocol.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.ArrayList;

//Команда findadr
//        Выполняет функции поиска данных по адресу абонента
//        1.	findadr при успешном выполнении возвращает FINDADR и ожидает передачи данных в формате TString (массив строк)
//        2.	Передача переменной количества параметров в списке TString (массив строк) передается числовым значением.
//        3.	Передача параметров в формате TString (массив строк)
//        Наименования параметров:
//        ID_TERMINAL = Идентификатор терминала, обязательный параметр;
//        LOGIN = Выданный логин, обязательный параметр;
//        CITY = Код города из справочника городов [CITY], обязательный параметр;
//        STREET = Код улицы из справочника улиц [STREET] , обязательный параметр;
//        HOME = Номер дома, обязательный параметр;
//        KORP = Корпус дома, обязательный параметр (в случае отсутствия указывать пустое значение);
//        KV = Номер квартиры, обязательный параметр (в случае отсутствия указывать пустое значение);
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
//        •	POKAZ_TEK – Если это прибор учета,  то отображаются текущие показания если нет прибора то 0
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
//        * - все значения дополнительных справочников  можно получить командой gettable


/**
 * Created by lexa on 08.12.2016.
 */
public class CommandFindAdr extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(CommandFindAdr.class);

    @Override
    public void doWorck(ArrayList<String> result, Connection connectionToTerminalDB, Connection connectionToWorkingDB) {
    }
}


////*///////////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'findadr') then
//        begin
//        AContext.Connection.Socket.WriteLn('FINDADR',TEncoding.UTF8);
//        //прочитаем: код города, код улицы, номер дома, корпус, квартиру.
//        counts:=StrToIntDef(AContext.Connection.Socket.ReadLn(TEncoding.UTF8),1);
//        AContext.Connection.Socket.ReadStrings(Str,counts,TEncoding.UTF8);
//        Results:=DM1.GetFindAdr(Str,AContext.Connection.Socket.Binding.PeerIP,LOGIN,PASSWD,DB,DB_WORK);
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
//        //sqlFreeWork.ParamByName('KOD_ORG').asString:=DATA.VALUES['KOD_ORG'];
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
