package protocol.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Описывает состояние команды
 */
public class CommandStateDescriptor {

    private static final Logger logger = LoggerFactory.getLogger(CommandStateDescriptor.class);

    public enum CommandState
    {
        Empty,  //
        // нет состояния,
        // отправлен первый ответ
        // получено количество строк ко чтению
        // команда отправлена на выполнение
    }

    public String CommandName = "";
    public CommandState state = CommandState.Empty;
    public int rowCount = 0;

}
