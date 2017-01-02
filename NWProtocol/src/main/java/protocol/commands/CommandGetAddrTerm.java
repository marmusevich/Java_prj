package protocol.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
* Команда getaddrterm
*         Возвращает значение адреса терминала
*         1.	getaddrterm при успешном выполнении возвращает GADDRTRM
*         2.	Возвращение результата выполнения команды
*         В случае успешного выполнения команды возвращается 200 ОК, в случае возникновения какой либо ошибки выводится
 *         сообщение 500 ERROR. По завершению работы команды происходит отключение от сервера.
*         Наименования параметров:
*         ID_TERMINAL = Идентификатор терминала, обязательный параметр;
*         LOGIN = Выданный логин, обязательный параметр;
*         Количество передаваемых параметров – два. В случае неправильного написания наименования параметров, параметр
 *         будет проигнорирован, и заполнен значением по умолчанию. В случае не заполнения одного из обязательных
 *         параметров сервер вернет ошибку выполнения команды.  Параметры могут быть перечислены в любой последовательности.
*         3.	Далее сервер возвращает адрес терминала строкой в формате ADDRES=АДРЕС ТЕРМИНАЛА;
*         4.	Возвращение результата выполнения команды
*         В случае успешного выполнения команды возвращается 200 ОК, в случае возникновения какой либо ошибки выводится
 *         сообщение 500 ERROR. По завершению работы команды происходит отключение от сервера.

 */
public class CommandGetAddrTerm extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(CommandGetAddrTerm.class);


    /**
     * первый ответ
     */
    public static final String firstResponse = "GADDRTRM";

    /**
     * попытатся распарсить данные команды
     * @param commandData
     */
    public static CommandGetAddrTerm tryParseCommand(String commandData) {
        CommandGetAddrTerm ret = null;
        boolean flOK = false;

        UserAuthenticationData uad = new UserAuthenticationData();
        flOK = Parser.parseUserAndPassword(commandData, uad);

        if (flOK) {
            ret = new CommandGetAddrTerm();
            ret.setUserNameAndPass(uad);
        }

        return ret;
    }


    @Override
    public void doWorck(ArrayList<String> result, Connection connectionToTerminalDB, Connection connectionToWorkingDB) throws SQLException {
        String SQLText =
                "SELECT ADDRES, ID, BANK_ID FROM TERMINAL WHERE " +
                        "    TERMINAL_ID= ? AND BANK_ID =(SELECT BANK FROM USERS WHERE LOGIN= ?) AND " +
                        "    (SELECT count(*) FROM SMENA WHERE DATA_K is null and SMENA.id_terminal=TERMINAL.ID)>0";

        PreparedStatement ps = connectionToTerminalDB.prepareStatement(SQLText);
        ps.setString(1, userAuthenticationData.name);
        ps.setString(2, userAuthenticationData.pass);

        ResultSet rs = ps.executeQuery();
        int terminalID=0;
        while (rs.next()) {
            terminalID = rs.getInt("ID");//Integer.getInteger(rs.getString("ID"));
        }
        rs.close();
        ps.close();

        if(terminalID >0){
            SQLText = " SELECT ADDRES FROM TERMINAL WHERE ID = ? ";

            ps = connectionToTerminalDB.prepareStatement(SQLText);
            ps.setInt(1, terminalID);
            rs = ps.executeQuery();
            while (rs.next()) {
                result.add("ADDRES=" + rs.getString("ADDRES"));
            }
        }


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



