package protocol;

import protocol.net.NetServer;

/**
 * 
 */
public final class Server {

    private static CommandExecutor commandExecutor;

    /**
     * @param args
     */
    public static void main(String[]  args) {
        // TODO implement here
        // насоздовать тут всего
        // еще какой то поток для управления сервером


        commandExecutor = new CommandExecutor(1);


        try {
            NetServer.start(5050);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static CommandExecutor getCommandExecutor(){
        return commandExecutor;
    }


}