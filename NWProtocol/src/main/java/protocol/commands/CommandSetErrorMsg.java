package protocol.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.ArrayList;

//Команда seterrormsg
//        Добавляет записи в таблицу TERMINAL_ERROR ошибок, команда выполняется в несколько этапов:
//        1.	seterrormsg при успешном выполнении возвращает SETERRORMSG ожидает передачи данных в формате TString (массив строк)
//        2.	Передача переменной количества параметров в списке TString (массив строк) передается числовым значением.
//        3.	Передача параметров в формате TString (массив строк)
//        Наименования параметров:
//        ID_TERMINAL = Идентификатор терминала, обязательный параметр;
//        LOGIN = Выданный логин, обязательный параметр;
//        ERROR_MSG = Описание ошибки;
//
//        В случае неправильного написание наименования параметров, параметр будет проигнорирован, и заполнен значением по умолчанию. В случае не заполнения одного из обязательных параметров сервер вернет ошибку выполнения команды.  Параметры могут быть перечислены в любой последовательности.
//        В случае успешного выполнения команды возвращается 200 ОК, в случае возникновения какой либо ошибки выводится сообщение 500 ERROR. По завершению работы команды происходит отключение от сервера.


/**
 * Created by lexa on 08.12.2016.
 */
public class CommandSetErrorMsg extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(CommandSetErrorMsg.class);

    @Override
    public void doWorck(ArrayList<String> result, Connection connection) {
    }
}