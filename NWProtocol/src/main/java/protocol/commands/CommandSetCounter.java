package protocol.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Команда setcounter
 *        Выполняет процедуру добавления записей счетчиков по PAY_ID платежа:
 *        1.	setcounter при успешном выполнении возвращает WCOUNTER и ожидает передачи данных в формате TString (массив строк)
 *        2.	Передача переменной количества параметров в списке TString (массив строк) передается числовым значением.
 *        3.	Передача параметров в формате TString (массив строк)
 *        Наименования параметров:
 *        ID_TERMINAL = Идентификатор терминала, обязательный параметр;
 *        LOGIN = Выданный логин, обязательный параметр;
 *        LS = Единый лицевой счет.
 *        DT = Текущая дата
 *        IDEN_SHET = Идентификатор счетчика
 *        POKAZ_PRED = Предыдущие показания счетчика.
 *        POKAZ_TEK= Текущие показания счетчика
 *        TARIF = Текущий тариф
 *        PAY_ID = Идентификатор платежа, получается командой setdata;
 *        KOD_ORG – Код организации, по умолчанию равен 0;
 *        USLUGA = Услуга по которой проводится платеж, обязательный параметр, назначается согласно внутреннего справочника на сервере,
 * и выдается индивидуально при регистрации терминала в системе.
 *
 *        В случае неправильного написание наименования параметров, параметр будет проигнорирован, и заполнен значением по умолчанию.
 * В случае не заполнения одного из обязательных параметров сервер вернет ошибку выполнения команды.
 * Параметры могут быть перечислены в любой последовательности.
 *
 *        4.	Возвращение результата выполнения команды
 *        В случае успешного выполнения команды возвращается 200 ОК, в случае возникновения какой либо ошибки выводится сообщение 500 ERROR,
 * либо 500 и описание ошибки. По завершению работы команды происходит отключение от сервера.
 */
