package Protocol.Commands;

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
         String[] ret = {"Unknown command"};
         return ret;
     }
}