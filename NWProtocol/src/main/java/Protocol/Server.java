package protocol;

import protocol.net.NetServer;

/**
 *
 */
public final class Server {

    private static CommandServer commandServer;
    private static NetServer netServer;
    private static Parameters parameters;

    /**
     * @param args
     */
    public static void main(String[] args) {

//        protocol.commands.Parser p = new protocol.commands.Parser();
//
//        String str ="ID_TERM=2 \n"+"PASSWORD=qazwsx12 \n"+"CMD=DATE";
//
//        System.out.println(str);
//
//        System.out.println("<"+p.getCammandName(str) + ">");
        start();
    }

    /**
     * запустить
     */
    public static void start() {
        //TODO еще какой то поток для управления сервером

        parameters = new Parameters();

        try {
            commandServer = new CommandServer(parameters.commandExecutorThreads);

            netServer = new NetServer(parameters.netBossThreads, parameters.netWorkerThreads);
            netServer.ConfigureSSL(parameters.isSSL);
            netServer.start(parameters.port);

        } catch (Exception e) {
            stop();
            e.printStackTrace();
        }

    }



    /**
     * остановить сервер
     */
    public static void stop() {
        if (netServer != null)
            netServer.stop();
        if (commandServer != null)
            commandServer.stop();
    }

    public static CommandServer getCommandExecutorContext() {
        return commandServer;
    }
    public static NetServer getNetServer() {
        return netServer;
    }
    public static Parameters getParameters() {
        return parameters;
    }


}