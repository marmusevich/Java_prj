package protocol.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.ArrayList;

//Команда gettable
//        Выполняет процедуру получения значений справочников в формате TString (массив строк)
//        1.	gettable при успешном выполнении возвращает GTABLE и ожидает передачи данных в формате TString (массив строк)
//        2.	Передача параметров в формате TString (массив строк)
//        Наименования параметров:
//        ID_TERMINAL = Идентификатор терминала, обязательный параметр;
//        LOGIN = Выданный логин, обязательный параметр;
//        TABLE = Наименование таблицы в верхнем регистре, обязательный параметр;
//
//        Количество передаваемых параметров – три. В случае неправильного написание наименования параметров, параметр будет проигнорирован, и заполнен значением по умолчанию. В случае не заполнения одного из обязательных параметров сервер вернет ошибку выполнения команды.  Параметры могут быть перечислены в любой последовательности.
//
//        3.	Далее сервер возвращает число количества строк в возвращаемом параметре TString (массив строк)
//        4.	После возвращает значение TString (массив строк) с заполненными данными, которые представляются в виде значений разделенными вертикальной чертой “|”, (Значение|Значение1|Значение2 и т.д.)
//
//        Для каждого справочника формируется своя структура данных с различным перечнем и количеством значений. Наименования значений равнозначны наименованию полей в таблицах для удобства использования. В предлагаемом перечне значений важен порядок перечисления значений.
//        Справочник улиц [STREET]
//        NP – порядковый номер в справочнике
//        TIP – код типа улицы
//        NAME - наименование
//        NAME_U – наименование украинское
//        ACTIV – признак активности улицы
//        CITY – код города (на данный момент данное поле не используется)
//        NAME_PRINT – наименование улицы для печати
//        Справочник городов [CITY]
//        NP – порядковый номер в справочнике
//        NAME – Наименование города
//        KOD – код города
//        KTO_V –Кто вносил (служебное поле, для контроля)
//        ACTIV – Признак активности города
//        NAME_U – Наименование города украинское
//        Справочник банков [BANK]
//        ID – порядковый номер в справочнике
//        NAME – Наименование банка
//        MFO – МФО Банка
//        Справочник филиалов банка [FILIAL]
//        ID – порядковый номер в справочнике
//        NAME – Наименование филиала банка
//        KOD – Код филиала банка
//        BANK_ID – Идентификатор банка
//
//        Справочник видов услуг [VIDUSLUGI]
//        NP – порядковый номер в справочнике
//        JEK – код жека (на данный момент не используется)
//        NAME – наименование услуги
//        KOD_ORG – код организации из справочника организации,
//        GROUPS – код группы услуг
//        NAME_PL – наименование используемое для печати
//        NAZNACHENIE – назначение платежа
//        Справочник организаций [ORGANIZATION]
//        ID – Порядковый номер
//        NAME – наименование организации
//        MFO – МФО организации
//        NS – расчетный счет организации
//        BANK – Наименование банка организации
//        KOD_OKPO – ОКПО организации
//        FIO_DIR – Фамилия директора организации
//        FIO_GLB – Фамилия главного бухгалтера организации
//        ADDRESS – Адрес организации
//        MAIL_OUT – email организации
//        FONE – телефон организации
//        ACTIVE – признак активности организации
//        KATEGOR – код связи со справочником TIP_ORGANIZATION
//        CITY – код связи со справочником CITY
//        KDD – дополнительный код организации
//        EXTRACTOR_NAME – аббревиатура для формирования выгрузок
//        Справочник типов организаций [TIP_ORGANIZATION]
//        ID – Порядковый номер
//        NAME – наименование типа организации
//        Справочник типов улиц [TIP_STREET]
//        NP – Порядковый номер в справочнике
//        NAME - Наименование
//        NAME_UKR - Наименование украинское
//
//        Справочник терминалов [TERMINAL]
//        ID – Идентификатор терминала
//        NAME – Наименование терминала
//        BANK_ID – Идентификатор банка
//        FILIAL_ID – Идентификатор терминала
//        TERMINAL_ID – Код терминала (присваивается при регистрации)
//        ADDRES – Адрес расположения терминала


/**
 * Created by lexa on 08.12.2016.
 */
