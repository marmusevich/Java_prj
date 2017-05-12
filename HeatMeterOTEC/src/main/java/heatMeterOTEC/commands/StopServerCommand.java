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
        mCommandType = "Stop Server Command";
    }


    /**
     * попытатся распарсить данные команды
     *
     * @param commandData
     */
    public static StopServerCommand tryParseCommand(String commandData) {
        StopServerCommand ret = null;
        boolean flOK = false;

        //UserAuthenticationData uad = new UserAuthenticationData();
        //flOK = Parser.parseUserAndPassword(commandData, uad);

        if (flOK) {
            ret = new StopServerCommand();
            //ret.setUserNameAndPass(uad);
        }
        return ret;
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
