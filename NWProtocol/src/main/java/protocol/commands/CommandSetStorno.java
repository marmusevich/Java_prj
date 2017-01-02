package protocol.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.ArrayList;

//Команда setstorno
//        Выполняет процедуру сторнирования платежа:
//        1.	setstorno при успешном выполнении возвращает STORNO и ожидает передачи данных в формате TString (массив строк)
//        2.	Передача переменной количества параметров в списке TString (массив строк) передается числовым значением.
//        3.	Передача параметров в формате TString (массив строк)
//        Наименования параметров:
//        ID_TERMINAL = Идентификатор терминала, обязательный параметр;
//        LOGIN = Выданный логин, обязательный параметр;
//        PAY_ID = ID платежа по которому необходимо совершить сторно;
//        DATA = Указывается дата и время сторнирования, обычно это текущая дата и время
//        SUMMA = Указывается сумма сторнирования, обязательный параметр
//        TIP = Указывается тип сторно, если TIP = 0 то это обычное сторно, если TIP = 1 то это признак сдачи, параметр не обязательный, по умолчанию равен 0;
//
//        В случае неправильного написание наименования параметров, параметр будет проигнорирован, и заполнен значением по умолчанию. В случае не заполнения одного из обязательных параметров сервер вернет ошибку выполнения команды.  Параметры могут быть перечислены в любой последовательности.
//
//        4.	Возвращение результата выполнения команды
//        В случае успешного выполнения команды возвращается 200 ОК, в случае возникновения какой либо ошибки выводится сообщение 500 ERROR, либо 500 и описание ошибки. По завершению работы команды происходит отключение от сервера.



/**
 * Created by lexa on 08.12.2016.
 */
public class CommandSetStorno extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(CommandSetStorno.class);

    /**
     * первый ответ
     */
    public static final String firstResponse = "";

    /**
     * попытатся распарсить данные команды
     * @param commandData
     */
    public static CommandSetStorno tryParseCommand(String commandData) {
        StopServerCommand ret = null;
        boolean flOK = false;

        UserAuthenticationData uad = new UserAuthenticationData();
        flOK = Parser.parseUserAndPassword(commandData, uad);

        if (flOK) {
            ret = new StopServerCommand();
            ret.setUserNameAndPass(uad);
        }
////*//////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'setstorno') then
//        begin
//        AContext.Connection.Socket.WriteLn('SETSTORNO',TEncoding.UTF8);
//        counts:=StrToIntDef(AContext.Connection.Socket.ReadLn(TEncoding.UTF8),1);
//        AContext.Connection.Socket.ReadStrings(Str,counts,TEncoding.UTF8);
//        {Подключение функции проверки и занесения данных в базу}
//        AContext.Connection.Socket.WriteLn(DM1.SetStorno(Str,AContext.Connection.Socket.Binding.PeerIP,LOGIN,PASSWD,DB));
//        //AContext.Connection.Socket.Close;
//        end

        return null;
    }



    @Override
    public void doWorck(ArrayList<String> result, Connection connectionToTerminalDB, Connection connectionToWorkingDB) {
////Оформление сторно платежа
//
//        function TDM1.SetStorno(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string):string;
//        var
//        Str:TStringList;
//        ID_TERM, i:integer;
//        STORNO:Int64;
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
//        sqlNew.Close;
//        sqlNew.SQL.Clear;
//        sqlNew.SQL.Add('INSERT INTO STORNO (DATA, SUMMA, PAY_ID, TIP)');
//        sqlNew.SQL.Add('VALUES');
//        sqlNew.SQL.Add('(:DATA, :SUMMA, :PAY_ID, :TIP)');
//        sqlNew.ParamByName('DATA').AsDateTime:=StrToDateTime(trim(DATA.VALUES['DATA']));
//        sqlNew.ParamByName('SUMMA').asFloat:=StrToFloatDef(ReplaceSub(trim(DATA.VALUES['SUMMA']),'.',DecimalSeparator),0);
//        sqlNew.ParamByName('PAY_ID').asInteger:=StrToInt64Def(trim(DATA.Values['PAY_ID']),0);
//        sqlNew.ParamByName('TIP').asInteger:=StrToIntDef(trim(DATA.Values['TIP']),0);
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
//        //Узнаем текущее значение генератора сторно
//        sqlNew.Close;
//        sqlNew.SQL.Clear;
//        sqlNew.SQL.Add('select gen_id(GEN_STORNO_ID, 0) from rdb$database');
//        try
//        sqlNew.Transaction.StartTransaction;
//        sqlNew.ExecQuery;
//        sqlNew.Transaction.CommitRetaining;
//        STORNO:=sqlNew.FieldByName('GEN_ID').AsInt64;
//        except
//        Result:='500 Error get generator storno';//+Data.Text;
//        Exit;
//        end;
//        //Теперь обновим запись платежа
//        sqlNew.Close;
//        sqlNew.SQL.Clear;
//        sqlNew.SQL.Add('UPDATE OPLATA SET STORNO_ID=:STORNO WHERE ID=:PAY_ID');
//        sqlNew.ParamByName('STORNO').AsInt64:=STORNO;
//        sqlNew.ParamByName('PAY_ID').AsInt64:=StrToInt64Def(trim(DATA.Values['PAY_ID']),0);
//        try
//        sqlNew.Transaction.StartTransaction;
//        sqlNew.ExecQuery;
//        sqlNew.Transaction.CommitRetaining;
//        except
//        Result:='500 Error update oplata';//+Data.Text;
//        Exit;
//        end;
//
//
//        END ELSE BEGIN Result:='500 Error open smen'; END;
//        Str.Free;
//        END ELSE Result:='500 Error connect FIB';
//        end;
//
    }
}



