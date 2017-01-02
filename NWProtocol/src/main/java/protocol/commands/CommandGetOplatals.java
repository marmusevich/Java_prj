package protocol.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.ArrayList;

//Команда getoplatals
//        Выполняет процедуру получения данных оплат по лицевому счету получателя LS_POLUCH, в формате TString (массив строк)
//        1.	getoplatals при успешном выполнении возвращает GOPLATALS и ожидает передачи данных в формате TString (массив строк)
//        2.	Передача переменной количества параметров в списке TString (массив строк) передается числовым значением.
//        3.	Передача параметров в формате TString (массив строк)
//        Наименования параметров:
//        ID_TERMINAL = Идентификатор терминала, обязательный параметр;
//        LOGIN = Выданный логин, обязательный параметр;
//        LS_POLUCH= лицевой счет получателя платежа;
//        DATA = Дата от которой начала периода выбора платежей
//        KOD_ORG = Код организации поставщика услуг
//
//        В случае неправильного написание наименования параметров, параметр будет проигнорирован, и заполнен значением по умолчанию. В случае не заполнения одного из обязательных параметров сервер вернет ошибку выполнения команды.  Параметры могут быть перечислены в любой последовательности.
//
//        4.	Далее сервер возвращает число количества строк в возвращаемом параметре TString (массив строк)
//        5.	После возвращает значение TString (массив строк) с заполненными данными, которые представляются в виде значений разделенными вертикальной чертой “|”, (Значение|Значение1|Значение2 и т.д.) . Значение колонок по порядку:
//        •	Идентификатор платежа
//        •	ФИО
//        •	Адрес,
//        •	Сумма,
//        •	Индивидуальный налоговый номер,
//        •	Номер постановления,
//        •	Дата постановления,
//        •	Код организации,
//        •	Код организации подотчетный,
//        •	Код смены,
//        •	Идентификатор терминала,
//        •	Код услуги,
//        •	IP,
//        •	Идентификатор купюроприемника,
//        •	Дата,
//        •	Дата вноса
//        •	Дата изменения
//        •	Кто вносил
//        •	Кто изменял,
//        •	Начальный период,
//        •	Конечный период,
//        •	Лицевой счет единый,
//        •	Лицевой счет получателя,
//        •	Комиссия,
//        •	Комиссия 2,
//        •	Название организации получателя,
//        •	МФО Получателя,
//        •	ОКПО Получателя,
//        •	Банк получателя,
//        •	Расчетный счет получателя,
//        •	Идентификатор сторно,
//        •	Комиссия 3,
//        •	Комиссия 4


/**
 * Created by lexa on 08.12.2016.
 */
public class CommandGetOplatals extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(CommandGetOplatals.class);

    @Override
    public void doWorck(ArrayList<String> result, Connection connectionToTerminalDB, Connection connectionToWorkingDB) {
    }
}

///////////////////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'getoplatals') then
//        begin
//        AContext.Connection.Socket.WriteLn('GOPLATALS');
//        try
//        counts:=StrToIntDef(AContext.Connection.Socket.ReadLn(TEncoding.UTF8),1);
//        except
//        counts := 0;
//        end;
//        AContext.Connection.Socket.ReadStrings(Str,counts,TEncoding.UTF8);
//        {Подключение функции проверки и занесения данных в базу}
//        try
//        Results:=DM1.GetOplataLS(Str,AContext.Connection.Socket.Binding.PeerIP,LOGIN,PASSWD,DB);
//        except
//        AContext.Connection.Socket.WriteLn('500 ERROR',TEncoding.UTF8);
//
//        end;
//        if (Results = '200 OK') and (GET_USLUGA.Count>0) then
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
//        AContext.Connection.Socket.WriteLn('400 None records',TEncoding.UTF8);
//        AContext.Connection.Socket.Close;
//        AContext.Connection.Disconnect;
//        end


////Получение данных оплаты по LS_POLUCH лицевому счету получателя
//        function TDM1.GetOplataLS(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string):string;
//        var
//        S:string;
//        i:integer;
//        begin
//        Result:='500 ERROR';
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN
//        //Проверка подключения к базе
//        //Проверка корректности подключения терминала подключение возможно только после открытия смены
//        //if (TerminalID(DATA)>0)THEN
////   BEGIN
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN
//        try
//        sqlFree.Close;
//        sqlFree.SelectSQL.Clear;
//        sqlFree.SelectSQL.Add('SELECT * FROM oplata');
//        sqlFree.SelectSQL.Add(' WHERE');
//        sqlFree.SelectSQL.Add('OPLATA.LS_POLUCH LIKE :LS_POLUCH and DATA_V>=:DATA and KOD_ORG=:KOD_ORG');
//        sqlFree.ParamByName('LS_POLUCH').AsString:=trim(DATA.VALUES['LS_POLUCH']);
//        sqlFree.ParamByName('DATA').AsDate:=StrToDate(trim(DATA.VALUES['DATA']));
//        sqlFree.ParamByName('KOD_ORG').AsInteger:=StrToInt64Def(trim(DATA.VALUES['KOD_ORG']),6);
//        sqlFree.open;
//
//        GET_USLUGA:=TStringList.Create;
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
//        Result:='500 Error getoplata';
//        exit;
//        end;
//        END ELSE Result:='500 Error connect database'; //Подключение к базе
//        //END ELSE BEGIN Result:='500 Not open smena'; Exit; END;
//        END ELSE Result:='500 Error connect FIB' //Подключение к базе
//        end;
//
