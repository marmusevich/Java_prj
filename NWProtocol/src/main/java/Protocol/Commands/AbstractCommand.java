package protocol.commands;

import protocol.bd.DBContext;

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
    public abstract void execute(DBContext dbContext);

    /**
     * Вернуть результат
     *
     * @return набор строк результата
     */
    public abstract String[] getResult();


}