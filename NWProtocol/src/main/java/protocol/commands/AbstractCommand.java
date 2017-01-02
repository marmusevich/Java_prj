package protocol.commands;

import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.bd.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Абстастная команда
 * все остальные команды наследовать от нее
 */
public abstract class AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(AbstractCommand.class);

    /**
     * название параметров имени и пароля пользователя
     */
    public static final String UserParametrName = "ID_TERM";
    public static final String PassParametrName = "PASSWORD";

    /**
     * контекст сетевого подключения
     */
    protected ChannelHandlerContext ctx;

    /**
     * устоновить сонтекст подключения
     * @param ctx
     */
    public void setChannelHandlerContext(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }


    protected UserAuthenticationData userAuthenticationData;
    /**
     *  установить имя пользователя и пароль
     * @param uad -
     */
    final public void setUserNameAndPass(UserAuthenticationData uad) {
        this.userAuthenticationData = uad;
    }

    /**
     * проверить уровень доступа
     * @param connectionToTerminalDB - подключение к БД
     * @return
     */
    final public boolean checkUserNameAndPass(Connection connectionToTerminalDB) throws SQLException {
        int dostup=0;

        String SQLText = "SELECT ADDRES, ID, BANK_ID FROM TERMINAL WHERE \n" +
                "    TERMINAL_ID= ? AND BANK_ID =(SELECT BANK FROM USERS WHERE LOGIN= ?) AND \n"+
                "    (SELECT count(*) FROM SMENA WHERE DATA_K is null and SMENA.id_terminal=TERMINAL.ID)>0";

        PreparedStatement ps = connectionToTerminalDB.prepareStatement(SQLText);
        ps.setString(1, userAuthenticationData.name);
        ps.setString(2, userAuthenticationData.pass);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            dostup = rs.getInt("ID");//Integer.getInteger(rs.getString("ID"));

            //logger.info
            System.out.println("dostup=" + dostup +     " -> ADDRES = " + rs.getString("ADDRES") + ", ID = " + rs.getString("ID") + "BANK_ID = " + rs.getString("BANK_ID"));
        }
        //return true;
        return dostup != 0;
    }


    /**
     * Выполнить команду
     */
    final public void execute() {
        Connection connectionToTerminalDB = null;
        Connection connectionToWorkingDB = null;
        try {
            connectionToTerminalDB = DBContext.getConnectionToTerminalDB();
            connectionToWorkingDB = DBContext.getConnectionToWorkingDB();

            if (checkUserNameAndPass(connectionToTerminalDB)) {
                result = new ArrayList<String>();

                doWorck(result, connectionToTerminalDB, connectionToWorkingDB);

                //result.add("   userName = ("+userName+") userPass = ("+userPass+")" );

//todo  передавать ошибки, при соеденении и т.п.
//        Result:='200 OK';
//        except
//        Result:='500 Error open SQL';
//        Exit;
//        end;
//        END ELSE result:='500 Error connect FIB';

            }
            else
                sendError( ErrorFactory.Error.AccessDenied);

        } catch (SQLException e) {
            //TODO SQLException
            logger.error("getConnection() and work", e);
        } finally {
            if (connectionToTerminalDB != null)
                try {
                    connectionToTerminalDB.close(); // вернуть соеденение в пул
                } catch (SQLException e) {
                    //TODO SQLException
                    logger.error("connection.close()", e);
                }
            if (connectionToWorkingDB != null)
                try {
                    connectionToWorkingDB.close(); // вернуть соеденение в пул
                } catch (SQLException e) {
                    //TODO SQLException
                    logger.error("connection.close()", e);
                }
        }
    }

    /**
     * переопределить в наследниках
     * @param result результат - массив строк
     * @param connectionToTerminalDB - соеденение к базе данных
     * @param connectionToWorkingDB - соеденение к базе данных
     */
    public abstract void doWorck(ArrayList<String> result, Connection connectionToTerminalDB, Connection connectionToWorkingDB) throws SQLException;





    // результат
    private ArrayList<String> result;
    /**
     * Вернуть результат
     * @return набор строк результата
     */
    final public ArrayList<String> getResult(){
        return result;
    }



    /**
     * отправить результат если он есть
     */
    final public void sendResult() {
        //TODO проверить а активно ли соеденение
        //TODO  убивать не активные каналы
        logger.trace("sendResult {}", ctx.pipeline().channel().remoteAddress().toString());

        if (ctx != null)
            ctx.writeAndFlush(this);
    }

    final public void sendError(ErrorFactory.Error error) {
        result = new ArrayList<String>();
        ErrorFactory.convertError(error, result);
        sendResult();
    }


    // для теста
    @Override
    public void finalize() {
        //logger.info("finalize");
    }
}