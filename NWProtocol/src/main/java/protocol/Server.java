package protocol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    /**
     * @param args
     */
    public static void main(String[] args) {
        logger.trace(" Before start server...");
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
            logger.error(" server stoped on start. ", e);
            //e.printStackTrace();
            //todo сервер не останавливается при ошибке
        }
    }

    /**
     * остановить сервер
     */
    public static void stop() {
        if (netServer != null)
            netServer.stop();
        if (commandServer != null)
            commandServer.stop(true);

        logger.trace("server stop");
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