package protocol.commands;

/**
 * Абстастная команда
 */
public abstract class AbstractCommand {


    /**
     * Default constructor
     */
    public AbstractCommand() {
    }

    /**
     * Выполнить команду
     * здесь реализовать логику
     */
    public abstract void execute();

    /**
     * Вернуть результат
     *
     * @return набор строк результата
     */
    public abstract String[] getResult();




}