package heatMeterOTEC.commands;

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

    {
        mCommandType = StatisticServerCommand.class.toString();
    }

    @Override
    public void doWorck(ArrayList<String> result, Connection connection) throws SQLException {
        InetAddress remoteAddress = ((InetSocketAddress) ctx.pipeline().channel().remoteAddress()).getAddress();
        InetAddress localAddress = ((InetSocketAddress) ctx.pipeline().channel().localAddress()).getAddress();

        if (remoteAddress.equals(localAddress)) {
            //logger.info("Command = 'Statistic Server' in adress ({}) user = '{}'", remoteAddress.getHostAddress(), userAuthenticationData.name);
            //todo StatisticServerCommand
        }
    }

    @Override
    public String toString() {
        return super.toString() + " --> " ;
    }
}
