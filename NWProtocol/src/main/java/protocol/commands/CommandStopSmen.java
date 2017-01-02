package protocol.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.ArrayList;


//Команда stopsmen
//        Выполняет идентификацию закрытия смены, запускается при выключении терминала либо подготовке терминала или кассы к инкассации, команда выполняется в несколько этапов:
//        1.	stopsmen при успешном выполнении возвращает WSTOP
//        2.	Передача параметров в формате TString (массив строк)
//        Наименования параметров:
//        ID_TERMINAL = Идентификатор терминала, обязательный параметр;
//        LOGIN = Выданный логин, обязательный параметр;
//
//        В случае неправильного написание наименования параметров, параметр будет проигнорирован, и заполнен значением по умолчанию. В случае не заполнения одного из обязательных параметров сервер вернет ошибку выполнения команды.  Параметры могут быть перечислены в любой последовательности.
//
//        3.	Передача переменной количества параметров в списке TString (массив строк) передается числовым значением.
//        4.	Передает переменную в формате TString (массив строк) со следующими записями:
//        - DATA_N = Дата открытия смены;
//        - DATA_K = Дата закрытия смены;
//        - SMENA = Идентификатор закрываемой смены
//        - SUMMA = Указывается общая сумма принятых платежей;
//        - 1 = Количество купюр;
//        -5 = Количество купюр;
//        - 10= Количество купюр;
//        - 20 = Количество купюр;
//        - 50 = Количество купюр;
//        - 100 = Количество купюр;
//        - 200 = Количество купюр;
//        -500 = Количество купюр;
//        (в списке могут отсутствовать те или иные номенклатуры купюр, в зависимости их наличия в терминале);
//        5.	Возвращение результата выполнения команды
//        В случае успешного выполнения команды возвращается 200 ОК, в случае возникновения какой либо ошибки выводится сообщение 500 ERROR. По завершению работы команды происходит отключение от сервера.
//


/**
 * Created by lexa on 08.12.2016.
 */
