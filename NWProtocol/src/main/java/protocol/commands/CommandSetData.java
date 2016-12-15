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


import protocol.bd.DBContext;

/**
 * Created by lexa on 08.12.2016.
 */
public class CommandSetData extends AbstractCommand {
    @Override
    public void doWorck(DBContext dbContext) {
    }
}