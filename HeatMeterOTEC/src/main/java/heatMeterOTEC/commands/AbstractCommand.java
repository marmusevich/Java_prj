package heatMeterOTEC.commands;

import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import heatMeterOTEC.bd.DBContext;

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
     * контекст сетевого подключения
     */
    protected ChannelHandlerContext ctx;
    protected UserAuthenticationData userAuthenticationData;
    // результат
    private ArrayList<String> result;

    /**
     * устоновить сонтекст подключения
     *
     * @param ctx
     */
    public void setChannelHandlerContext(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    /**
     * установить имя пользователя и пароль
     *
     * @param uad -
     */
    final public void setUserNameAndPass(UserAuthenticationData uad) {
        this.userAuthenticationData = uad;
    }

    /**
     * проверить уровень доступа
     *
     * @param connection - подключение к БД
     * @return
     */
    final public boolean checkUserNameAndPass(Connection connection) throws SQLException {

        int terminalID = 0;

        String SQLText =
                "SELECT ADDRES, ID, BANK_ID FROM TERMINAL WHERE " +
                        "    TERMINAL_ID= ? AND BANK_ID =(SELECT BANK FROM USERS WHERE LOGIN= ?) AND " +
                        "    (SELECT count(*) FROM SMENA WHERE DATA_K is null and SMENA.id_terminal=TERMINAL.ID)>0";

        PreparedStatement ps = connection.prepareStatement(SQLText);
        ps.setString(1, userAuthenticationData.name);
        ps.setString(2, userAuthenticationData.pass);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            terminalID = rs.getInt("ID");
        }
        ps.close();


        return terminalID != 0;
    }





//todo  передавать ошибки, при соеденении и т.п.


    /**
     * Выполнить команду
     */
    final public void execute() {
        Connection connection = null;
        try {
            connection = DBContext.getConnectionDB();

            if (checkUserNameAndPass(connection)) {
                result = new ArrayList<String>();
                doWorck(result, connection);
                sendResult();
            } else
                sendError(ErrorFactory.Error.AccessDenied);

        } catch (SQLException e) {
            //TODO SQLException
            logger.error("getConnection() and work", e);
        } finally {
            if (connection != null)
                try {
                    connection.close(); // вернуть соеденение в пул
                } catch (SQLException e) {
                    //TODO SQLException
                    logger.error("connection.close()", e);
                }
        }
    }

    /**
     * переопределить в наследниках
     *
     * @param result                 результат - массив строк
     * @param connection - соеденение к базе данных
     */
    public abstract void doWorck(ArrayList<String> result, Connection connection) throws SQLException;

    /**
     * Вернуть результат
     * @return набор строк результата
     */
    final public ArrayList<String> getResult() {
        return result;
    }


    /**
     * отправить результат если он есть
     */
    final public void sendResult() {
        logger.info("sendResult {}", ctx.pipeline().channel().remoteAddress().toString());

        //проверить а активно ли соеденение
        if (ctx != null && ctx.channel().isActive())
            ctx.writeAndFlush(this);
        //result = null;
    }

    final public void sendError(ErrorFactory.Error error) {
        result = new ArrayList<String>();
        ErrorFactory.convertError(error, result);
        sendResult();
    }


    // для теста
    @Override
    public void finalize() {
        logger.info("finalize");
    }
}