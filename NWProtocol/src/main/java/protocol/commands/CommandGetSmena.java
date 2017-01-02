package protocol.commands;

//Команда getsmena
//        Выполняет процедуру получения данных о сменах начиная от указанной даты, в формате TString (массив строк)
//        1.	getsmena при успешном выполнении возвращает GSMENA и ожидает передачи данных в формате TString (массив строк)
//        2.	Передача переменной количества параметров в списке TString (массив строк) передается числовым значением.
//        3.	Передача параметров в формате TString (массив строк)
//        Наименования параметров:
//        ID_TERMINAL = Идентификатор терминала, обязательный параметр;
//        LOGIN = Выданный логин, обязательный параметр;
//        DATA_N = Дата начала выбора смен;
//
//        В случае неправильного написание наименования параметров, параметр будет проигнорирован, и заполнен значением по умолчанию. В случае не заполнения одного из обязательных параметров сервер вернет ошибку выполнения команды.  Параметры могут быть перечислены в любой последовательности.
//
//        4.	Далее сервер возвращает число количества строк в возвращаемом параметре TString (массив строк)
//        5.	После возвращает значение TString (массив строк) с заполненными данными, которые представляются в виде значений разделенными вертикальной чертой “|”, (Значение|Значение1|Значение2 и т.д.)


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * Created by lexa on 08.12.2016.
 */
public class CommandGetSmena extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(CommandGetSmena.class);

    @Override
    public void doWorck(ArrayList<String> result, Connection connectionToTerminalDB, Connection connectionToWorkingDB) {
    }
}
////*//////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'getsmena') then
//        begin
//        AContext.Connection.Socket.WriteLn('GSMENA');
//        counts:=StrToIntDef(AContext.Connection.Socket.ReadLn(TEncoding.UTF8),1);
//        AContext.Connection.Socket.ReadStrings(Str,counts,TEncoding.UTF8);
//        {Подключение функции проверки и занесения данных в базу}
//        Results:=DM1.GetSmena(Str,AContext.Connection.Socket.Binding.PeerIP,LOGIN,PASSWD,DB);
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


////Получение номеров смены по начиная с указанной даты
//        function TDM1.GetSmena(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string):string;
//        var
//        S:string;
//        i:integer;
//        begin
//        Result:='500 ERROR';
//        GET_USLUGA:=TStringList.Create;
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN //Проверка подключения к базе
//        //Проверка корректности подключения терминала подключение возможно только после открытия смены
//        //if (TerminalID(DATA)>0)THEN
//        //BEGIN
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN
//        try
//        SysUtils.ShortDateFormat:='dd.mm.yyyy';
//        SysUtils.DateSeparator:='.';
//        SysUtils.ShortTimeFormat:='hh:mm:ss';
//        SysUtils.TimeSeparator:=':';
//        sqlFree.Close;
//        sqlFree.SelectSQL.Clear;
//        sqlFree.SelectSQL.Add('select ID, DATA_N, DATA_K, (select count(id) from oplata');
//        sqlFree.SelectSQL.Add(' where oplata.kod_smen=smena.id) from smena where');
//        sqlFree.SelectSQL.Add('   SMENA.id_terminal=(SELECT ID FROM terminal where TERMINAL_ID=:ID_TERMINAL)');
//        sqlFree.SelectSQL.Add('   and  SMENA.DATA_N>=:DATA_N');
//        sqlFree.SelectSQL.Add('    group by ID, DATA_N, DATA_K');
//        sqlFree.SelectSQL.Add('having (select count(id) from oplata where oplata.kod_smen=smena.id)>0');
//        sqlFree.SelectSQL.Add('order by ID');
//        sqlFree.ParamByName('ID_TERMINAL').AsString:=trim(DATA.VALUES['ID_TERMINAL']);
//        sqlFree.ParamByName('DATA_N').AsDateTime:=StrToDateTimeDef(trim(DATA.Values['DATA_N']),StrToDate('01.01.2012'));
//        sqlFree.open;
//
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
//        if S='' then S:=sqlFree.Fields[i].AsString else S:=S+'|'+sqlFree.Fields[i].AsString;
//        end;
//        end;
//        GET_USLUGA.Add(S);
//        sqlFree.Next;
//        end;
//        Result:='200 OK';
//        Exit;
//        except
//        Result:='500 Error GetSmena';
//        exit;
//        end;
//        END ELSE Result:='500 Error connect database'; //Подключение к базе
//        //END ELSE BEGIN Result:='500 Not open smena'; Exit; END;
//        END ELSE Result:='500 Error connect FIB' //Подключение к базе
//        end;
//
