package protocol.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.ArrayList;

//Команда getaddrterm
//        Возвращает значение адреса терминала
//        1.	getaddrterm при успешном выполнении возвращает GADDRTRM
//        2.	Возвращение результата выполнения команды
//        В случае успешного выполнения команды возвращается 200 ОК, в случае возникновения какой либо ошибки выводится сообщение 500 ERROR. По завершению работы команды происходит отключение от сервера.
//        Наименования параметров:
//        ID_TERMINAL = Идентификатор терминала, обязательный параметр;
//        LOGIN = Выданный логин, обязательный параметр;
//        Количество передаваемых параметров – два. В случае неправильного написания наименования параметров, параметр будет проигнорирован, и заполнен значением по умолчанию. В случае не заполнения одного из обязательных параметров сервер вернет ошибку выполнения команды.  Параметры могут быть перечислены в любой последовательности.
//        3.	Далее сервер возвращает адрес терминала строкой в формате ADDRES=АДРЕС ТЕРМИНАЛА;
//        4.	Возвращение результата выполнения команды
//        В случае успешного выполнения команды возвращается 200 ОК, в случае возникновения какой либо ошибки выводится сообщение 500 ERROR. По завершению работы команды происходит отключение от сервера.


/**
 * Created by lexa on 08.12.2016.
 */
public class CommandGetAddrTerm extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(CommandGetAddrTerm.class);


    /**
     * первый ответ
     */
    public static final String firstResponse = "";

    /**
     * попытатся распарсить данные команды
     * @param commandData
     */
    public static CommandGetAddrTerm tryParseCommand(String commandData) {
        CommandData ret = null;
        boolean flOK = false;

        UserAuthenticationData uad = new UserAuthenticationData();
        flOK = Parser.parseUserAndPassword(commandData, uad);

        if (flOK) {
            ret = new CommandData();
            ret.setUserNameAndPass(uad);
        }


//////////////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'getaddrterm') then
//        begin
//        AContext.Connection.Socket.WriteLn('GADDRTRM');
//        AContext.Connection.Socket.ReadStrings(Str,2,TEncoding.UTF8); //Пока указываю жестко количество параметров
//        {Подключение функции проверки и занесения данных в базу}
//        Results:=DM1.GetAddrTerm(Str,AContext.Connection.Socket.Binding.PeerIP,LOGIN,PASSWD,DB);
//        if Results = '200 OK' then
//        begin
//        AContext.Connection.Socket.WriteLn(GET_USLUGA.Strings[0],TEncoding.UTF8);
//        AContext.Connection.Socket.WriteLn('200 OK',TEncoding.UTF8);
//        GET_USLUGA.Free;
//        end
//        else
//        begin
//        AContext.Connection.Socket.WriteLn(Results);
//        AContext.Connection.Socket.Close;
//        end;
//        // AContext.Connection.Socket.Close;
//        end
        return null;
    }


    @Override
    public void doWorck(ArrayList<String> result, Connection connectionToTerminalDB, Connection connectionToWorkingDB) {
////Возвращает адрес терминала
//        function TDM1.GetAddrTerm(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string):string;
//        begin
//        Result:='500 ERROR';
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN
//        //Проверка корректности подключения терминала подключение возможно только после открытия смены
//        if (TerminalID(DATA)>0) THEN
//        BEGIN
//        GET_USLUGA:=TStringList.Create;
//        {sqlNew.Close;
//        sqlNew.SQL.Text:='SELECT ADDRES FROM TERMINAL WHERE ID='+intToStr(TerminalID(DATA));
//        sqlNew.ExecQuery;}
//        GET_USLUGA.Add('ADDRES='+sqlFreeReturn.fieldByName('ADDRES').asString);
//        Result:='200 OK';
//        Exit;
//        END ELSE BEGIN Result:='500 Not open smena'; Exit; END;
//        END ELSE result:='500 Error connect FIB';
//        end;
//
    }
}



