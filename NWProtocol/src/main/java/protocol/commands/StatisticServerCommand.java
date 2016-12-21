package protocol.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.util.ArrayList;

/**
 * Created by asus on 20.12.2016.
 */
public class StatisticServerCommand extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(StatisticServerCommand.class);

    @Override
    public void doWorck(ArrayList<String> result, Connection connection) {
        InetAddress remoteAddress = ((InetSocketAddress)ctx.pipeline().channel().remoteAddress()).getAddress();
        InetAddress localAddress = ((InetSocketAddress)ctx.pipeline().channel().localAddress()).getAddress();

        if(remoteAddress.equals(localAddress)){
            logger.info("Command = 'Statistic Server' in adress ({}) user = '{}'", remoteAddress.getHostAddress(), userName);
            //todo StatisticServerCommand
        }
    }
}
