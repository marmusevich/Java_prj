package protocol.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;

/**
 * Команда getkomisiya
 * Возвращает значения для расчета комиссии.
 * 1.	getkomisiya при успешном выполнении возвращает GKOMISIA
 * 2.	Передача переменной количества параметров в списке TString (массив строк) передается числовым значением.
 * 3.	Передача параметров в формате TString (массив строк)
 * <p>
 * Перечень параметров;
 * ID_TERMINAL = Идентификатор терминала, обязательный параметр;
 * LOGIN = Выданный логин, обязательный параметр;
 * USLUGA= Код услуги по которой нужно получить состав услуг, обязательный параметр (по умолчанию = 0);
 * KOD_ORG = Код организации, обязательный параметр (по умолчанию = 0);
 * BANK_ID = Код банка параметр (по умолчанию = 0);
 * FILIAL_ID = Код филиала банка параметр (по умолчанию = 0);
 * KOMISSIA_TYPE_ID = Код типа комиссии параметр (по умолчанию = 0);
 * SUMMA = Сумма платежа, относительно которого должна рассчитываться комиссия, обязательный параметр (по умолчанию = 0);
 * <p>
 * В случае неправильного написания наименования параметров, параметр будет проигнорирован, и заполнен значением по умолчанию.
 * В случае не заполнения одного из обязательных параметров сервер вернет ошибку выполнения команды.
 * Параметры могут быть перечислены в любой последовательности.
 * 4.	Далее сервер возвращает число количества строк в возвращаемом параметре TString (массив строк)
 * 5.	После возвращает значение TString (массив строк) с заполненными данными, которые представляются в структуре:
 * •	OUR_SUM_INNER=(Значение)
 * •	OUT_SUM_INNER=(Значение)
 * •	OUR_SUM_OUTER=(Значение)
 * •	OUT_SUM_OUTER=(Значение)
 * <p>
 * 6.	Возвращение результата выполнения команды
 * В случае успешного выполнения команды возвращается 200 ОК, в случае возникновения какой либо ошибки выводится сообщение 500 ERROR.
 * По завершению работы команды происходит отключение от сервера.
 */
public class CommandGetKomisiya extends AbstractCommand {
    /**
     * первый ответ
     */
    public static final String firstResponse = "GKOMISIA";
    private static final Logger logger = LoggerFactory.getLogger(CommandGetKomisiya.class);
    String terminal_id = "";
    String bank_id = null;
    String filial_id = null;
    String komissia_type_id = null;
    int usluga_id = -1;
    int organization_id = -1;
    float summa_pay = 0;

    /**
     * попытатся распарсить данные команды
     *
     * @param commandData
     */
    public static CommandGetKomisiya tryParseCommand(String commandData) {
        CommandGetKomisiya ret = null;
        boolean flOK = false;

        UserAuthenticationData uad = new UserAuthenticationData();
        flOK = Parser.parseUserAndPassword(commandData, uad);

        String _bank_id = Parser.getParametrData(commandData, "BANK_ID");
        String _filial_id = Parser.getParametrData(commandData, "FILIAL_ID");
        String _komissia_type_id = Parser.getParametrData(commandData, "KOMISSIA_TYPE_ID");

        String _terminal_id = Parser.getParametrData(commandData, "ID_TERMINAL");
        String _usluga_id = Parser.getParametrData(commandData, "USLUGA");
        String _organization_id = Parser.getParametrData(commandData, "KOD_ORG");
        String _summa_pay = Parser.getParametrData(commandData, "SUMMA");

        flOK = flOK && (_terminal_id != null) && (_usluga_id != null) && (_organization_id != null) && (_summa_pay != null);

        if (flOK) {
            ret = new CommandGetKomisiya();
            ret.setUserNameAndPass(uad);
            ret.terminal_id = _terminal_id;
            ret.usluga_id = Integer.parseInt(_usluga_id);
            ret.organization_id = Integer.parseInt(_organization_id);
            ret.summa_pay = Float.parseFloat(_summa_pay);
            ret.bank_id = _bank_id;
            ret.filial_id = _filial_id;
            ret.komissia_type_id = _komissia_type_id;
        }
        return ret;

////*//////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'getkomisiya') then
//        begin
//        AContext.Connection.Socket.WriteLn('GKOMISIA',TEncoding.UTF8);
//        counts:=StrToIntDef(AContext.Connection.Socket.ReadLn(TEncoding.UTF8),1);
//        AContext.Connection.Socket.ReadStrings(Str,counts,TEncoding.UTF8);
//        {Подключение функции проверки и занесения данных в базу}
//        Results:=DM1.GetKomisiya(Str,AContext.Connection.Socket.Binding.PeerIP,LOGIN,PASSWD,DB);
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
//        end;
//        //AContext.Connection.Socket.Close;
//        end
    }

