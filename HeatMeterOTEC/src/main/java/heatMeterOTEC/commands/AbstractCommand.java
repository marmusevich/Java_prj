package heatMeterOTEC.commands;

import com.google.gson.annotations.SerializedName;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import heatMeterOTEC.bd.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import com.google.gson.annotations.Expose;

/**
 * Абстастная команда
 * все остальные команды наследовать от нее
 */
public abstract class AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(AbstractCommand.class);
    /**
     * контекст сетевого подключения
     */
    @Expose
    protected ChannelHandlerContext ctx;

    @SerializedName("Command_Type")
    protected String mCommandType = AbstractCommand.class.toString();

    @SerializedName("User_Name")
    protected String mUserName = "";
    @SerializedName("User_Pass")
    protected String mUserPass = "";


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
     * @param userName -
     * @param userPass -
     */
    final public void setUserNameAndPass(String userName, String userPass) {
        this.mUserName = userName;
        this.mUserPass = userPass;
    }

    /**
     * проверить уровень доступа
     *
     * @param connection - подключение к БД
     * @return
     */
    final public boolean checkUserNameAndPass(Connection connection) throws SQLException {

//        int terminalID = 0;
//
//        String SQLText =
//                "SELECT ADDRES, ID, BANK_ID FROM TERMINAL WHERE " +
//                        "    TERMINAL_ID= ? AND BANK_ID =(SELECT BANK FROM USERS WHERE LOGIN= ?) AND " +
//                        "    (SELECT count(*) FROM SMENA WHERE DATA_K is null and SMENA.id_terminal=TERMINAL.ID)>0";
//
//        PreparedStatement ps = connection.prepareStatement(SQLText);
//        ps.setString(1, mUserName);
//        ps.setString(2, mUserPass);
//
//        ResultSet rs = ps.executeQuery();
//        while (rs.next()) {
//            terminalID = rs.getInt("ID");
//        }
//        ps.close();
//
//        return terminalID != 0;
        return true;
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
                sendError("AccessDenied");

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

    final public void sendError(String error) {
        result = new ArrayList<String>();
        result.add(error);
        sendResult();
    }


    // для теста
    @Override
    public void finalize() {
        logger.info("finalize");
    }

    @Override
    public String toString() {
        return  "Command Type = <" + mCommandType + ">, " +
                "[ User Name = " + mUserName + ", "+
                "User Pass = " + mUserPass + "] ";
    }
}