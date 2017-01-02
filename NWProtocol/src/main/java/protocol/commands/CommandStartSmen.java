package protocol.commands;


//Команда startsmen
//        Выполняет идентификацию открытия смены и присвоению текущей смене уникального индекса, запускается при включении терминала или открытия кассы, команда выполняется в несколько этапов:
//        1.	startsmen при успешном выполнении возвращает WSTART и ожидает передачи данных в формате TString (массив строк)
//        2.	Передача параметров в формате TString (массив строк)
//        Наименования параметров:
//        ID_TERMINAL = Идентификатор терминала, обязательный параметр;
//        LOGIN = Выданный логин, обязательный параметр;
//
//        В случае неправильного написание наименования параметров, параметр будет проигнорирован, и заполнен значением по умолчанию. В случае не заполнения одного из обязательных параметров сервер вернет ошибку выполнения команды.  Параметры могут быть перечислены в любой последовательности.
//
//        3.	Возвращение результата выполнения команды
//        В случае успешного выполнения команды возвращается 200 ОК, в случае возникновения какой либо ошибки выводится сообщение 500 ERROR. По завершению работы команды происходит отключение от сервера.


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * Created by lexa on 08.12.2016.
 */
public class CommandStartSmen extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(CommandStartSmen.class);

    @Override
    public void doWorck(ArrayList<String> result, Connection connectionToTerminalDB, Connection connectionToWorkingDB) {
    }
}

//////////////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'startsmen') then
//        begin
//        AContext.Connection.Socket.WriteLn('WSTART',TEncoding.UTF8);
//        AContext.Connection.Socket.ReadStrings(Str,2,TEncoding.UTF8);
//        AContext.Connection.Socket.WriteLn(DM1.StartSmen(Str,AContext.Connection.Socket.Binding.PeerIP,LOGIN,PASSWD,DB),TEncoding.UTF8);
////    AContext.Connection.Socket.WriteLn('200 OK');
//        end


////Открытие смены
//        function TDM1.StartSmen(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string):string;
//        begin
//        Result:='500 ERROR';
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN
//
//        sqlNew.Close;
//        sqlNew.SQL.Text:='select count(id) COUNTS from smena where id_terminal=:ID_TERMINAL and data_k is null';
//        sqlNew.ParamByName('ID_TERMINAL').asInteger:=GetTermnalID(DATA.Values['ID_TERMINAL']);
//        sqlNew.ExecQuery;
//
//        if sqlNew.FieldByName('COUNTS').AsInteger=0 then
//        begin
//
//        sqlFreeReturn.Close;
//        sqlFreeReturn.SelectSQL.Text:='SELECT * FROM TERMINAL WHERE terminal_ID=:ID_TERMINAL and BANK_ID=(SELECT BANK FROM USERS WHERE LOGIN=:LOGIN)';
//        sqlFreeReturn.ParamByName('ID_TERMINAL').asString:=trim(DATA.Values['ID_TERMINAL']);
//        sqlFreeReturn.ParamByName('LOGIN').asString:=trim(DATA.Values['LOGIN']);
//        sqlFreeReturn.Open;
//
//
//        if sqlFreeReturn.RecordCount>0 then
//        begin
//        sqlNew.Close;
//        sqlNew.SQL.Clear;
//        sqlNew.SQL.Add('INSERT INTO SMENA (DATA_N, ID_TERMINAL) VALUES');
//        sqlNew.SQL.Add('(:DATA_N, :ID_TERMINAL)');
//        sqlNew.ParamByName('DATA_N').AsDateTime:=Now();
//        sqlNew.ParamByName('ID_TERMINAL').asInteger:=sqlFreeReturn.FieldByName('ID').asInteger;
//        //sqlNew.ParamByName('ID_LOGIN').asInteger:=ID_LOGIN;
//        //sqlNew.SQL.SaveToFile('C:\123.txt');
//        try
//        sqlNew.Transaction.StartTransaction;
//        sqlNew.ExecQuery;
//        sqlNew.Transaction.CommitRetaining;
//        Result:='200 OK';
//        except
//        Result:='500 Error insert record';
//        Exit;
//        end;
//        end
//        else begin Result:='500 Users not found'; Exit; end;
//        end else Result:='400  smen opened'; //Конец проверки условия открытой смены
//        END ELSE result:='500 Error connect FIB';
//        end;
//
