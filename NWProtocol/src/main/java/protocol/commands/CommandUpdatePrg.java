package protocol.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.ArrayList;

//Команда updateprg
//        Выполняет запрос на получение файла программы
//        updateprg при успешном выполнении возвращает UPDATEPRG;
//        1.	Передача переменной количества параметров в списке TString (массив строк) передается числовым значением.
//        2.	Передача параметров в формате TString (массив строк)
//        Наименования параметров:
//        ID_TERMINAL = Идентификатор терминала, обязательный параметр;
//        LOGIN = Выданный логин, обязательный параметр;
//        FILENAME = Имя файла программного обеспечения, обязательный параметр;
//        DIR =  Директория сохранения полученного файла, обязательный параметр;
//        PATHPRG = Директория место расположения файла на сервере, если параметр не указан, тогда устанавливается директория размещения файлов на сервере определенная в протоколе по умолчанию
//        В случае неправильного написание наименования параметров, параметр будет проигнорирован, и заполнен значением по умолчанию. В случае не заполнения одного из обязательных параметров сервер вернет ошибку выполнения команды.  Параметры могут быть перечислены в любой последовательности.
//        3.	Далее сервер возвращает потоком TStream полученный файл с сервера.


/**
 * Created by lexa on 08.12.2016.
 */
public class CommandUpdatePrg extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(CommandUpdatePrg.class);

    @Override
    public void doWorck(ArrayList<String> result, Connection connectionToTerminalDB, Connection connectionToWorkingDB) {
    }
}