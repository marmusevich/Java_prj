package protocol.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.ArrayList;

//Команда chkupdate
//        Выполняет запрос на проверку наличия обновлений программного обеспечения закрепленного за данным логином и паролем
//        1.	chkupdate при успешном выполнении возвращает CHKUPDATE;
//        2.	Передача переменной количества параметров в списке TString (массив строк) передается числовым значением.
//        3.	Передача параметров в формате TString (массив строк)
//        Наименования параметров:
//        ID_TERMINAL = Идентификатор терминала, обязательный параметр;
//        LOGIN = Выданный логин, обязательный параметр;
//        VERSIONL = Номер версии установленного (локального) программного обеспечения, обязательный параметр;
//        FILENAME = Имя файла программного обеспечения, обязательный параметр;
//
//        В случае неправильного написание наименования параметров, параметр будет проигнорирован, и заполнен значением по умолчанию. В случае не заполнения одного из обязательных параметров сервер вернет ошибку выполнения команды.  Параметры могут быть перечислены в любой последовательности.
//        4.	Далее сервер возвращает число количества строк в возвращаемом параметре TString (массив строк)
//        5.	После возвращает значение TString (массив строк) с заполненными данными, которые представляются в виде значений:
//        - 1_НАЗВАНИЕ_ЗНАЧЕНИЯ=1_ЗНАЧЕНИЕ;
//        - 2_НАЗВАНИЕ_ЗНАЧЕНИЯ=2_ЗНАЧЕНИЕ;
//
//        В предлагаемом перечне значений не важен порядок перечисления значений.


/**
 * Created by lexa on 08.12.2016.
 */
public class CommandChkUpdate extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(CommandChkUpdate.class);

    /**
     * первый ответ
     */
    public static final String firstResponse = "";

    /**
     * попытатся распарсить данные команды
     * @param commandData
     */
    public static CommandChkUpdate tryParseCommand(String commandData) {
//////////////////////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'chkupdate') then
//        begin
//        Socket.WriteLn('CHKUPDATE',TEncoding.UTF8);
//        counts:=StrToIntDef(AContext.Connection.Socket.ReadLn,1);
//        Socket.ReadStrings(Str,counts,TEncoding.UTF8);
//        results:=DM1.ChkUpdate(Str, Socket.Binding.PeerIP,LOGIN,PASSWD,DB,DB_WORK);
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
//        end
//

        return null;
    }



    @Override
    public void doWorck(ArrayList<String> result, Connection connectionToTerminalDB, Connection connectionToWorkingDB) {

////Проверка наличия обновлений
//        function TDM1.ChkUpdate(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string;DB_WORK:string):string;
//        var
//        LS:integer;
//        Versions:TCEVersionInfo;
//        begin
//        Result:='500 ERROR';
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN
//        //Проверка корректности подключения терминала подключение возможно только после открытия смены
//        //if (TerminalID(DATA)>0)THEN
//        //BEGIN
//        //Создадим запрос поиска имени файла по логину и паролю пользователя
//        sqlFree.Close;
//        sqlFree.SelectSQL.Clear;
//        sqlFree.SelectSQL.Add('SELECT PATCH, filename from prg_version where');
//        sqlFree.SelectSQL.Add('id=(select prg_version from USERS where USERS.login=:LOGIN)');
//        sqlFree.ParamByName('LOGIN').asString:=DATA.Values['LOGIN'];
//        try
//        sqlFree.Open;
//        GET_USLUGA:=TStringList.Create;
//        except
//        sqlFree.SelectSQL.SaveToFile('C:\ErrorCHKUPDATE.txt');
//        Result:='500 Error CHKUPDATE';
//        Exit;
//        end;
//        Versions:=TCEVersionInfo.Create(Owner);
//        while not sqlFree.EOF do
//        begin
//        //Versions.GetInfo(sqlFree.FieldByName('PATCH').asString+sqlFree.FieldByName('filename').asString);
//        Versions.GetInfo(sqlFree.FieldByName('PATCH').asString+trim(DATA.VALUES['FILENAME']));
//
//        GET_USLUGA.Add('PATCH='+sqlFree.FieldByName('PATCH').asString+trim(DATA.VALUES['FILENAME']));
//        GET_USLUGA.Add('VERSIONS='+Versions.FileVersion);
//        GET_USLUGA.Add('VERSIONL='+trim(DATA.Values['VERSIONL']));
//        GET_USLUGA.Add('FILENAME='+trim(DATA.VALUES['FILENAME']));
//        if ChkVersion(trim(DATA.Values['VERSIONL']),Versions.FileVersion) then
//        GET_USLUGA.Add('UPDATE=true') else GET_USLUGA.Add('UPDATE=false');
//        sqlFree.Next;
//        end;
//        Versions.Free;
//        Result:='200 OK';
//        Exit;
//        //END ELSE BEGIN Result:='500 Not open smena';  Exit; END;
//        END ELSE Result:='500 Error connect FIB';
//        end;
//
//        procedure AddText(Txt: String);
//        const
//        log = 'C:\service_test.log';
//        var
//        TF: TextFile;
//        begin
//        AssignFile(TF, log);
//        try
//        if FileExists(log) then Append(TF)
//        else ReWrite(TF);
//        WriteLn(TF, Txt);
//        finally
//        Flush(TF);
//        CloseFile(TF);
//        end;
//        end;
//
    }
}

