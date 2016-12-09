package protocol.commands;

import protocol.bd.DBContext;

//Команда getoplatals
//        Выполняет процедуру получения данных оплат по лицевому счету получателя LS_POLUCH, в формате TString (массив строк)
//        1.	getoplatals при успешном выполнении возвращает GOPLATALS и ожидает передачи данных в формате TString (массив строк)
//        2.	Передача переменной количества параметров в списке TString (массив строк) передается числовым значением.
//        3.	Передача параметров в формате TString (массив строк)
//        Наименования параметров:
//        ID_TERMINAL = Идентификатор терминала, обязательный параметр;
//        LOGIN = Выданный логин, обязательный параметр;
//        LS_POLUCH= лицевой счет получателя платежа;
//        DATA = Дата от которой начала периода выбора платежей
//        KOD_ORG = Код организации поставщика услуг
//
//        В случае неправильного написание наименования параметров, параметр будет проигнорирован, и заполнен значением по умолчанию. В случае не заполнения одного из обязательных параметров сервер вернет ошибку выполнения команды.  Параметры могут быть перечислены в любой последовательности.
//
//        4.	Далее сервер возвращает число количества строк в возвращаемом параметре TString (массив строк)
//        5.	После возвращает значение TString (массив строк) с заполненными данными, которые представляются в виде значений разделенными вертикальной чертой “|”, (Значение|Значение1|Значение2 и т.д.) . Значение колонок по порядку:
//        •	Идентификатор платежа
//        •	ФИО
//        •	Адрес,
//        •	Сумма,
//        •	Индивидуальный налоговый номер,
//        •	Номер постановления,
//        •	Дата постановления,
//        •	Код организации,
//        •	Код организации подотчетный,
//        •	Код смены,
//        •	Идентификатор терминала,
//        •	Код услуги,
//        •	IP,
//        •	Идентификатор купюроприемника,
//        •	Дата,
//        •	Дата вноса
//        •	Дата изменения
//        •	Кто вносил
//        •	Кто изменял,
//        •	Начальный период,
//        •	Конечный период,
//        •	Лицевой счет единый,
//        •	Лицевой счет получателя,
//        •	Комиссия,
//        •	Комиссия 2,
//        •	Название организации получателя,
//        •	МФО Получателя,
//        •	ОКПО Получателя,
//        •	Банк получателя,
//        •	Расчетный счет получателя,
//        •	Идентификатор сторно,
//        •	Комиссия 3,
//        •	Комиссия 4


/**
 * Created by lexa on 08.12.2016.
 */
public class CommandGetOplatals extends AbstractCommand {
    @Override
    public void doWorck(DBContext dbContext) {
    }
}

