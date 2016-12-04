package Protocol;

import Protocol.Net.NetServer;

/**
 * 
 */
public final class Server {

    /**
     * @param args
     */
    public static void main(String[]  args) {
        // TODO implement here
        // насоздовать тут всего
        // еще какой то поток для управления сервером


        //CommandExecutor cmd = new CommandExecutor(1);


        try {
            NetServer.start(5050);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}