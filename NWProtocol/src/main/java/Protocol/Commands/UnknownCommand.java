package protocol.commands;

/**
 * 
 */
public class UnknownCommand extends AbstractCommand {

    /**
     * Default constructor
     */
    public UnknownCommand() {
    }

    /**
     * Выполнить команду
     * здесь реализовать логику
     */
	 @Override
    public void execute(){

     }

    /**
     * Вернуть результат
     * @return набор строк результата
     */
	 @Override
    public String[] getResult(){
         return new String[] {"Unknown command"};
     }
}