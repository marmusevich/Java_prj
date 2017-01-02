package protocol.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.ArrayList;

//Команда closeoplata
//        Выполняет закрытие процедуры оплаты. Значение.
//        4.	closeoplata при успешном выполнении возвращает CLOSEOPL
//        5.	Возвращение результата выполнения команды
//        В случае успешного выполнения команды возвращается 200 ОК, в случае возникновения какой либо ошибки выводится сообщение 500 ERROR. По завершению работы команды происходит отключение от сервера.
//        Наименования параметров:
//        ID_TERMINAL = Идентификатор терминала, обязательный параметр;
//        LOGIN = Выданный логин, обязательный параметр;
//
//        Количество передаваемых параметров – два. В случае неправильного написания наименования параметров, параметр будет проигнорирован, и заполнен значением по умолчанию. В случае не заполнения одного из обязательных параметров сервер вернет ошибку выполнения команды.  Параметры могут быть перечислены в любой последовательности.
//
//        6.	Возвращение результата выполнения команды
//        В случае успешного выполнения команды возвращается 200 ОК, в случае возникновения какой либо ошибки выводится сообщение 500 ERROR. По завершению работы команды происходит отключение от сервера.


/**
 * Created by lexa on 08.12.2016.
 */
public class CommandCloseOplata extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(CommandCloseOplata.class);

    @Override
    public void doWorck(ArrayList<String> result, Connection connectionToTerminalDB, Connection connectionToWorkingDB) {
    }
}

//////////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'closeoplata') then
//        begin
//        AContext.Connection.Socket.WriteLn('CLOSEOPL',TEncoding.UTF8);
//        AContext.Connection.Socket.ReadStrings(Str,2,TEncoding.UTF8);
//        {Подключение функции проверки и занесения данных в базу}
//        AContext.Connection.Socket.WriteLn(DM1.CloseOplata(Str,AContext.Connection.Socket.Binding.PeerIP,LOGIN,PASSWD,DB),TEncoding.UTF8);
//        AContext.Connection.Socket.Close;
//        end


////Закрытие процедуры оплаты
////Процедуру будет отключена как устаревшая
//        function TDM1.CloseOplata(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string):string;
//        var
//        Str:TStringList;
//        begin
//        Result:='500 ERROR';
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN
//        Str:=TStringList.Create;
//        //Проверка корректности подключения терминала подключение возможно только после открытия смены
//        //Если сессия открыта для данного терминала
//        if (TerminalID(DATA)>0) THEN
//        BEGIN
//        {//Определим значение генератора пачек денег кешкодера
//        sqlNew.Close;
//        sqlNew.SQL.Clear;
//        sqlNew.SQL.Add('select gen_id(GEN_SETCURRENCE, 1) from rdb$database');
//        try
//        sqlNew.Transaction.StartTransaction;
//        sqlNew.ExecQuery;
//        sqlNew.Transaction.CommitRetaining; }
//        Result:='200 OK';
//        {except
//        Str.Add('Error exec select gen_id(GEN_SETCURRENCE, 1) from rdb$database');
//        Result:='500 Error SET_GEN';
//        Exit;
//        end; }
//        END ELSE BEGIN Result:='500 Error open smen'; END;
//        Str.Free;
//        END ELSE Result:='500 Error connect FIB';
//        end;
//
