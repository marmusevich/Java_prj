package protocol.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

//Команда getcomponent
//        Возвращает перечень разрешенных компонентов программы, команда выполняется в несколько этапов:
//        1.	getcomponent при успешном выполнении возвращает GCOMPONENTи ожидает передачи данных в формате TString (массив строк)
//        2.	Передача переменной количества параметров в списке TString (массив строк) передается числовым значением.
//        3.	Передача параметров в формате TString (массив строк)
//        Наименования параметров:
//        ID_TERMINAL = Идентификатор терминала, обязательный параметр;
//        LOGIN = Выданный логин, обязательный параметр;
//
//        В случае неправильного написание наименования параметров, параметр будет проигнорирован, и заполнен значением по умолчанию. В случае не заполнения одного из обязательных параметров сервер вернет ошибку выполнения команды.  Параметры могут быть перечислены в любой последовательности.
//        4.	Далее сервер возвращает число количества строк в возвращаемом параметре TString (массив строк)
//        5.	После возвращает значение TString (массив строк) с заполненными данными, которые представляются в виде значений:
//        - 1_НАЗВАНИЕ_ЗНАЧЕНИЯ=1_ЗНАЧЕНИЕ;
//        В случае успешного выполнения команды возвращается 200 ОК, в случае возникновения какой либо ошибки выводится сообщение 500 ERROR. По завершению работы команды происходит отключение от сервера.


/**
 * Created by lexa on 08.12.2016.
 */
public class CommandGetComponent extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(CommandGetComponent.class);

    /**
     * первый ответ
     */
    public static final String firstResponse = "";

    /**
     * попытатся распарсить данные команды
     * @param commandData
     */
    public static CommandGetComponent tryParseCommand(String commandData) {
        CommandData ret = null;
        boolean flOK = false;

        UserAuthenticationData uad = new UserAuthenticationData();
        flOK = Parser.parseUserAndPassword(commandData, uad);

        if (flOK) {
            ret = new CommandData();
            ret.setUserNameAndPass(uad);
        }


////////////////////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'getcomponent') then
//        begin
//        Socket.WriteLn('GCOMPONENT',TEncoding.UTF8);
//        counts:=StrToIntDef(AContext.Connection.Socket.ReadLn,1);
//        Socket.ReadStrings(Str,counts,TEncoding.UTF8);
//        Results:=DM1.GetComponent(Str,Socket.Binding.PeerIP,LOGIN,PASSWD,DB);
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



        return null;
    }



    @Override
    public void doWorck(ArrayList<String> result, Connection connectionToTerminalDB, Connection connectionToWorkingDB) throws SQLException {
        String SQLText =
                "  " +
                        "  ";

        PreparedStatement ps = connectionToTerminalDB.prepareStatement(SQLText);
        ps.setString(1, userAuthenticationData.name);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            //dostup = rs.getInt("ID");//Integer.getInteger(rs.getString("ID"));
            //System.out.println("dostup=" + dostup +     " -> ADDRES = " + rs.getString("ADDRES") + ", ID = " + rs.getString("ID") + "BANK_ID = " + rs.getString("BANK_ID"));
        }


        ////Возвращает перечень разрешенных компонентов программы
//        function TDM1.GetComponent(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string):string;
//        var
//        ID_TERMINAL:integer;
//        Str:TstringList;
//        begin
//        Result:='500 ERROR';
//        GET_USLUGA:=TStringList.Create;
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN
//        Str:=TStringList.Create;
//        sqlNew.Close;
//        sqlNew.SQL.Clear;
//        //Проверка корректности подключения терминала подключение возможно только после открытия смены
//        if (TerminalID(DATA)>0) THEN
//        BEGIN
//        //Получим внутренний ID терминала
//        ID_TERMINAL:=GetTermnalID(DATA.Values['ID_TERMINAL']);
//
//        sqlNew.SQL.Add('SELECT * from COMPONENT where ID_TERMINAL=:ID_TERMINAL');
//        sqlNew.ParamByName('ID_TERMINAL').asInteger:=ID_TERMINAL;
//
//        try
//        sqlNew.Transaction.StartTransaction;
//        sqlNew.ExecQuery;
//        sqlNew.Transaction.CommitRetaining;
//        while not sqlNew.EOF do
//        begin
//        GET_USLUGA.Add(Trim(sqlNew.FieldByName('ID_COMPONENT').asString));
//        sqlNew.Next;
//        end;
//        Result:='200 OK';
//        Exit;
//        except
//        sqlNew.SQL.SaveToFile('C:\ErrorComponent.txt');
//        Result:='500 Error COMPONENT';
//        exit;
//        end;
//
//        END ELSE BEGIN Result:='500 Not open smena'; Exit; END;
//        Str.Free;
//        END ELSE Result:='500 Error connect FIB';
//        end;
//
//
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
//
    }
}


