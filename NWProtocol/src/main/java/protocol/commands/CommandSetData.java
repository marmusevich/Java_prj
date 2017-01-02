package protocol.commands;

//Команда setdata
//        Выполняет процедуру регистрации платежа,, команда выполняется в несколько этапов:
//        1.	setdata при успешном выполнении возвращает WD и ожидает передачи данных в формате TString (массив строк)
//        2.	Передача переменной количества параметров в списке TString (массив строк) передается числовым значением.
//        3.	Передача параметров в формате TString (массив строк)
//        Наименования параметров:
//        ID_TERMINAL = Идентификатор терминала, обязательный параметр;
//        LOGIN = Выданный логин, обязательный параметр;
//        ID_CURRENCE – Идентификатор из справочника купюро приемника, значение можно получить командой startoplata,по умолчанию равен 0;
//        ADDRES = Адрес плательщика.
//        INN = Индивидуальный налоговый номер плательщика
//        ID_CURRENCE = Признак начала формирования пачки банкнот, если равен 1 тогда наращивается генератор, если значение равно 0 тогда значение счетчика купюроприемника остается неизменным, по умолчанию значение равно 1;
//        FIO = Фамилия И.О. плательщика.
//        SUMMA = Перечисляемая сумма платежа, обязательный параметр.
//        POSTANOVA = Номер постановления
//        DATA_POSTANOV  = Дата постановления
//        KOD_ORG  = Код организации  получателя платежа, обязательный параметр , назначается согласно внутреннего справочника на сервере, и выдается индивидуально при регистрации терминала в системе.
//        KOD_ORG_DOP – Дополнительный код организации, по умолчанию равен 0;
//        USLUGA = Услуга по которой проводится платеж, обязательный параметр, назначается согласно внутреннего справочника на сервере, и выдается индивидуально при регистрации терминала в системе.
//        DATA = Дата и время совершения платежа, обязательный параметр
//        PER_START = Начальный период за который проведен платеж (указывается в случае оплаты клиентом коммунальных платежей) в формате ММ-ГГГГ, где ММ это месяц, а ГГГГ – год.
//        PER_END = Конечный период за который проведен платеж (указывается в случае оплаты клиентом коммунальных платежей) в формате ММ-ГГГГ где ММ это месяц, а ГГГГ – год.
//        LS = Единый лицевой счет;
//        LS_POLUCH = Лицевой счет поставщика услуг
//        KOMISIYA = Комиссия внутренняя
//        KOMISIYA_BANK = Комиссия банка
//        ORGANIZATION = Наименование организации получателя платежа
//        MFO = МФО организации получателя платежа
//        OKPO = ОКПО организации получателя платежа
//        BANK = Наименование банка организации получателя платежа
//        R_SHET = Расчетный счет организации получателя платежа
//
//        В случае неправильного написание наименования параметров, параметр будет проигнорирован, и заполнен значением по умолчанию. В случае не заполнения одного из обязательных параметров сервер вернет ошибку выполнения команды.  Параметры могут быть перечислены в любой последовательности.
//
//        4.	Следующей строкой выдается значение PAY_ID текущего платежа;
//        5.	Возвращение результата выполнения команды
//        В случае успешного выполнения команды возвращается 200 ОК, в случае возникновения какой либо ошибки выводится сообщение 500 ERROR, либо 500 и описание ошибки. По завершению работы команды происходит отключение от сервера.
//


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * Created by lexa on 08.12.2016.
 */
public class CommandSetData extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(CommandSetData.class);


    /**
     * первый ответ
     */
    public static final String firstResponse = "";

    /**
     * попытатся распарсить данные команды
     * @param commandData
     */
    public static CommandSetData tryParseCommand(String commandData) {
        StopServerCommand ret = null;
        boolean flOK = false;

        UserAuthenticationData uad = new UserAuthenticationData();
        flOK = Parser.parseUserAndPassword(commandData, uad);

        if (flOK) {
            ret = new StopServerCommand();
            ret.setUserNameAndPass(uad);
        }
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


        return null;
    }


    @Override
    public void doWorck(ArrayList<String> result, Connection connectionToTerminalDB, Connection connectionToWorkingDB) {

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
