package protocol.commands;

//Команда date:
//        Возвращает текущую дату и время сервера в формате 200 ДД.ММ.ГГГГ ЧЧ:ММ:СС, где:
//        200 Код успешного выполнения команды
//        ДД - день с лидирующем нулем;
//        ММ – месяц с лидирующим нулем ;
//        ГГГГ – год , в четырехзначном представлении;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 */
public class CommandData  extends AbstractCommand {

    private static final Logger logger = LoggerFactory.getLogger(CommandData.class);


    @Override
    public void doWorck(ArrayList<String> result, Connection connection) {

        logger.trace("doWorck");

        // поигратся потом с фарматом
        String str = new Date().toString();
        result.add(str);
    }

}
