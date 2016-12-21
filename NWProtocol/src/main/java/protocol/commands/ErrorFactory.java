package protocol.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Created by asus on 20.12.2016.
 */
public class ErrorFactory {
    private static final Logger logger = LoggerFactory.getLogger(ErrorFactory.class);

    public enum Error
    {
        Timeout,
        AccessDenied
    }

    public static void convertError(Error error, ArrayList<String>result){
        logger.info("ErrorFactory {}", error);
        switch (error) {
            case Timeout:
                result.add("Timeout");
                break;
            case AccessDenied:
                result.add(" Access Denied");
                break;
        }
    }
}
