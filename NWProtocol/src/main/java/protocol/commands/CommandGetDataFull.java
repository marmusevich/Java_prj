package protocol.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.ArrayList;

//Команда getdatafull
//        Выполняет процедуру получения данных по л/с поставщика услуги (аналогична команде getdata но без привязки к коду организации) Выполняет поиск как по л/с поставщика услуги так и по единому л/с
//        1.	getdatafull при успешном выполнении возвращает GDATAFULL и ожидает ввода следующего параметра
//        2.	Передача переменной количества параметров в списке TString (массив строк) передается числовым значением.
//        3.	Передача параметров в формате TString (массив строк)
//        Наименования параметров:
//        ID_TERMINAL = Идентификатор терминала, обязательный параметр;
//        LOGIN = Выданный логин, обязательный параметр;
//        LS = Лицевой счет поставщика услуги, обязательный параметр;
//
//        В случае неправильного написание наименования параметров, параметр будет проигнорирован, и заполнен значением по умолчанию. В случае не заполнения одного из обязательных параметров сервер вернет ошибку выполнения команды.  Параметры могут быть перечислены в любой последовательности.
//
//        4.	Далее сервер возвращает число количества строк в возвращаемом параметре TString (массив строк)
//        5.	После возвращает значение TString (массив строк) с заполненными данными, которые представляются в структуре:
//        •	DTM  - Актуальная дата начислений
//        •	USLUGANAME - Наименование услуги (Либо указание на счетчик и его номер)
//        •	IDEN_SHET – Если это прибор учета то отображается его номер, если нет счетчика то 0
//        •	USLUGA – Код услуги согласно справочника Видов услуг (VIDUSLUGI)*
//        •	POKAZ_PRED – Если это прибор учета, то отображаются предыдущие показания если нет прибора то 0
//        •	POKAZ_TEK – Если это прибор учета  то отображаются текущие показания если нет прибора то 0
//        •	FIO – Фамилия И.О.  абонента зарегистрированного за услугой поставщиком услуг
//        •	TARIF – Тариф за оказанную услугу
//        •	KOPLATE – Сумма к оплате
//        •	ADDRESS – Адрес абонента зарегистрированного за услугой
//        •	NS – Единый номер лицевого счета
//        •	LS_POLUCH – лицевой счет поставщика услуг.
//        •	KOD_POLUCH – код поставщика услуг согласно справочника организаций (ORGANIZATION)*
//        •	ORGANIZATION – Наименование организации поставщика услуг
//        •	MFO – МФО поставщика услуг
//        •	OKPO – ОКПО поставщика услуг
//        •	BANK – Наименование банка поставщика услуг.
//        •	R_SHET – Расчетный счет поставщика услуг
//        Значение данных выделяется в отдельную строку. Параметры разделяются вертикальной чертой “|”  и записываются в строгом порядке указанном выше.
//        * - все значения дополнительных справочников  можно получить командой gettable;


/**
 * Created by lexa on 08.12.2016.
 */
public class CommandGetDataFull extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(CommandGetDataFull.class);

    /**
     * первый ответ
     */
    public static final String firstResponse = "";

    /**
     * попытатся распарсить данные команды
     * @param commandData
     */
    public static CommandGetDataFull tryParseCommand(String commandData) {

////*//////////////////////////////////////////////////////////////////////////////////////
//        else if SameText(trim(LCmd), 'getdatafull') then
//        begin
//        AContext.Connection.Socket.WriteLn('GDATAFULL',TEncoding.UTF8);
//        counts:=StrToIntDef(AContext.Connection.Socket.ReadLn(TEncoding.UTF8),0);
//        AContext.Connection.Socket.ReadStrings(Str,counts,TEncoding.UTF8);
//        Results:=DM1.GetDataFull(Str,'0',LOGIN,PASSWD,DB,DB_WORK);
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
//        AContext.Connection.Socket.WriteLn(Results,TEncoding.UTF8);
//        AContext.Connection.Socket.Close;
//        //  AContext.Connection.Socket.Close;
//        end

        return null;
    }



    @Override
    public void doWorck(ArrayList<String> result, Connection connectionToTerminalDB, Connection connectionToWorkingDB) {

        //todo а где DM1.GetDataFull
    }
}