public class CommandStopSmen extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(CommandStopSmen.class);


    /**
     * первый ответ
     */
    public static final String firstResponse = "";

    /**
     * попытатся распарсить данные команды
     * @param commandData
     */
    public static CommandStopSmen tryParseCommand(String commandData) {
/////////////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'stopsmen') then
//        begin
//        AContext.Connection.Socket.WriteLn('WSTOP',TEncoding.UTF8);
//        AContext.Connection.Socket.ReadStrings(Str,2,TEncoding.UTF8);
//        Results:=DM1.CloseSmen(Str,AContext.Connection.Socket.Binding.PeerIP,LOGIN,PASSWD,DB);
//        if Results='200 OK' then
//        begin
//        AContext.Connection.Socket.WriteLn(IntToStr(GET_USLUGA.Count),TEncoding.UTF8);
//        AContext.Connection.Socket.WriteBufferOpen;
//        AContext.Connection.Socket.Write(GET_USLUGA,true,TEncoding.UTF8);
//        AContext.Connection.Socket.WriteBufferClose;
//        AContext.Connection.Socket.WriteLn('200 OK',TEncoding.UTF8);
//        GET_USLUGA.Free;
//        end
//        else  begin
//        AContext.Connection.Socket.WriteLn(Results,TEncoding.UTF8);
//        AContext.Connection.Socket.Close;
//        end;
////    AContext.Connection.Disconnect;
//        end

        return null;
    }


    @Override
    public void doWorck(ArrayList<String> result, Connection connectionToTerminalDB, Connection connectionToWorkingDB) {
////Закрытие смены
//        function TDM1.CloseSmen(DATA: TStringList;IPer:string;USER:string;PASSWD:string;DB:string):string;
//        var
//        SMENA,ID_TERMINAL,INKASATOR:INTEGER;
//        SUMMA:extended;
//        DATA_K,DATA_N:TDateTime;
//        begin
//        Result:='500 ERROR';
//        IF ConnectFIB(USER,PASSWD,DB) THEN
//        BEGIN
//
//        ID_TERMINAL:=GetTermnalID(
//        DATA.Values['ID_TERMINAL']);
//
//        //Получим ID инкасатора
//        sqlFreeReturn.Close;
//        sqlFreeReturn.SelectSQL.Text:='SELECT ID FROM INKASATOR WHERE KEY_ID=:KEY_ID';
//        sqlFreeReturn.ParamByName('KEY_ID').asString:=trim(DATA.Values['LOGIN']);
//        sqlFreeReturn.Open;
//        INKASATOR:=sqlFreeReturn.FieldByName('ID').asInteger;
//
//        //Необходимо узнать номер текущей смены для данного терминала
//        sqlFreeReturn.Close;
//        sqlFreeReturn.SelectSQL.Text:='SELECT ID, DATA_K, DATA_N FROM SMENA WHERE ID_TERMINAL=:ID_TERMINAL and DATA_K is null';
//        sqlFreeReturn.ParamByName('ID_TERMINAL').asInteger:=ID_TERMINAL;
//        sqlFreeReturn.Open;
//        SMENA:=sqlFreeReturn.FieldByNAme('ID').asInteger;
//        DATA_K:=sqlFreeReturn.FieldByNAme('DATA_K').AsDateTime;
//        DATA_N:=sqlFreeReturn.FieldByNAme('DATA_N').AsDateTime;
//
//        sqlFreeReturn.Close;
//        sqlFreeReturn.SelectSQL.Text:='SELECT SUM(SUMMA) FROM OPLATA WHERE KOD_SMEN=:KOD_SMEN';
//        sqlFreeReturn.ParamByName('KOD_SMEN').asInteger:=SMENA;
//        sqlFreeReturn.Open;
//        SUMMA:=sqlFreeReturn.FieldByNAme('SUM').asFloat;
//
//        sqlNew.Close;
//        sqlNew.SQL.Clear;
//        sqlNew.SQL.Add('UPDATE SMENA set DATA_K = :DATA_K, INKASATOR=:INKASATOR WHERE ID_Terminal=:ID_TERMINAL and DATA_K is null');
//        sqlNew.ParamByName('DATA_K').AsDateTime:=Now();
//        sqlNew.ParamByName('ID_TERMINAL').asInteger:=ID_TERMINAL;
//        sqlNew.ParamByName('INKASATOR').asInteger:=INKASATOR;
//        try
//        GET_USLUGA:=TStringList.Create;
//        sqlNew.Transaction.StartTransaction;
//        sqlNew.ExecQuery;
//        sqlNew.Transaction.CommitRetaining;
//
//        GET_USLUGA.Add('SUMMA='+FloatToStr(SUMMA));
//        GET_USLUGA.Add('SMENA='+IntToStr(SMENA));
//        GET_USLUGA.Add('DATA_K='+FormatDateTime('dd.mm.yyyy hh.mm.ss',DATA_K));
//        GET_USLUGA.Add('DATA_N='+FormatDateTime('dd.mm.yyyy hh.mm.ss',DATA_N));
//
//        sqlFreeReturn.Close;
//        sqlFreeReturn.SelectSQL.Text:='select nominal, count(nominal) kolvo from currency  where  currency.smena=:smena group by nominal';
//        sqlFreeReturn.ParamByName('SMENA').asInteger:=SMENA;
//        sqlFreeReturn.Open;
//        while not sqlFreeReturn.EOF do
//        begin
//        GET_USLUGA.Add(sqlFreeReturn.FieldByName('nominal').asString+'='+sqlFreeReturn.FieldByName('kolvo').asString);
//        sqlFreeReturn.Next;
//        end;
//        Result:='200 OK';
//        except
//        Result:='500 Error Insert record';
//        Exit;
//        end;
//        END ELSE Result:='Error connect FIB';
//        end;
    }
}


