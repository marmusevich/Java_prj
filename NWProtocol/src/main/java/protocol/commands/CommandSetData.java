package protocol.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.sql.*;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


/**
 * Команда setdata
 *         Выполняет процедуру регистрации платежа,, команда выполняется в несколько этапов:
 *         1.	setdata при успешном выполнении возвращает WD и ожидает передачи данных в формате TString (массив строк)
 *         2.	Передача переменной количества параметров в списке TString (массив строк) передается числовым значением.
 *         3.	Передача параметров в формате TString (массив строк)
 *         Наименования параметров:
 *         ID_TERMINAL = Идентификатор терминала, обязательный параметр;
 *         LOGIN = Выданный логин, обязательный параметр;
 *         ID_CURRENCE – Идентификатор из справочника купюро приемника, значение можно получить командой startoplata,по умолчанию равен 0;
 *         ADDRES = Адрес плательщика.
 *         INN = Индивидуальный налоговый номер плательщика
 *         ID_CURRENCE = Признак начала формирования пачки банкнот, если равен 1 тогда наращивается генератор, если значение равно 0
 *         тогда значение счетчика купюроприемника остается неизменным, по умолчанию значение равно 1;
 *         FIO = Фамилия И.О. плательщика.
 *         SUMMA = Перечисляемая сумма платежа, обязательный параметр.
 *         POSTANOVA = Номер постановления
 *         DATA_POSTANOV  = Дата постановления
 *         KOD_ORG  = Код организации  получателя платежа, обязательный параметр , назначается согласно внутреннего справочника на сервере,
 *         и выдается индивидуально при регистрации терминала в системе.
 *         KOD_ORG_DOP – Дополнительный код организации, по умолчанию равен 0;
 *         USLUGA = Услуга по которой проводится платеж, обязательный параметр, назначается согласно внутреннего справочника на сервере,
 *         и выдается индивидуально при регистрации терминала в системе.
 *         DATA = Дата и время совершения платежа, обязательный параметр
 *         PER_START = Начальный период за который проведен платеж (указывается в случае оплаты клиентом коммунальных платежей)
 *         в формате ММ-ГГГГ, где ММ это месяц, а ГГГГ – год.
 *         PER_END = Конечный период за который проведен платеж (указывается в случае оплаты клиентом коммунальных платежей)
 *         в формате ММ-ГГГГ где ММ это месяц, а ГГГГ – год.
 *         LS = Единый лицевой счет;
 *         LS_POLUCH = Лицевой счет поставщика услуг
 *         KOMISIYA = Комиссия внутренняя
 *         KOMISIYA_BANK = Комиссия банка
 *         ORGANIZATION = Наименование организации получателя платежа
 *         MFO = МФО организации получателя платежа
 *         OKPO = ОКПО организации получателя платежа
 *         BANK = Наименование банка организации получателя платежа
 *         R_SHET = Расчетный счет организации получателя платежа
 * 
 *         В случае неправильного написание наименования параметров, параметр будет проигнорирован, и заполнен значением по умолчанию.
 *         В случае не заполнения одного из обязательных параметров сервер вернет ошибку выполнения команды.
 *         Параметры могут быть перечислены в любой последовательности.
 * 
 *         4.	Следующей строкой выдается значение PAY_ID текущего платежа;
 *         5.	Возвращение результата выполнения команды
 *         В случае успешного выполнения команды возвращается 200 ОК, в случае возникновения какой либо ошибки выводится сообщение 500 ERROR,
 *         либо 500 и описание ошибки. По завершению работы команды происходит отключение от сервера.
 */
