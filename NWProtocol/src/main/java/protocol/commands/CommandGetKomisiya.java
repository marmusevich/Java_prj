package protocol.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.ArrayList;

//Команда getkomisiya
//        Возвращает значения для расчета комиссии.
//        1.	getkomisiya при успешном выполнении возвращает GKOMISIA
//        2.	Передача переменной количества параметров в списке TString (массив строк) передается числовым значением.
//        3.	Передача параметров в формате TString (массив строк)
//
//        Перечень параметров;
//        ID_TERMINAL = Идентификатор терминала, обязательный параметр;
//        LOGIN = Выданный логин, обязательный параметр;
//        USLUGA= Код услуги по которой нужно получить состав услуг, обязательный параметр (по умолчанию = 0);
//        KOD_ORG = Код организации, обязательный параметр (по умолчанию = 0);
//        BANK_ID = Код банка параметр (по умолчанию = 0);
//        FILIAL_ID = Код филиала банка параметр (по умолчанию = 0);
//        KOMISSIA_TYPE_ID = Код типа комиссии параметр (по умолчанию = 0);
//        SUMMA = Сумма платежа, относительно которого должна рассчитываться комиссия, обязательный параметр (по умолчанию = 0);
//
//        В случае неправильного написания наименования параметров, параметр будет проигнорирован, и заполнен значением по умолчанию. В случае не заполнения одного из обязательных параметров сервер вернет ошибку выполнения команды.  Параметры могут быть перечислены в любой последовательности.
//        4.	Далее сервер возвращает число количества строк в возвращаемом параметре TString (массив строк)
//        5.	После возвращает значение TString (массив строк) с заполненными данными, которые представляются в структуре:
//        •	OUR_SUM_INNER=(Значение)
//        •	OUT_SUM_INNER=(Значение)
//        •	OUR_SUM_OUTER=(Значение)
//        •	OUT_SUM_OUTER=(Значение)
//
//        6.	Возвращение результата выполнения команды
//        В случае успешного выполнения команды возвращается 200 ОК, в случае возникновения какой либо ошибки выводится сообщение 500 ERROR. По завершению работы команды происходит отключение от сервера.


/**
 * Created by lexa on 08.12.2016.
 */
public class CommandGetKomisiya extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(CommandGetKomisiya.class);

    @Override
    public void doWorck(ArrayList<String> result, Connection connection) {
    }
}
