package protocol.commands;

import protocol.bd.DBContext;



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
    @Override
    public void doWorck(DBContext dbContext) {
    }
}