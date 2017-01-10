package protocol.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.Server;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * останавливает сервер
 * вызов только с локальной машины
 */
public class StopServerCommand extends AbstractCommand {
    /**
     * первый ответ
     */
    public static final String firstResponse = "STOP_SERVER";
    private static final Logger logger = LoggerFactory.getLogger(StopServerCommand.class);

    /**
     * попытатся распарсить данные команды
     *
     * @param commandData
     */
    public static StopServerCommand tryParseCommand(String commandData) {
        StopServerCommand ret = null;
        boolean flOK = false;

        UserAuthenticationData uad = new UserAuthenticationData();
        flOK = Parser.parseUserAndPassword(commandData, uad);

        if (flOK) {
            ret = new StopServerCommand();
            ret.setUserNameAndPass(uad);
        }
        return ret;
    }


    @Override
    public void doWorck(ArrayList<String> result, Connection connectionToTerminalDB, Connection connectionToWorkingDB) throws SQLException {
        InetAddress remoteAddress = ((InetSocketAddress) ctx.pipeline().channel().remoteAddress()).getAddress();
        InetAddress localAddress = ((InetSocketAddress) ctx.pipeline().channel().localAddress()).getAddress();

        if (remoteAddress.equals(localAddress)) {
            logger.info("Command = 'Stop Server' in adress ({}) user = '{}'", remoteAddress.getHostAddress(), userAuthenticationData.name);
            Server.stop();
        }
    }
}
