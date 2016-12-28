package protocol.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.Server;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.util.ArrayList;

/**
 * Created by asus on 20.12.2016.
 */
public class StopServerCommand extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(StopServerCommand.class);

    //CMD=STOP_SERVER

    @Override
    public void doWorck(ArrayList<String> result, Connection connectionToTerminalDB, Connection connectionToWorkingDB) {
        InetAddress remoteAddress = ((InetSocketAddress)ctx.pipeline().channel().remoteAddress()).getAddress();
        InetAddress localAddress = ((InetSocketAddress)ctx.pipeline().channel().localAddress()).getAddress();

        if(remoteAddress.equals(localAddress)){
            logger.info("Command = 'Stop Server' in adress ({}) user = '{}'", remoteAddress.getHostAddress(), userName);
            Server.stop();
        }
    }
}
