package protocol.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

//todo какаята статистика по серверу, думать

/**
 * статистика сервера,
 * вызов только с локальной машины
 */
public class StatisticServerCommand extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(StatisticServerCommand.class);

    /**
     * первый ответ
     */
    public static final String firstResponse = "";

    /**
     * попытатся распарсить данные команды
     * @param commandData
     */
    public static StatisticServerCommand tryParseCommand(String commandData) {
        StatisticServerCommand ret = null;
        boolean flOK = false;

        UserAuthenticationData uad = new UserAuthenticationData();
        flOK = Parser.parseUserAndPassword(commandData, uad);

        if (flOK) {
            ret = new StatisticServerCommand();
            ret.setUserNameAndPass(uad);
        }
        return ret;
    }

    @Override
    public void doWorck(ArrayList<String> result, Connection connectionToTerminalDB, Connection connectionToWorkingDB) throws SQLException {
        InetAddress remoteAddress = ((InetSocketAddress)ctx.pipeline().channel().remoteAddress()).getAddress();
        InetAddress localAddress = ((InetSocketAddress)ctx.pipeline().channel().localAddress()).getAddress();

        if(remoteAddress.equals(localAddress)){
            logger.info("Command = 'Statistic Server' in adress ({}) user = '{}'", remoteAddress.getHostAddress(), userAuthenticationData.name);
            //todo StatisticServerCommand
        }
    }
}
