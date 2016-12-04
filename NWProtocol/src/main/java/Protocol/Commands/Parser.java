package Protocol.Commands;

import io.netty.buffer.ByteBuf;

/**
 * 
 */
public class Parser {

    /**
     * Default constructor
     */
    public Parser() {
    }

    /**
     * Пытается распарсить команду
     * @param cammandName имя команды
     * @param commandData данные команды
     * @return распарсеную команду
     */
    public AbstractCommand tryParseCommand(String cammandName, ByteBuf commandData) {
        // TODO implement here
        return null;
    }

    public AbstractCommand tryParseCommand(String cammandName, String commandData) {
        // TODO implement here
        return null;
    }


}