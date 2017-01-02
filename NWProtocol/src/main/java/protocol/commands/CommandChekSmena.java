package protocol.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.ArrayList;

//Команда cheksmena
//        Команда выполняет проверку наличия открытой смены по данному ID_TERMINAL и LOGIN
//        1.	checksmena при успешном выполнении возвращает CHKSMENA и ожидает передачи данных в формате TString (массив строк)
//        2.	Передача параметров в формате TString (массив строк)
//        Наименования параметров:
//        ID_TERMINAL = Идентификатор терминала, обязательный параметр;
//        LOGIN = Выданный логин, обязательный параметр;
//        Количество передаваемых параметров – два. В случае неправильного написание наименования параметров, параметр будет проигнорирован, и заполнен значением по умолчанию. В случае не заполнения одного из обязательных параметров сервер вернет ошибку выполнения команды.  Параметры могут быть перечислены в любой последовательности.
//
//        3.	Передача переменной количества параметров в списке TString (массив строк) передается числовым значением.
//        4.	Передает переменную в формате TString (массив строк) со следующими записями:
//        - ADRESS = Адрес терминала или кассы;
//        - TERMINAL = Наименование терминала;
//        - SMENA = Количество открытых смен
//        - SMENA_ID = Идентификатор смены
//        - DATA_N=Дата начала смены
//        - BANK_ID=Идентификатор банка
//        - BANK_NAME=Наименование банка
//        - FILIAL_ID=Идентификатор филиала
//        - FILIAL=Наименование филиала
//        5.	Возвращение результата выполнения команды
//        В случае успешного выполнения команды возвращается 200 ОК, в случае возникновения какой либо ошибки выводится сообщение 500 ERROR. По завершению работы команды происходит отключение от сервера.


/**
 * Created by lexa on 08.12.2016.
 */
