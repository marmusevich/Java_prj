package protocol.commands;

import protocol.bd.DBContext;

//Команда chkupdate
//        Выполняет запрос на проверку наличия обновлений программного обеспечения закрепленного за данным логином и паролем
//        1.	chkupdate при успешном выполнении возвращает CHKUPDATE;
//        2.	Передача переменной количества параметров в списке TString (массив строк) передается числовым значением.
//        3.	Передача параметров в формате TString (массив строк)
//        Наименования параметров:
//        ID_TERMINAL = Идентификатор терминала, обязательный параметр;
//        LOGIN = Выданный логин, обязательный параметр;
//        VERSIONL = Номер версии установленного (локального) программного обеспечения, обязательный параметр;
//        FILENAME = Имя файла программного обеспечения, обязательный параметр;
//
//        В случае неправильного написание наименования параметров, параметр будет проигнорирован, и заполнен значением по умолчанию. В случае не заполнения одного из обязательных параметров сервер вернет ошибку выполнения команды.  Параметры могут быть перечислены в любой последовательности.
//        4.	Далее сервер возвращает число количества строк в возвращаемом параметре TString (массив строк)
//        5.	После возвращает значение TString (массив строк) с заполненными данными, которые представляются в виде значений:
//        - 1_НАЗВАНИЕ_ЗНАЧЕНИЯ=1_ЗНАЧЕНИЕ;
//        - 2_НАЗВАНИЕ_ЗНАЧЕНИЯ=2_ЗНАЧЕНИЕ;
//
//        В предлагаемом перечне значений не важен порядок перечисления значений.


/**
 * Created by lexa on 08.12.2016.
 */
public class CommandChkUpdate  extends AbstractCommand {
    @Override
    public void executeImpl(DBContext dbContext) {
    }
}