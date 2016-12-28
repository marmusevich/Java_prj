package protocol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.bd.DBContext;
import protocol.net.NetServer;


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
        logger.info(" Before start server...");
        start();
    }

    /**
     * запустить
     */
    public static void start() {
        parameters = new Parameters();
        try {
            //инитить пул БД
            DBContext.init(parameters);

            commandServer = new CommandServer(parameters.commandExecutorThreads, parameters.blockingQueueCapacity, parameters.commandAdTimeout);

            netServer = new NetServer(parameters.netBossThreads, parameters.netWorkerThreads);
            netServer.ConfigureSSL(parameters.isSSL);
            netServer.start(parameters.port, parameters.netCharset);
        } catch (Exception e) {
            stop();
            logger.error(" server stoped on start. ERROR= ", e);
        }
    }

    /**
     * остановить сервер
     */
    public static void stop() {
        DBContext.close();
        if (netServer != null)
            netServer.stop();
        if (commandServer != null)
            commandServer.stop(false);

        logger.info("server stop");
    }

    public static CommandServer getCommandServer() {
        return commandServer;
    }
    public static NetServer getNetServer() {
        return netServer;
    }
    public static Parameters getParameters() {
        return parameters;
    }

}