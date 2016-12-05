package protocol;

import protocol.net.NetServer;

/**
 *
 */
public final class Server {

    private static CommandExecutorContext commandExecutorContext;

    /**
     * @param args
     */
    public static void main(String[] args) {

        // насоздовать тут всего
        // еще какой то поток для управления сервером


        commandExecutorContext = new CommandExecutorContext(1);


        try {
            NetServer.start(5050);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static CommandExecutorContext getCommandExecutorContext() {
        return commandExecutorContext;
    }


}