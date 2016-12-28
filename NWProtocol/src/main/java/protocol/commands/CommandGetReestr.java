package protocol.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.ArrayList;

//Команда getreestr
//        Выполняет процедуру получения данных о реестре платежей данного платежного терминала по указанной смене, в формате TString (массив строк)
//        1.	getreestr при успешном выполнении возвращает GREESTR и ожидает передачи данных в формате TString (массив строк)
//        2.	Передача переменной количества параметров в списке TString (массив строк) передается числовым значением.
//        3.	Передача параметров в формате TString (массив строк)
//        Наименования параметров:
//        ID_TERMINAL = Идентификатор терминала, обязательный параметр;
//        LOGIN = Выданный логин, обязательный параметр;
//        KOD_SMEN= Код смены;
//
//        В случае неправильного написание наименования параметров, параметр будет проигнорирован, и заполнен значением по умолчанию. В случае не заполнения одного из обязательных параметров сервер вернет ошибку выполнения команды.  Параметры могут быть перечислены в любой последовательности.
//
//        4.	Далее сервер возвращает число количества строк в возвращаемом параметре TString (массив строк)
//        5.	После возвращает значение TString (массив строк) с заполненными данными, которые представляются в виде значений разделенными вертикальной чертой “|”, (Значение|Значение1|Значение2 и т.д.)


/**
 * Created by lexa on 08.12.2016.
 */
public class CommandGetReestr extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(CommandGetReestr.class);

    @Override
    public void doWorck(ArrayList<String> result, Connection connectionToTerminalDB, Connection connectionToWorkingDB) {
    }
}