public class CommandGetTable extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(CommandGetTable.class);


    /**
     * первый ответ
     */
    public static final String firstResponse = "";

    /**
     * попытатся распарсить данные команды
     * @param commandData
     */
    public static CommandGetTable tryParseCommand(String commandData) {
////*//////////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'gettable') then
//        begin
//        AContext.Connection.Socket.WriteLn('GTABLE',TEncoding.UTF8);
//        AContext.Connection.Socket.ReadStrings(Str,3,TEncoding.UTF8); //Пока указываю жестко количество параметров
//        if (STR.VALUES['TABLE']='STREET') or
//        (STR.VALUES['TABLE']='CITY') or
//        (STR.VALUES['TABLE']='BANK') or
//        (STR.VALUES['TABLE']='FILIAL') or
//        (STR.VALUES['TABLE']='VIDUSLUGI') or
//        (STR.VALUES['TABLE']='ORGANIZATION') or
//        (STR.VALUES['TABLE']='TIP_ORGANIZATION') or
//        (STR.VALUES['TABLE']='TIP_STREET') or
//        (STR.VALUES['TABLE']='KOD_OPLAT') or
//        (STR.VALUES['TABLE']='SOSTAVUSLUG') or
//        (STR.VALUES['TABLE']='TERMINAL')
//        then
//        Results:=DM1.GetTable(Str,AContext.Connection.Socket.Binding.PeerIP,LOGIN,PASSWD,DB,DB_WORK);
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
//        //AContext.Connection.Socket.Close;
//        end


        return null;
    }


    @Override
    public void doWorck(ArrayList<String> result, Connection connectionToTerminalDB, Connection connectionToWorkingDB) {
/////Формирование вывода запроса улицы
//        function TDM1.GetTable(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string;DB_WORK:string):string;
//        var
//        S:string;
//        i:integer;
//        begin
//        Result:='500 ERROR';
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN //Проверка подключения к базе
//        //Проверка корректности подключения терминала подключение возможно только после открытия смены
//        if (TerminalID(DATA)>0)THEN
//        BEGIN
//        //Таблицы BANK, FILIAL, TERMINAL запрашивается из базы терминала
//        if  (trim(DATA.Values['TABLE'])='BANK')or
//        (trim(DATA.Values['TABLE'])='FILIAL')or
//        (trim(DATA.Values['TABLE'])='SOSTAVUSLUG')or
//        (trim(DATA.Values['TABLE'])='TERMINAL')
//        then
//        begin //Подключение к тестовой базе
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN
//        try
//        sqlFree.Close;
//        sqlFree.SelectSQL.Text:='SELECT * FROM '+trim(DATA.VALUES['TABLE']);
//        sqlFree.open;
//        GET_USLUGA:=TStringList.Create;
//        while not sqlFree.EOF do
//        begin
//        S:='';
//        for I := 0 to sqlFree.FieldCount - 1 do
//        begin
//        if S='' then S:=sqlFree.Fields[i].AsString else S:=S+'|'+sqlFree.Fields[i].AsString;
//        end;
//        GET_USLUGA.Add(S);
//        sqlFree.Next;
//        end;
//        Result:='200 OK';
//        Exit;
//        except
//        Result:='500 Error +'+trim(DATA.VALUES['TABLE']);
//        exit;
//        end;
//        END ELSE Result:='500 Error connect database'; //Подключение к базе
//        end //Конец подключения к тестовой базе
//        else
//        begin//Проведем подключение к базе WORKING
//        if ConnectFIBWORK(USER,PASSWD,DB_WORK) then
//        BEGIN
//        try
//        sqlFreeWork.Close;
//        sqlFreeWork.SelectSQL.Text:='SELECT * FROM '+trim(DATA.VALUES['TABLE']);
//        sqlFreeWork.open;
//        GET_USLUGA:=TStringList.Create;
//        while not sqlFreeWork.EOF do
//        begin
//        S:='';
//        for I := 0 to sqlFreeWork.FieldCount - 1 do
//        begin
//        if S='' then S:=sqlFreeWork.Fields[i].AsString else S:=S+'|'+sqlFreeWork.Fields[i].AsString;
//        end;
//        GET_USLUGA.Add(S);
//        sqlFreeWork.Next;
//        end;
//        Result:='200 OK';
//        Exit;
//        except
//        Result:='500 Error +'+trim(DATA.VALUES['TABLE']);
//        exit;
//        end;
//        END ELSE Result:='500 Error connect work database'; //Подключение к рабочей базе
//        END; //Конец подключения к рабочей базе
//        END ELSE BEGIN Result:='500 Not open smena'; Exit; END;
//        END ELSE Result:='500 Error connect FIB' //Подключение к базе
//        end;
//
//        procedure TDM1.OpenSSL1GetPassword(var Password: string);
//        begin
//        password:= 'qazwsx12';
////password:= 'aaaa';
//        end;
//
    }
}



