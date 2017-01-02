package protocol.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 * Команда chkupdate
 *  Выполняет запрос на проверку наличия обновлений программного обеспечения закрепленного за данным логином и паролем
 *      1.	chkupdate при успешном выполнении возвращает CHKUPDATE;
 *      2.	Передача переменной количества параметров в списке TString (массив строк) передается числовым значением.
 *      3.	Передача параметров в формате TString (массив строк)
 *      Наименования параметров:
 *      ID_TERMINAL = Идентификатор терминала, обязательный параметр;
 *      LOGIN = Выданный логин, обязательный параметр;
 *      VERSIONL = Номер версии установленного (локального) программного обеспечения, обязательный параметр;
 *      FILENAME = Имя файла программного обеспечения, обязательный параметр;
 *  В случае неправильного написание наименования параметров, параметр будет проигнорирован, и заполнен значением по умолчанию. В случае не заполнения одного из обязательных параметров сервер вернет ошибку выполнения команды.  Параметры могут быть перечислены в любой последовательности.
 *      4.	Далее сервер возвращает число количества строк в возвращаемом параметре TString (массив строк)
 *      5.	После возвращает значение TString (массив строк) с заполненными данными, которые представляются в виде значений:
 *      - 1_НАЗВАНИЕ_ЗНАЧЕНИЯ=1_ЗНАЧЕНИЕ;
 *      - 2_НАЗВАНИЕ_ЗНАЧЕНИЯ=2_ЗНАЧЕНИЕ;
 *  В предлагаемом перечне значений не важен порядок перечисления значений.
 */
public class CommandChkUpdate extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(CommandChkUpdate.class);

    /**
     * первый ответ
     */
    public static final String firstResponse = "CHKUPDATE";

    /**
     * попытатся распарсить данные команды
     * @param commandData
     */
    public static CommandChkUpdate tryParseCommand(String commandData) {
        CommandChkUpdate ret = null;
        boolean flOK = false;

        UserAuthenticationData uad = new UserAuthenticationData();
        flOK = Parser.parseUserAndPassword(commandData, uad);

        if (flOK) {
            ret = new CommandChkUpdate();
            ret.setUserNameAndPass(uad);
        }
        return ret;
    }

    //todo спросить у сереги, а что тут происходит


    @Override
    public void doWorck(ArrayList<String> result, Connection connectionToTerminalDB, Connection connectionToWorkingDB) throws SQLException {
        String SQLText =
                " SELECT PATCH, filename from prg_version where " +
                        " id=(select prg_version from USERS where USERS.login=?) ";

        PreparedStatement ps = connectionToTerminalDB.prepareStatement(SQLText);
        ps.setString(1, userAuthenticationData.name);
        ResultSet rs = ps.executeQuery();


//        sqlFree.SelectSQL.SaveToFile('C:\ErrorCHKUPDATE.txt');
//        Result:='500 Error CHKUPDATE';
//        Versions:=TCEVersionInfo.Create(Owner);


        while (rs.next()) {
            //dostup = rs.getInt("ID");//Integer.getInteger(rs.getString("ID"));
            //System.out.println("dostup=" + dostup +     " -> ADDRES = " + rs.getString("ADDRES") + ", ID = " + rs.getString("ID") + "BANK_ID = " + rs.getString("BANK_ID"));

//        Versions.GetInfo(sqlFree.FieldByName('PATCH').asString+trim(DATA.VALUES['FILENAME']));
//        GET_USLUGA.Add('PATCH='+sqlFree.FieldByName('PATCH').asString+trim(DATA.VALUES['FILENAME']));
//        GET_USLUGA.Add('VERSIONS='+Versions.FileVersion);
//        GET_USLUGA.Add('VERSIONL='+trim(DATA.Values['VERSIONL']));
//        GET_USLUGA.Add('FILENAME='+trim(DATA.VALUES['FILENAME']));
//        if ChkVersion(trim(DATA.Values['VERSIONL']),Versions.FileVersion) then
//        GET_USLUGA.Add('UPDATE=true') else GET_USLUGA.Add('UPDATE=false');
//        sqlFree.Next;


        }
    }
}

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


//        function ChkVersion(old,New:string):boolean;
//        var
//        Major,Minor,Release,Build:string;
//        MajorN,MinorN,ReleaseN,BuildN:string;
//        S:string;
//        begin
//        Result:=false;
//        if (old>'')and (Length(old)>6) then
//        begin
//        ///////
//        S:=old;
//        Delete(S,pos('.',S),255);
//        Major:=S;
//        //////
//        S:=old;
//        Delete(S,1,pos('.',S));//Первый символ до точки
//        Delete(S,pos('.',S),255); //Второе значение
//        Minor:=S;
//        //////
//        S:=old;
//        Delete(S,1,pos('.',S));//Первый символ до точки
//        Delete(S,1,pos('.',S));//Второй символ до точки
//        Delete(S,pos('.',S),255); //Третье значение
//        Release:=S;
//        //////
//        S:=old;
//        Delete(S,1,pos('.',S));//Первый символ до точки
//        Delete(S,1,pos('.',S));//Второй символ до точки
//        Delete(S,1,pos('.',S));//Третий символ до точки
//        Delete(S,pos('.',S),255); //Четвертое значение
//        Build:=S;
//        end;
//        if (new>'') and (Length(new)>6) then
//        begin
//        S:=new;
//        Delete(S,pos('.',S),255);
//        MajorN:=S;
//        //////
//        S:=new;
//        Delete(S,1,pos('.',S));//Первый символ до точки
//        Delete(S,pos('.',S),255); //Второе значение
//        MinorN:=S;
//        //////
//        S:=new;
//        Delete(S,1,pos('.',S));//Первый символ до точки
//        Delete(S,1,pos('.',S));//Второй символ до точки
//        Delete(S,pos('.',S),255); //Третье значение
//        ReleaseN:=S;
//        //////
//        S:=new;
//        Delete(S,1,pos('.',S));//Первый символ до точки
//        Delete(S,1,pos('.',S));//Второй символ до точки
//        Delete(S,1,pos('.',S));//Третий символ до точки
//        Delete(S,pos('.',S),255); //Четвертое значение
//        BuildN:=S;
//        end;
//        if StrToIntDef(MajorN,0)>StrToIntDef(Major,0) then Result:=true
//        else
//        if StrToIntDef(MinorN,0)>StrToIntDef(Minor,0) then Result:=true
//        else
//        if StrToIntDef(ReleaseN,0)>StrToIntDef(Release,0) then Result:=true
//        else
//        if StrToIntDef(BuildN,0)>StrToIntDef(Build,0) then Result:=true
//        else
//        Result:=false;
//        end;