    @Override
    public void doWorck(ArrayList<String> result, Connection connectionToTerminalDB, Connection connectionToWorkingDB) throws SQLException {
        String SQLText = " SELECT * FROM TERMINAL WHERE TERMINAL_ID = ? ";

        PreparedStatement ps = connectionToTerminalDB.prepareStatement(SQLText);
        ps.setString(1, terminal_id);
        ResultSet rs = ps.executeQuery();

        int id = -1;
        //todo rs.next(); -->  ...= rs.get... - нельзя получатьб просто так, вдрух нет результата
        rs.next();
        id = rs.getInt("ID");
        rs.close();
        ps.close();


        //SQLText = " SELECT * from CALCULATE_KOMISSIA(:USLUGA_ID, :ORGANIZATION_ID, :BANK_ID, :FILIAL_ID, :ID_TERMINAL, :KOMISSIA_TYPE_ID, :SUMMA_PAY)";
        SQLText = " SELECT * from CALCULATE_KOMISSIA(?, ?, ?, ?, ?, ?, ?)";
        ps = connectionToWorkingDB.prepareStatement(SQLText);
        ps.setInt(1, usluga_id);
        ps.setInt(2, organization_id);
        if (bank_id == null)
            ps.setNull(3, Types.INTEGER);
        else
            ps.setInt(3, Integer.parseInt(bank_id));

        if (filial_id == null)
            ps.setNull(4, Types.INTEGER);
        else
            ps.setInt(4, Integer.parseInt(filial_id));

        ps.setInt(5, id);
        if (komissia_type_id == null)
            ps.setNull(6, Types.INTEGER);
        else
            ps.setInt(6, Integer.parseInt(komissia_type_id));

        ps.setFloat(7, summa_pay);
        rs = ps.executeQuery();

        while (rs.next()) {
            result.add("OUR_SUM_INNER=" + rs.getString("OUR_SUM_INNER"));
            result.add("OUT_SUM_INNER=" + rs.getString("OUT_SUM_INNER"));
            result.add("OUR_SUM_OUTER=" + rs.getString("OUR_SUM_OUTER"));
            result.add("OUT_SUM_OUTER=" + rs.getString("OUT_SUM_OUTER"));
        }
        ps.close();


////Возвращает список расчета комиссии
//        function TDM1.GetKomisiya(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string):string;
//        var
//        Str:TstringList;
//        begin
//        Result:='500 ERROR';
//
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN
//        Str:=TStringList.Create;
//        sqlNew.Close;
//        sqlNew.SQL.Clear;
//        //Проверка корректности подключения терминала подключение возможно только после открытия смены
//        if (TerminalID(DATA)>0) THEN
//        BEGIN
//        sqlNew.SQL.Add('SELECT * from CALCULATE_KOMISSIA(');
//        sqlNew.SQL.add(':USLUGA_ID,');
//        sqlNew.SQL.add(':ORGANIZATION_ID,');
//        if StrToIntDef(trim(DATA.Values['BANK_ID']),0)>0 then sqlNew.SQL.add(':BANK_ID,') else sqlNew.SQL.add('null,');
//        if StrToIntDef(trim(DATA.Values['FILIAL_ID']),0)>0 then sqlNew.SQL.add(':FILIAL_ID,') else sqlNew.SQL.add('null, ');
//        sqlNew.SQL.add(':ID_TERMINAL,');
//        if StrToIntDef(trim(DATA.Values['KOMISSIA_TYPE_ID']),0)>0 then sqlNew.SQL.add(':KOMISSIA_TYPE_ID,') else sqlNew.SQL.add('null,');
//        sqlNew.SQL.add(':SUMMA_PAY)');
//
//        //sqlNew.SQL.SaveToFile('C:\123.txt');
//
//        sqlFree.Close;
//        sqlFree.SelectSQL.Text:='SELECT * FROM TERMINAL WHERE TERMINAL_ID=:TERMINAL_ID';
//        sqlFree.ParamByName('TERMINAL_ID').AsString:=trim(DATA.Values['ID_TERMINAL']);
//        sqlFree.Open;
//
//
//
//
//        sqlNew.ParamByName('ID_TERMINAL').asInteger:=sqlFree.FieldByName('ID').AsInteger;//StrToIntDef(trim(DATA.Values['ID_TERMINAL']),1);
//        sqlNew.ParamByName('USLUGA_ID').asInteger:=StrToIntDef(trim(DATA.Values['USLUGA']),1);
//        sqlNew.ParamByName('ORGANIZATION_ID').asInteger:=StrToIntDef(trim(DATA.Values['KOD_ORG']),1);
//
//        if trim(DATA.Values['BANK_ID'])>'0' then sqlNew.ParamByName('BANK_ID').asInteger:=StrToIntDef(trim(DATA.Values['BANK_ID']),0);
//        if trim(DATA.Values['FILIAL_ID'])>'0' then sqlNew.ParamByName('FILIAL_ID').asInteger:=StrToIntDef(trim(DATA.Values['FILIAL_ID']),0);
//        if trim(DATA.Values['KOMISSIA_TYPE_ID'])>'0' then sqlNew.ParamByName('KOMISSIA_TYPE_ID').asInteger:=StrToIntDef(trim(DATA.Values['KOMISSIA_TYPE_ID']),0);
//
//
//        sqlNew.ParamByName('SUMMA_PAY').AsFloat:=StrToFloatDef(ReplaceSub(trim(DATA.VALUES['SUMMA']),'.',DecimalSeparator),0);
//        try
//        sqlNew.Transaction.StartTransaction;
//        sqlNew.ExecQuery;
//        sqlNew.Transaction.CommitRetaining;
//        GET_USLUGA:=TStringList.Create;
//        while not sqlNew.EOF do
//        begin
//        GET_USLUGA.Add(Trim('OUR_SUM_INNER='+sqlNew.FieldByName('OUR_SUM_INNER').asString));
//        GET_USLUGA.Add(Trim('OUT_SUM_INNER='+sqlNew.FieldByName('OUT_SUM_INNER').asString));
//        GET_USLUGA.Add(Trim('OUR_SUM_OUTER='+sqlNew.FieldByName('OUR_SUM_OUTER').asString));
//        GET_USLUGA.Add(Trim('OUT_SUM_OUTER='+sqlNew.FieldByName('OUT_SUM_OUTER').asString));
//        sqlNew.Next;
//        end;
//        Result:='200 OK';
//        Exit;
//        except
//        sqlNew.SQL.SaveToFile('C:\ErrorKomissiya.txt');
//        Result:='500 Error KOMISIYA';
//        exit;
//        end;
//
//        END ELSE BEGIN Result:='500 Not open smena'; Exit; END;
//        Str.Free;
//        END ELSE Result:='500 Error connect FIB';
//        end;
//
    }
}