public class CommandSetData extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(CommandSetData.class);


    /**
     * первый ответ
     */
    public static final String firstResponse = "WD";

    /**
     * попытатся распарсить данные команды
     * @param commandData
     */
    public static CommandSetData tryParseCommand(String commandData) {
        CommandSetData ret = null;
        boolean flOK = false;

        UserAuthenticationData uad = new UserAuthenticationData();
        flOK = Parser.parseUserAndPassword(commandData, uad);

        String _id_currence = Parser.getParametrData(commandData, "ID_CURRENCE");
        String _addres = Parser.getParametrData(commandData, "ADDRES");
        String _fio = Parser.getParametrData(commandData, "FIO");
        String _summa = Parser.getParametrData(commandData, "SUMMA");
        String _inn = Parser.getParametrData(commandData, "INN");
        String _postanova = Parser.getParametrData(commandData, "POSTANOVA");
        String _data_postanov = Parser.getParametrData(commandData, "DATA_POSTANOV");
        String _kod_org = Parser.getParametrData(commandData, "KOD_ORG");
        String _id_terminal = Parser.getParametrData(commandData, "ID");
        String _usluga = Parser.getParametrData(commandData, "USLUGA");

        String _data = Parser.getParametrData(commandData, "DATA");
        String _kto_v = Parser.getParametrData(commandData, "LOGIN");
        String _per_start = Parser.getParametrData(commandData, "PER_START");
        String _per_end = Parser.getParametrData(commandData, "PER_END");
        String _ls = Parser.getParametrData(commandData, "LS");
        String _ls_poluch = Parser.getParametrData(commandData, "LS_POLUCH");
        String _kod_org_dop = Parser.getParametrData(commandData, "KOD_ORG_DOP");
        String _komisiya = Parser.getParametrData(commandData, "KOMISIYA");
        String _komisiya_bank = Parser.getParametrData(commandData, "KOMISIYA_BANK");
        String _organization = Parser.getParametrData(commandData, "ORGANIZATION");
        String _mfo = Parser.getParametrData(commandData, "MFO");
        String _okpo = Parser.getParametrData(commandData, "OKPO");
        String _bank = Parser.getParametrData(commandData, "BANK");
        String _r_shet = Parser.getParametrData(commandData, "R_SHET");

        flOK = flOK && (_id_currence != null) && (_addres != null) && (_fio != null) &&
            (_summa != null) && (_inn != null) && (_postanova != null) && (_data_postanov != null) &&
            (_kod_org != null) && (_id_terminal != null) && (_usluga != null) && (_data  != null) &&
            (_kto_v != null) && (_per_start != null) && (_per_end != null) && (_ls != null) &&
            (_ls_poluch != null) && (_kod_org_dop != null) && (_komisiya != null) && (_komisiya_bank != null) &&
            (_organization != null) && (_mfo != null) && (_okpo != null) && (_bank != null) && (_r_shet != null);

        if (flOK) {
            ret = new CommandSetData();
            ret.setUserNameAndPass(uad);
            ret.id_currence = Long.parseLong(_id_currence);
            ret.addres = _addres;
            ret.fio = _fio;
            ret.summa = Float.parseFloat(_summa);
            ret.inn = _inn;
            ret.postanova = _postanova;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            ret.data_postanov = dateFormat.parse(_data_postanov, new ParsePosition(0)); //todo поотенциально проблема распарсивания
            ret.kod_org = Integer.parseInt(_kod_org);
            ret.id_terminal = Integer.parseInt(_id_terminal);
            ret.usluga = Integer.parseInt(_usluga);

            //String ip = _ip;

            ret.data = dateFormat.parse(_data, new ParsePosition(0)); //todo поотенциально проблема распарсивания
            ret.kto_v = _kto_v;
            ret.per_start = _per_start;
            ret.per_end = _per_end;
            ret.ls = Integer.parseInt(_ls);
            String ls_poluch = _ls_poluch;
            ret.kod_org_dop = Integer.parseInt(_kod_org_dop);
            ret.komisiya = Float.parseFloat(_komisiya);
            ret.komisiya_bank = Float.parseFloat(_komisiya_bank);
            ret.organization = _organization;
            ret.mfo = _mfo;
            ret.okpo = _okpo;
            ret.bank = _bank;
            ret.r_shet = _r_shet;
        }

        return ret;
////*//////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'setdata') then
//        begin
//        AContext.Connection.Socket.WriteLn('WD');
//        counts:=StrToIntDef(AContext.Connection.Socket.ReadLn(TEncoding.UTF8),1);
//        AContext.Connection.Socket.ReadStrings(Str,counts,TEncoding.UTF8);
//        {Подключение функции проверки и занесения данных в базу}
//        Results:=DM1.SetData(Str,AContext.Connection.Socket.Binding.PeerIP,LOGIN,PASSWD,DB);
//        //Вренем PAY_ID для чека
//        if Results = '200 OK' then
//        begin
//        AContext.Connection.Socket.WriteLn(GET_USLUGA.Strings[0],TEncoding.UTF8);
//        AContext.Connection.Socket.WriteLn('200 OK',TEncoding.UTF8);
//        GET_USLUGA.Free;
//        end
//        else
//        begin
//        AContext.Connection.Socket.WriteLn(Results,TEncoding.UTF8);
//        AContext.Connection.Socket.Close;
//        end;
//        end
    }

    long id_currence =0;
    String addres = "";
    String fio = "";
    float summa = 0;
    String inn = "";
    String postanova = "";
    java.util.Date data_postanov = null;
    int kod_org = 0;
    int id_terminal = 0;
    int usluga = 0;
    String ip = "";
    java.util.Date data = null;
    String kto_v = "";
    String per_start = "";
    String per_end = "";
    int ls = 0;
    String ls_poluch = "";
    int kod_org_dop = 0;
    float komisiya = 0;
    float komisiya_bank = 0;
    String organization = "";
    String mfo = "";
    String okpo = "";
    String bank = "";
    String r_shet = "";

    @Override
    public void doWorck(ArrayList<String> result, Connection connectionToTerminalDB, Connection connectionToWorkingDB) throws SQLException {
        String SQLText = " INSERT INTO OPLATA (ID_CURRENCE ,ADDRES, FIO, SUMMA, INN, POSTANOVA, DATA_POSTANOV, " +
                " KOD_ORG, ID_TERMINAL, USLUGA, DATA, IP, KTO_V, PER_START, PER_END, LS, LS_POLUCH, KOD_ORG_DOP, " +
                "KOMISIYA, KOMISIYA_BANK, ORGANIZATION, MFO, OKPO, BANK, R_SHET) " +
                " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ";
//        sqlNew.SQL.Add('(:ID_CURRENCE, :ADDRES, :FIO, :SUMMA, :INN, :POSTANOVA, :DATA_POSTANOV, :KOD_ORG, :ID_TERMINAL, :USLUGA, :DATA, :IP, :KTO_V, :PER_START, :PER_END, :LS, :LS_POLUCH, :KOD_ORG_DOP, :KOMISIYA, :KOMISIYA_BANK, :ORGANIZATION, :MFO, :OKPO, :BANK, :R_SHET)');

        InetAddress remoteAddress = ((InetSocketAddress)ctx.pipeline().channel().remoteAddress()).getAddress();
        ip = remoteAddress.getHostAddress();


        PreparedStatement ps = connectionToTerminalDB.prepareStatement(SQLText);

        ps.setLong(1, id_currence );
        ps.setString(2, addres );
        ps.setString(3, fio );
        ps.setFloat(4, summa );
        ps.setString(5, inn );
        ps.setString(6, postanova );
        ps.setDate(7, (Date) data_postanov );
        ps.setInt(8, kod_org );
        ps.setInt(9, id_terminal );
        ps.setInt(10, usluga );
        ps.setString(11, ip );
        ps.setDate(12, (Date) data );
        ps.setString(13, kto_v );
        ps.setString(14, per_start );
        ps.setString(15, per_end );
        ps.setInt(16, ls );
        ps.setString(17, ls_poluch );
        ps.setInt(18, kod_org_dop );
        ps.setFloat(19, komisiya );
        ps.setFloat(20, komisiya_bank );
        ps.setString(21, organization );
        ps.setString(22, mfo );
        ps.setString(23, okpo );
        ps.setString(24, bank );
        ps.setString(25, r_shet );

        int countChangeString = ps.executeUpdate();
        ps.close();

        if(countChangeString != -1) { // ok
            SQLText = "select gen_id(GEN_OPLATA_ID, 0) from rdb$database ";
            ps = connectionToTerminalDB.prepareStatement(SQLText);
            ResultSet rs = ps.executeQuery();
            rs.next();
            result.add("PAY_ID=" + rs.getString("GEN_ID"));
        }
        else { //error
            result.add("500 Error insert record");
        }

        //todo как возращать результат для сетерных команд


////Добавление платежа в оплату
//        function TDM1.SetData(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string):string;
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
//        sqlNew.SQL.Add('INSERT INTO OPLATA (ID_CURRENCE ,ADDRES, FIO, SUMMA, INN, POSTANOVA, DATA_POSTANOV, KOD_ORG, ID_TERMINAL, USLUGA, DATA, IP, KTO_V, PER_START, PER_END, LS, LS_POLUCH, KOD_ORG_DOP, KOMISIYA, KOMISIYA_BANK, ORGANIZATION, MFO, OKPO, BANK, R_SHET)');
//        sqlNew.SQL.Add('VALUES');
//        sqlNew.SQL.Add('(:ID_CURRENCE, :ADDRES, :FIO, :SUMMA, :INN, :POSTANOVA, :DATA_POSTANOV, :KOD_ORG, :ID_TERMINAL, :USLUGA, :DATA, :IP, :KTO_V, :PER_START, :PER_END, :LS, :LS_POLUCH, :KOD_ORG_DOP, :KOMISIYA, :KOMISIYA_BANK, :ORGANIZATION, :MFO, :OKPO, :BANK, :R_SHET)');
//        sqlNew.ParamByName('ID_CURRENCE').AsInt64:=StrToInt64Def(trim(DATA.VALUES['ID_CURRENCE']),0);
//        sqlNew.ParamByName('ADDRES').asString:=trim(DATA.VALUES['ADDRES']);
//        sqlNew.ParamByName('FIO').asString:=trim(DATA.VALUES['FIO']);
//        sqlNew.ParamByName('SUMMA').asFloat:=StrToFloatDef(ReplaceSub(trim(DATA.VALUES['SUMMA']),'.',DecimalSeparator),0);
//        sqlNew.ParamByName('INN').asString:=trim(DATA.VALUES['INN']);
//        sqlNew.ParamByName('POSTANOVA').asString:=trim(DATA.VALUES['POSTANOVA']);
//        sqlNew.ParamByName('DATA_POSTANOV').AsDateTime:=StrToDateTimeDef(trim(DATA.VALUES['DATA_POSTANOV']),StrToDateTime('01.01.2001 00:00:01'));//StrToIntDef(DATA.VALUES['DATA_POSTANOV'],0);
//        sqlNew.ParamByName('KOD_ORG').asInteger:=StrToIntDef(trim(DATA.VALUES['KOD_ORG']),0);
//        sqlNew.ParamByName('ID_TERMINAL').asInteger:=sqlFreeReturn.FieldByName('ID').asInteger;
//        sqlNew.ParamByName('USLUGA').asInteger:=StrToIntDef(trim(DATA.VALUES['USLUGA']),0);
//        sqlNew.ParamByName('IP').asString:=trim(IPer);
//        sqlNew.ParamByName('DATA').AsDateTime:=Now();//StrToDateTimeDef(trim(DATA.VALUES['DATA']),StrToDateTime('01.01.2001 00:00:01'));
//        sqlNew.ParamByName('KTO_V').asString:=trim(DATA.VALUES['LOGIN']);
//        sqlNew.ParamByName('PER_START').asString:=trim(DATA.VALUES['PER_START']);
//        sqlNew.ParamByName('PER_END').asString:=trim(DATA.VALUES['PER_END']);
//        sqlNew.ParamByName('LS').asInteger:=StrToIntDef(trim(DATA.VALUES['LS']),0);
//        sqlNew.ParamByName('LS_POLUCH').asString:=trim(DATA.VALUES['LS_POLUCH']);
//        sqlNew.ParamByName('KOD_ORG_DOP').asInteger:=StrToIntDef(trim(DATA.VALUES['KOD_ORG_DOP']),0);
//        sqlNew.ParamByName('KOMISIYA').asFloat:=StrToFloatDef(ReplaceSub(trim(DATA.VALUES['KOMISIYA']),'.',DecimalSeparator),0);
//        sqlNew.ParamByName('KOMISIYA_BANK').asFloat:=StrToFloatDef(ReplaceSub(trim(DATA.VALUES['KOMISIYA_BANK']),'.',DecimalSeparator),0);
//        sqlNew.ParamByName('ORGANIZATION').asString:=trim(DATA.VALUES['ORGANIZATION']);
//        sqlNew.ParamByName('MFO').asString:=trim(DATA.VALUES['MFO']);
//        sqlNew.ParamByName('OKPO').asString:=trim(DATA.VALUES['OKPO']);
//        sqlNew.ParamByName('BANK').asString:=trim(DATA.VALUES['BANK']);
//        sqlNew.ParamByName('R_SHET').asString:=trim(DATA.VALUES['R_SHET']);
//        except
//        Str.Add('except errors params');
//        for i:=0 to sqlNew.ParamCount-1 do
//        begin
//        Str.Add(sqlNew.ParamName(i)+'='+sqlNew.Params[i].AsString);
//        end;
//        end;
//        //***********************************
//        try
//        sqlNew.Transaction.StartTransaction;
//        sqlNew.ExecQuery;
//        sqlNew.Transaction.CommitRetaining;
//        //Для возврата текущего присвоенного PAY_ID
//        sqlNew.Close;
//        sqlNew.SQL.Clear;
//        sqlNew.SQL.Add('select gen_id(GEN_OPLATA_ID, 0) from rdb$database');
//        sqlNew.Transaction.StartTransaction;
//        sqlNew.ExecQuery;
//        sqlNew.Transaction.CommitRetaining;
//        GET_USLUGA:=TStringList.Create;
//        GET_USLUGA.ADD('PAY_ID='+sqlNew.FieldBYName('GEN_ID').asString);
//        Result:='200 OK';
//        except
//        sqlNew.Transaction.RollbackRetaining;
//        Result:='500 Error insert record';//+Data.Text;
//        Str.Add('Error execute sql');
//        Str.Add(sqlNew.SQL.Text);
//        for i:=0 to sqlNew.ParamCount-1 do
//        begin
//        Str.Add(sqlNew.ParamName(i)+'='+sqlNew.Params[i].AsString);
//        end;
//        Str.SaveToFile('C:\ErrorInsertOplata.txt');
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
