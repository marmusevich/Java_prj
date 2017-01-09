package protocol.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Описывает состояние команды
 */
public class CommandStateDescriptor {

    private static final Logger logger = LoggerFactory.getLogger(CommandStateDescriptor.class);

    /**
     * состояние парсинга команды
     */
    public enum CommandState
    {
        Empty,                  // нет состояния,
        FirstResponseResive,    // отправлен первый ответ
        CommandlDataCountReaded,// получено количество строк ко чтению
        CommandlDataReaded,     // прочитано необходимое количество строк
        CommandExec,            // команда отправлена на выполнение
    }

    /**
     * имя команды
     */
    public String CommandName = "";

    /**
     * состояние команды
     */
    public CommandState state = CommandState.Empty;

    /**
     * количество строк для чтения
     */
    public int rowCount = 0;

    /**
     * текущее прочитаное количество строк
     */
    public int currentRowCount = 0;


    /**
     * данные команды, прочитанные строки
     */
    public String CommandData = "";
}
