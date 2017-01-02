package protocol.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.ArrayList;

//Команда getaddres
//        Выполняет запрос получения уникальных значений номеров домов, корпусов и квартир из базы абонентов в формат  TString (массив строк). Используется для синхронизации поисковых справочников на стороне клиента.
//        1.	getaddres при успешном выполнении возвращает GADDRES и ожидает передачи данных в формате TString (массив строк)
//        2.	Передача параметров в формате TString (массив строк)
//        Наименования параметров:
//        ID_TERMINAL = Идентификатор терминала, обязательный параметр;
//        LOGIN = Выданный логин, обязательный параметр;
//        PARAMS = Параметр который может принимать 2 значения HOME и KV
//
//        Количество передаваемых параметров – три. В случае неправильного написание наименования параметров, параметр будет проигнорирован, и заполнен значением по умолчанию. В случае не заполнения одного из обязательных параметров сервер вернет ошибку выполнения команды.  Параметры могут быть перечислены в любой последовательности.
//
//        3.	Далее сервер возвращает число количества строк в возвращаемом параметре TString (массив строк)
//        4.	После возвращает значение TString (массив строк) с заполненными данными, которые представляются в виде значений разделенными вертикальной чертой “|”, (Значение|Значение1|Значение2 и т.д.)
//
//        Для каждого параметра формируется своя структура данных с различным перечнем и количеством значений. Наименования значений равнозначны наименованию полей в таблицах для удобства использования. В предлагаемом перечне значений важен порядок перечисления значений.


/**
 * Created by lexa on 08.12.2016.
 */
public class CommandGetAddres  extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(CommandGetAddres.class);

    @Override
    public void doWorck(ArrayList<String> result, Connection connectionToTerminalDB, Connection connectionToWorkingDB) {
    }
}

//        else if SameText(trim(LCmd), 'getaddres') then
//        begin
//        AContext.Connection.Socket.WriteLn('GADDRES',TEncoding.UTF8);
//        AContext.Connection.Socket.ReadStrings(Str,3,TEncoding.UTF8); //Пока указываю жестко количество параметров
//        Results:=DM1.GetAddres(Str,AContext.Connection.Socket.Binding.PeerIP,LOGIN,PASSWD,DB,DB_WORK);
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



////Возвращает список уникальных значений домов, корпусов или квартир
//        function TDM1.GetAddres(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string;DB_WORK:string):string;
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
//        //Проведем подключение к базе WORKING
//        IF ConnectFIBWORK(USER,PASSWD,DB_WORK) THEN
//        BEGIN
//        if trim(DATA.VALUES['PARAMS'])='HOME' then
//        BEGIN
//        sqlFreeWork.Close;
//        sqlFreeWork.SelectSQL.Text:='SELECT DISTINCT HOME, KORP, CYTI FROM SHETA';
//        try
//        sqlFreeWork.open;
//        Result:='200 OK';
//        except
//        Result:='500 Error GETADDRES';
//        exit;
//        end;
//        GET_USLUGA:=TStringList.Create;
//        while not sqlFreeWork.EOF do
//        begin
//        GET_USLUGA.Add(trim(sqlFreeWork.fieldByName('HOME').asString)+'|'+trim(sqlFreeWork.FieldByName('KORP').asString)+'|'+trim(sqlFreeWork.FieldByName('CYTI').asString));
//        sqlFreeWork.Next;
//        end;
//        Result:='200 OK';
//        Exit;
//        END ELSE if trim(DATA.VALUES['PARAMS'])='KV' then
//        BEGIN
//        sqlFreeWork.Close;
//        sqlFreeWork.SelectSQL.Text:='SELECT DISTINCT(KV) FROM SHETA';
//        try
//        sqlFreeWork.open;
//        Result:='200 OK';
//        except
//        Result:='500 Error GETADDRES';
//        exit;
//        end;
//        GET_USLUGA:=TStringList.Create;
//        while not sqlFreeWork.EOF do
//        begin
//        GET_USLUGA.Add(trim(sqlFreeWork.fieldByName('KV').asString)+'|');
//        sqlFreeWork.Next;
//        end;
//        Result:='200 OK';
//        Exit;
//        END;
//        END ELSE BEGIN Result:='500 Error connect FIBWORK'; END;
//        END ELSE BEGIN Result:='500 Not open smena'; Exit; END;
//        Str.Free;
//        END ELSE Result:='500 Error connect FIB';
//        end;
