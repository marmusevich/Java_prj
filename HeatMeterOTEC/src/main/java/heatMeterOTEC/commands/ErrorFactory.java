package heatMeterOTEC.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * стандартные ошибки
 */
public class ErrorFactory {
    private static final Logger logger = LoggerFactory.getLogger(ErrorFactory.class);

    public static void convertError(Error error, ArrayList<String> result) {
        logger.error("ErrorFactory->{}", error);
        switch (error) {
            case CommandExecutionError:
                result.add("500 ERROR");
                break;
            case Timeout:
                result.add("500 Timeout");
                break;
            case AccessDenied:
                result.add("500 Access Denied");
                break;
        }
    }

    //todo убрать, отправлять строку


    //500 ERROR

    public enum Error {
        CommandExecutionError,  // ошибка при выполнении команды
        Timeout,                // превышен интервал ожидания
        AccessDenied            // нет доступа
    }
}
