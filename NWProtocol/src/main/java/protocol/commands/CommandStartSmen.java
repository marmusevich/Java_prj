package protocol.commands;


//Команда startsmen
//        Выполняет идентификацию открытия смены и присвоению текущей смене уникального индекса, запускается при включении терминала или открытия кассы, команда выполняется в несколько этапов:
//        1.	startsmen при успешном выполнении возвращает WSTART и ожидает передачи данных в формате TString (массив строк)
//        2.	Передача параметров в формате TString (массив строк)
//        Наименования параметров:
//        ID_TERMINAL = Идентификатор терминала, обязательный параметр;
//        LOGIN = Выданный логин, обязательный параметр;
//
//        В случае неправильного написание наименования параметров, параметр будет проигнорирован, и заполнен значением по умолчанию. В случае не заполнения одного из обязательных параметров сервер вернет ошибку выполнения команды.  Параметры могут быть перечислены в любой последовательности.
//
//        3.	Возвращение результата выполнения команды
//        В случае успешного выполнения команды возвращается 200 ОК, в случае возникновения какой либо ошибки выводится сообщение 500 ERROR. По завершению работы команды происходит отключение от сервера.


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * Created by lexa on 08.12.2016.
 */
public class CommandStartSmen extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(CommandStartSmen.class);

    @Override
    public void doWorck(ArrayList<String> result, Connection connectionToTerminalDB, Connection connectionToWorkingDB) {
    }
}
