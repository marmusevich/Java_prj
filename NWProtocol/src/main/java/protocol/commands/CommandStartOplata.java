package protocol.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.ArrayList;

//Команда startoplata
//Выполняет процедуру начала регистрации оплаты. Наращивает значение ID_CURRENCY на единицу.
//        1.	startoplata при успешном выполнении возвращает STARTOPL
//        2.	Возвращение результата выполнения команды
//        В случае успешного выполнения команды возвращается 200 ОК, в случае возникновения какой либо ошибки выводится сообщение 500 ERROR. По завершению работы команды происходит отключение от сервера.
//        Наименования параметров:
//        ID_TERMINAL = Идентификатор терминала, обязательный параметр;
//        LOGIN = Выданный логин, обязательный параметр;
//
//        В случае неправильного написания наименования параметров, параметр будет проигнорирован, и заполнен значением по умолчанию. В случае не заполнения одного из обязательных параметров сервер вернет ошибку выполнения команды.  Параметры могут быть перечислены в любой последовательности.
//
//        3.	Возвращение результата выполнения команды
//        В случае успешного выполнения команды возвращается 200 ОК, в случае возникновения какой либо ошибки выводится сообщение 500 ERROR. По завершению работы команды происходит отключение от сервера.


/**
 * Created by lexa on 08.12.2016.
 */
public class CommandStartOplata extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(CommandStartOplata.class);


    /**
     * первый ответ
     */
    public static final String firstResponse = "";

    /**
     * попытатся распарсить данные команды
     * @param commandData
     */
    public static CommandStartOplata tryParseCommand(String commandData) {
//////////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'startoplata') then
//        begin
//        AContext.Connection.Socket.WriteLn('STARTOPL',TEncoding.UTF8);
//        AContext.Connection.Socket.ReadStrings(Str,2,TEncoding.UTF8);
//        {Подключение функции проверки и занесения данных в базу}
////    AContext.Connection.Socket.WriteLn(
//        Results:=DM1.StartOplata(Str,AContext.Connection.Socket.Binding.PeerIP,LOGIN,PASSWD,DB);
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
//////////////Начало оплаты генерирование номера ID_CURRENCE
//        function TDM1.StartOplata(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string):string;
//        var
//        Str:TStringList;
//        i:integer;
//        begin
//        Result:='500 ERROR';
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN
//        Str:=TStringList.Create;
//        if (TerminalID(DATA)>0) THEN
//        BEGIN
//        //Определим значение генератора пачек денег кешкодера
//        sqlNew.Close;
//        sqlNew.SQL.Clear;
//        sqlNew.SQL.Add('select gen_id(GEN_SETCURRENCE, 1) from rdb$database');
//        try
//        sqlNew.Transaction.StartTransaction;
//        sqlNew.ExecQuery;
//        GET_USLUGA:=TStringList.Create;
//        GET_USLUGA.Add('ID_CURRENCE='+sqlNew.fieldByName('GEN_ID').asString);
//        sqlNew.Transaction.CommitRetaining;
//        Result:='200 OK';
//        except
//        Str.Add('Error exec select gen_id(GEN_SETCURRENCE, 1) from rdb$database');
//        Result:='500 Error SET_GEN';
//        Exit;
//        end;
//
//        END ELSE BEGIN Result:='500 Error open smen'; END;
//        Str.Free;
//        END ELSE Result:='500 Error connect FIB';
//        end;
//
    }
}

