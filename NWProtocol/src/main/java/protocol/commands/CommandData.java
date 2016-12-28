package protocol.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
* Команда date:
* Возвращает текущую дату и время сервера в формате 200 ДД.ММ.ГГГГ ЧЧ:ММ:СС, где:
* 200 Код успешного выполнения команды
* ДД - день с лидирующем нулем;
* ММ – месяц с лидирующим нулем ;
* ГГГГ – год , в четырехзначном представлении;
 */
public class CommandData  extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(CommandData.class);

    @Override
    public void doWorck(ArrayList<String> result, Connection connectionToTerminalDB, Connection connectionToWorkingDB) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("'200' dd.MM.yyyy HH:mm:ss");
        result.add(dateFormat.format( new Date() ));
    }
}
