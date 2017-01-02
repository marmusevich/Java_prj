package protocol.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.ArrayList;

//Команда setcurrence
//        Выполняет процедуру регистрации пачки купюр. При вставке купюры в базу вносится ее номенклатура с указанием цифрового значения текущего генератора купюроприемника. Значение генератора наращивается на единицу лишь после выполнения команды startoplata.
//        1.	setcurrence при успешном выполнении возвращает SETCUR
//        2.	Возвращение результата выполнения команды
//        В случае успешного выполнения команды возвращается 200 ОК, в случае возникновения какой либо ошибки выводится сообщение 500 ERROR. По завершению работы команды происходит отключение от сервера.
//        Наименования параметров:
//        ID_TERMINAL = Идентификатор терминала, обязательный параметр;
//        LOGIN = Выданный логин, обязательный параметр;
//        NOM = Номенклатура денежной единицы, обязательный параметр;
//        ID_CURRENCE-Код сессии купюроприемника, значение текущее значение можно получить командой startoplata
//
//        В случае неправильного написание наименования параметров, параметр будет проигнорирован, и заполнен значением по умолчанию. В случае не заполнения одного из обязательных параметров сервер вернет ошибку выполнения команды.  Параметры могут быть перечислены в любой последовательности.
//
//        3.	Возвращение результата выполнения команды
//        В случае успешного выполнения команды возвращается 200 ОК, в случае возникновения какой либо ошибки выводится сообщение 500 ERROR. По завершению работы команды происходит отключение от сервера.



/**
 * Created by lexa on 08.12.2016.
 */
public class CommandSetCurrence extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(CommandSetCurrence.class);


    /**
     * первый ответ
     */
    public static final String firstResponse = "";

    /**
     * попытатся распарсить данные команды
     * @param commandData
     */
    public static CommandSetCurrence tryParseCommand(String commandData) {
        StopServerCommand ret = null;
        boolean flOK = false;

        UserAuthenticationData uad = new UserAuthenticationData();
        flOK = Parser.parseUserAndPassword(commandData, uad);

        if (flOK) {
            ret = new StopServerCommand();
            ret.setUserNameAndPass(uad);
        }
////*//////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'setcurrence') then
//        begin
//        AContext.Connection.Socket.WriteLn('SETCUR',TEncoding.UTF8);
//        AContext.Connection.Socket.ReadStrings(Str,4,TEncoding.UTF8);
//        {Подключение функции проверки и занесения данных в базу}
//        AContext.Connection.Socket.WriteLn(DM1.SetCurrence(Str,AContext.Connection.Socket.Binding.PeerIP,LOGIN,PASSWD,DB));
//        //AContext.Connection.Socket.Close;
//        end


        return null;
    }


    @Override
    public void doWorck(ArrayList<String> result, Connection connectionToTerminalDB, Connection connectionToWorkingDB) {

////Получение идентификатора почки банкнот из Кешкодера
//        function TDM1.SetCurrence(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string):string;
//        var
//        Str:TStringList;
//        i:integer;
//        ID_TERM,SMENA:integer;
//        begin
//        Result:='500 ERROR';
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN
//        Str:=TStringList.Create;
//        //Проверка корректности подключения терминала подключение возможно только после открытия смены
//        //Если сессия открыта для данного терминала
//        ID_TERM:=TerminalID(DATA);
//        if (ID_TERM>0) THEN
//        BEGIN
//
//        sqlNew.Close;
//        sqlNew.SQL.Clear;
//        sqlNew.SQL.Add('select id from smena where  id_terminal=:ID_TERM');
//        sqlNew.SQL.Add('and data_n=(select max(data_n) from smena where id_terminal=:ID_TERM)');
//        sqlNew.ParamByName('ID_TERM').asInteger:=ID_TERM;
//        sqlNew.ExecQuery;
//        SMENA:=sqlNew.FieldByName('ID').AsInteger;
//
//        sqlNew.Close;
//        sqlNew.SQL.Clear;
//        sqlNew.SQL.Add('INSERT INTO CURRENCY (NOMINAL, ID_CURRENCE, SMENA)');
//        sqlNew.SQL.Add('VALUES');
//        sqlNew.SQL.Add('(:NOMINAL, :ID_CURRENCE, :SMENA)');
//        sqlNew.ParamByName('NOMINAL').asInteger:=StrToIntDef(trim(DATA.VALUES['NOM']),0);
//        sqlNew.ParamByName('ID_CURRENCE').asInteger:=StrToIntDef(trim(DATA.VALUES['ID_CURRENCE']),0);
//        sqlNew.ParamByName('SMENA').asInteger:=SMENA;
//        try
//        sqlNew.Transaction.StartTransaction;
//        sqlNew.ExecQuery;
//        sqlNew.Transaction.CommitRetaining;
//        Result:='200 OK';
//        except
//        Result:='500 Error insert record';//+Data.Text;
//        Str.Add('Error execute sql');
//        for i:=0 to sqlNew.ParamCount-1 do
//        begin
//        Str.Add(sqlNew.ParamName(i)+'='+sqlNew.Params[i].AsString);
//        end;
//        end;
//        END ELSE BEGIN Result:='500 Error open smen'; END;
//        Str.Free;
//        END ELSE Result:='500 Error connect FIB';
//        end;
//
    }
}


