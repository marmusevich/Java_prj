package protocol.commands;

//Команда getsmena
//        Выполняет процедуру получения данных о сменах начиная от указанной даты, в формате TString (массив строк)
//        1.	getsmena при успешном выполнении возвращает GSMENA и ожидает передачи данных в формате TString (массив строк)
//        2.	Передача переменной количества параметров в списке TString (массив строк) передается числовым значением.
//        3.	Передача параметров в формате TString (массив строк)
//        Наименования параметров:
//        ID_TERMINAL = Идентификатор терминала, обязательный параметр;
//        LOGIN = Выданный логин, обязательный параметр;
//        DATA_N = Дата начала выбора смен;
//
//        В случае неправильного написание наименования параметров, параметр будет проигнорирован, и заполнен значением по умолчанию. В случае не заполнения одного из обязательных параметров сервер вернет ошибку выполнения команды.  Параметры могут быть перечислены в любой последовательности.
//
//        4.	Далее сервер возвращает число количества строк в возвращаемом параметре TString (массив строк)
//        5.	После возвращает значение TString (массив строк) с заполненными данными, которые представляются в виде значений разделенными вертикальной чертой “|”, (Значение|Значение1|Значение2 и т.д.)


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * Created by lexa on 08.12.2016.
 */
public class CommandGetSmena extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(CommandGetSmena.class);

    @Override
    public void doWorck(ArrayList<String> result, Connection connectionToTerminalDB, Connection connectionToWorkingDB) {
    }
}
