package heatMeterOTEC.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import heatMeterOTEC.Server;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * останавливает сервер
 * вызов только с локальной машины
 */
public class StopServerCommand extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(StopServerCommand.class);

    {
        mCommandType = StopServerCommand.class.toString();
    }

    @Override
    public void doWorck(ArrayList<String> result, Connection connection) throws SQLException {
        InetAddress remoteAddress = ((InetSocketAddress) ctx.pipeline().channel().remoteAddress()).getAddress();
        InetAddress localAddress = ((InetSocketAddress) ctx.pipeline().channel().localAddress()).getAddress();

        if (remoteAddress.equals(localAddress)) {
            //logger.info("Command = 'Stop Server' in adress ({}) user = '{}'", remoteAddress.getHostAddress(), userAuthenticationData.name);
            Server.stop();
        }
    }

    @Override
    public String toString() {
        return super.toString() + " --> " ;
    }

}
