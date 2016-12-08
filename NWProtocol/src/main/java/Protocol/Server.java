package protocol;

import protocol.net.NetServer;

//TODO добавить логирование
//TODO еще какой то поток для управления сервером или команда терминала с локалхоста
//TODO правильно перехватывать исключения из потоков
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
        start();
    }

    /**
     * запустить
     */
    public static void start() {
        parameters = new Parameters();

        try {
            commandServer = new CommandServer(parameters.commandExecutorThreads);

            netServer = new NetServer(parameters.netBossThreads, parameters.netWorkerThreads);
            netServer.ConfigureSSL(parameters.isSSL);
            netServer.start(parameters.port, parameters.netCharset);

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