public class CommandChekSmena extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(CommandChekSmena.class);

    /**
     * первый ответ
     */
    public static final String firstResponse = "";

    /**
     * попытатся распарсить данные команды
     * @param commandData
     */
    public static CommandChekSmena tryParseCommand(String commandData) {
        CommandData ret = null;
        boolean flOK = false;

        UserAuthenticationData uad = new UserAuthenticationData();
        flOK = Parser.parseUserAndPassword(commandData, uad);

        if (flOK) {
            ret = new CommandData();
            ret.setUserNameAndPass(uad);
        }
//        //////////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'cheksmena') then
//        begin
//        AContext.Connection.Socket.WriteLn('CHKSMENA');
//        AContext.Connection.Socket.ReadStrings(Str,2,TEncoding.UTF8); //Пока указываю жестко количество параметров
//        {Подключение функции проверки и занесения данных в базу}
//        Results:=DM1.ChekSmena(Str,AContext.Connection.Socket.Binding.PeerIP,LOGIN,PASSWD,DB,DB_WORK);
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
//        AContext.Connection.Socket.WriteLn(Results);
//        AContext.Connection.Socket.Close;
//        end

        return null;
    }


    @Override
    public void doWorck(ArrayList<String> result, Connection connectionToTerminalDB, Connection connectionToWorkingDB) {
//        function TDM1.ChekSmena(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string;DB_WORK:string):string;
//        begin
//
//        Result:='500 ERROR';
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN
//        sqlFreeReturn.Close;
//        sqlFreeReturn.SelectSQL.Clear;
//        sqlFreeReturn.SelectSQL.Add('SELECT  TERMINAL.ID KASSA_ID,');
//        sqlFreeReturn.SelectSQL.Add('TERMINAL.terminal_id ,');
//        sqlFreeReturn.SelectSQL.Add('TERMINAL.addres ADDRES,');
//        sqlFreeReturn.SelectSQL.Add('terminal.NAME TERMINAL,');
//        sqlFreeReturn.SelectSQL.Add('users.login USERS,');
//        sqlFreeReturn.SelectSQL.Add('TERMINAL.FILIAL_ID,');
//        sqlFreeReturn.SelectSQL.Add('filial.NAME filial,');
//        sqlFreeReturn.SelectSQL.Add('bank.name BANK,');
//        sqlFreeReturn.SelectSQL.Add('bank.id BANK_ID,');
//        sqlFreeReturn.SelectSQL.Add('USERS.FIO,');
//        sqlFreeReturn.SelectSQL.Add('(SELECT count(*) FROM SMENA WHERE DATA_K is null and SMENA.id_terminal=TERMINAL.ID) SMENA,');
//        sqlFreeReturn.SelectSQL.Add('(SELECT distinct(smena.id) FROM SMENA WHERE DATA_K is null and SMENA.id_terminal=TERMINAL.ID) smena_id,');
//        sqlFreeReturn.SelectSQL.Add('(SELECT distinct(smena.data_n) FROM SMENA WHERE DATA_K is null and SMENA.id_terminal=TERMINAL.ID) data_n,');
//        sqlFreeReturn.SelectSQL.Add('TERMINAL.RS_DEBET RS_DEBET,');
//        sqlFreeReturn.SelectSQL.Add('TERMINAL.RS_CREDIT RS_CREDIT');
//        sqlFreeReturn.SelectSQL.Add('   FROM TERMINAL');
//
//        sqlFreeReturn.SelectSQL.Add(' left join filial on (filial.id=terminal.filial_id)');
//        sqlFreeReturn.SelectSQL.Add(' left join BANK on (BANK.id=terminal.bank_id)');
//        sqlFreeReturn.SelectSQL.Add(' left join users on (USERS.bank=terminal.bank_id and USERS.login=:LOGIN)');
//        sqlFreeReturn.SelectSQL.Add(' WHERE');
//        sqlFreeReturn.SelectSQL.Add('TERMINAL.TERMINAL_ID=:ID_TERMINAL');
//        sqlFreeReturn.SelectSQL.Add('AND');
//        sqlFreeReturn.SelectSQL.Add('terminal.BANK_ID =(SELECT USERS.bank FROM USERS WHERE LOGIN=:LOGIN)');
//
//        sqlFreeReturn.ParamByName('ID_TERMINAL').asString:=trim(DATA.Values['ID_TERMINAL']);
//        sqlFreeReturn.ParamByName('LOGIN').AsString:=trim(DATA.Values['LOGIN']);
//        try
//        sqlFreeReturn.Open;
//        GET_USLUGA:=TStringList.Create;
//        while not sqlFreeReturn.EOF do
//        begin
//        GET_USLUGA.Add('ADDRES='+trim(sqlFreeReturn.FieldByName('ADDRES').asString));
//        GET_USLUGA.Add('KASSA_ID='+trim(sqlFreeReturn.FieldByName('KASSA_ID').asString));
//        GET_USLUGA.Add('TERMINAL='+trim(sqlFreeReturn.FieldByName('TERMINAL').asString));
//        GET_USLUGA.Add('SMENA='+trim(sqlFreeReturn.FieldByName('SMENA').asString));
//        GET_USLUGA.Add('SMENA_ID='+trim(sqlFreeReturn.FieldByName('SMENA_ID').asString));
//        GET_USLUGA.Add('BANK_NAME='+trim(sqlFreeReturn.FieldByName('BANK').asString));
//        GET_USLUGA.Add('DATA_N='+trim(FormatDateTime('dd.mm.yyyy hh:mm:ss',sqlFreeReturn.FieldByName('DATA_N').AsDateTime)));
//        GET_USLUGA.Add('BANK_ID='+trim(sqlFreeReturn.FieldByName('BANK_ID').asString));
//        GET_USLUGA.Add('FILIAL_ID='+trim(sqlFreeReturn.FieldByName('FILIAL_ID').asString));
//        GET_USLUGA.Add('FILIAL='+trim(sqlFreeReturn.FieldByName('FILIAL').asString));
//        GET_USLUGA.Add('FIO='+trim(sqlFreeReturn.FieldByName('FIO').asString));
//        GET_USLUGA.Add('RS_DEBET='+trim(sqlFreeReturn.FieldByName('RS_DEBET').asString));
//        GET_USLUGA.Add('RS_CREDIT='+trim(sqlFreeReturn.FieldByName('RS_CREDIT').asString));
//        sqlFreeReturn.Next;
//        end;
//        Result:='200 OK';
//        except
//        Result:='500 Error open SQL';
//        Exit;
//        end;
//
//        END ELSE result:='500 Error connect FIB';
//
//        end;
//
    }
}


