package protocol.commands;

import java.util.Date;

/**
 * 
 */
public class CommandGetData extends AbstractCommand {

    /**
     * Default constructor
     */
    public CommandGetData() {
    }

    /**
     * Выполнить команду
     * здесь реализовать логику
     */
	@Override
    public  void execute(){
	    // а ничего и не делаем

        //System.out.println("        CommandGetData - execute()");
    }

    /**
     * Вернуть результат
     * @return набор строк результата
     */
	@Override
    public  String[] getResult(){
	    // поигратся потом с фарматом
       String str = new Date().toString();
        return new String[] { str };
    }

}