public class CommandSetCounter extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(CommandSetCounter.class);

    /**
     * первый ответ
     */
    public static final String firstResponse = "WCOUNTER";

    /**
     * попытатся распарсить данные команды
     * @param commandData
     */
    public static CommandSetCounter tryParseCommand(String commandData) {
        CommandSetCounter ret = null;
        boolean flOK = false;

        UserAuthenticationData uad = new UserAuthenticationData();
        flOK = Parser.parseUserAndPassword(commandData, uad);

        String _ls = Parser.getParametrData(commandData, "LS");
        String _dt = Parser.getParametrData(commandData, "DT");
        String _usluga = Parser.getParametrData(commandData, "USLUGA");
        String _iden_shet= Parser.getParametrData(commandData, "IDEN_SHET");
        String _pokaz_tek = Parser.getParametrData(commandData, "POKAZ_TEK");
        String _pokaz_pred = Parser.getParametrData(commandData, "POKAZ_PRED");
        String _tarif = Parser.getParametrData(commandData, "TARIF");
        String _pay_id = Parser.getParametrData(commandData, "PAY_ID");
        String _kod_org = Parser.getParametrData(commandData, "KOD_ORG");

        flOK = flOK && (_ls != null) && (_dt != null) && (_usluga != null) && (_iden_shet != null)
                && (_pokaz_tek != null) && (_pokaz_pred != null) && (_tarif != null) && (_pay_id != null)  && (_kod_org != null);

        if (flOK) {
            ret = new CommandSetCounter();
            ret.setUserNameAndPass(uad);

            ret.ls = Integer.parseInt(_ls);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            ret.dt = dateFormat.parse(_dt, new ParsePosition(0)); //todo поотенциально проблема распарсивания
            ret.usluga = Integer.parseInt(_usluga);
            ret.iden_shet = _iden_shet;
            ret.pokaz_tek = Float.parseFloat(_pokaz_tek);
            ret.pokaz_pred = Float.parseFloat(_pokaz_pred);
            ret.tarif = Float.parseFloat(_tarif);
            ret.pay_id = Integer.parseInt(_pay_id);
            ret.kod_org = Integer.parseInt(_kod_org);
        }
        return ret;

        ////*//////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'setcounter') then
//        begin
//        try
//        Socket.WriteLn('WCOUNTER');
//        counts:=StrToIntDef(AContext.Connection.Socket.ReadLn(TEncoding.UTF8),1);
//        AContext.Connection.Socket.ReadStrings(Str,counts,TEncoding.UTF8);
//        {Подключение функции проверки и занесения данных в базу}
//        Results:=DM1.SetCounter(Str,AContext.Connection.Socket.Binding.PeerIP,LOGIN,PASSWD,DB);
//        if Results = '200 OK' then
//        begin
//        AContext.Connection.Socket.WriteLn('200 OK',TEncoding.UTF8);
//        end
//        else
//        begin
//        AContext.Connection.Socket.WriteLn(Results,TEncoding.UTF8);
//        end;
//        except
//        on e: EIdException do
//        begin
//        Str.Add('Error '+Lcmd+' '+e.Message);
//        Str.SaveToFile('C:\WMProtocol_EIdException_setCounter.txt');
//        end; // on EIdException
//        on e: Exception do
//        begin
//        Str.Add('Error: '+Lcmd+' '+e.Message);
//        Str.SaveToFile('C:\WMProtocol_Exception_setCounter.txt');
//        end; // on Exception
//        end;
//        end

    }

    int ls = 0;
    java.util.Date dt = null;
    int usluga = 0;
    String iden_shet= null;
    float pokaz_tek = 0;
    float pokaz_pred = 0;
    float tarif = 0;
    int pay_id = 0;
    int kod_org = 0;

    @Override
    public void doWorck(ArrayList<String> result, Connection connectionToTerminalDB, Connection connectionToWorkingDB) throws SQLException {
        String SQLText = " INSERT INTO COUNTER (LS ,DT, USLUGA, IDEN_SHET, POKAZ_PRED, POKAZ_TEK, TARIF, PAY_ID, KOD_ORG) " +
                "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ? )";

        PreparedStatement ps = connectionToTerminalDB.prepareStatement(SQLText);
        ps.setInt(1, ls);
        ps.setDate(2, (Date) dt);
        ps.setInt(3, usluga);
        ps.setString(4, iden_shet);
        ps.setFloat(5, pokaz_tek);
        ps.setFloat(6, pokaz_pred);
        ps.setFloat(7, tarif);
        ps.setInt(8, pay_id);
        ps.setInt(9, kod_org);

        int countChangeString = ps.executeUpdate();
        if(countChangeString != -1) { // ok

        }
        else { //error
            result.add("500 Error insert record");
        }

        //todo как возращать результат для сетерных команд


        ////Добавление данных счетчика в базу
//        function TDM1.SetCounter(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string):string;
//        var
//        Str:TStringList;
//        i:integer;
//        id_curr:integer;
//        begin
//        Result:='500 ERROR';
//        if ConnectFIB(USER,PASSWD,DB) then
//        BEGIN //Проверка подключения к базе
//        Str:=TStringList.Create;
//        if (TerminalID(DATA)>0)THEN
//        BEGIN //Проверка открытой смены по терминалу
//        try
//        //Определим значение генератора пачек денег кешкодера
//        sqlNew.Close;
//        sqlNew.SQL.Clear;
//        sqlNew.SQL.Add('INSERT INTO COUNTER (LS ,DT, USLUGA, IDEN_SHET, POKAZ_PRED, POKAZ_TEK, TARIF, PAY_ID, KOD_ORG)');
//        sqlNew.SQL.Add('VALUES');
//        sqlNew.SQL.Add('(:LS ,:DT, :USLUGA, :IDEN_SHET, :POKAZ_PRED, :POKAZ_TEK, :TARIF, :PAY_ID, :KOD_ORG)');
//        sqlNew.ParamByName('LS').AsInteger:=StrToIntDef(trim(DATA.VALUES['LS']),0);
//        sqlNew.ParamByName('DT').AsDateTime:=StrToDateTimeDef(trim(DATA.VALUES['DT']),StrToDateTime('01.01.2001 00:00:01'));
//        sqlNew.ParamByName('USLUGA').asInteger:=StrToIntDef(trim(DATA.VALUES['USLUGA']),0);
//        sqlNew.ParamByName('IDEN_SHET').asString:=trim(DATA.VALUES['IDEN_SHET']);
//        sqlNew.ParamByName('POKAZ_TEK').asFloat:=StrToFloatDef(ReplaceSub(trim(DATA.VALUES['POKAZ_TEK']),'.',DecimalSeparator),0);
//        sqlNew.ParamByName('POKAZ_PRED').asFloat:=StrToFloatDef(ReplaceSub(trim(DATA.VALUES['POKAZ_PRED']),'.',DecimalSeparator),0);
//        sqlNew.ParamByName('TARIF').asFloat:=StrToFloatDef(ReplaceSub(trim(DATA.VALUES['TARIF']),'.',DecimalSeparator),0);
//        sqlNew.ParamByName('PAY_ID').AsInteger:=StrToIntDef(trim(DATA.VALUES['PAY_ID']),0);
//        sqlNew.ParamByName('KOD_ORG').AsInteger:=StrToIntDef(trim(DATA.VALUES['KOD_ORG']),0);
//        except
//        Str.Add('except errors params');
//        for i:=0 to sqlNew.ParamCount-1 do
//        begin
//        Str.Add(sqlNew.ParamName(i)+'='+sqlNew.Params[i].AsString);
//        end;
//        Str.SaveToFile('C:\CountersExcept.txt');
//        end;
//        //***********************************
//        try
//        sqlNew.Transaction.StartTransaction;
//        sqlNew.ExecQuery;
//        sqlNew.Transaction.CommitRetaining;
//        Result:='200 OK';
//        except
//        Result:='500 Error insert record';//+Data.Text;
//        Str.Add('Error execute sql');
//        Str.Add(sqlNew.SQL.Text);
//        for i:=0 to sqlNew.ParamCount-1 do
//        begin
//        Str.Add(sqlNew.ParamName(i)+'='+sqlNew.Params[i].AsString);
//        end;
//        Str.SaveToFile('C:\ErrorInsertCounter.txt');
//        exit;
//        end;
//        //***************************************************************
//        END ELSE Result:='500 Error open smen';  //Открытая смена по терминалу
//        Str.Free;
//        END ELSE Result:='500 Error connet to FIB'; //Проверка подключения к базе
//
//        end;
//
    }
}


