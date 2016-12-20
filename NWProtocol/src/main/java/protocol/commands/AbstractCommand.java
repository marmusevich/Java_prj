package protocol.commands;

import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.bd.DBContext;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Абстастная команда
 * все остальные команды наследовать от нее
 */
public abstract class AbstractCommand implements Closeable {
    private static final Logger logger = LoggerFactory.getLogger(AbstractCommand.class);

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


    private String userName;
    private String userPass;
    /**
     *  установить имя пользователя и пароль
     * @param userName - пользователь
     * @param userPass - пароль
     */
    final public void setUserNameAndPass(String userName, String userPass){
        this.userName = userName;
        this.userPass = userPass;
    }

    /**
     * проверить уровень доступа
     * @param connection - подключение к БД
     * @return
     */
    final public boolean checkUserNameAndPass(Connection connection) throws SQLException {
        //TODO проверить аунтификацию, не выполнять команду
        logger.trace("checkUserNameAndPass");
        return true;

        // todo logger.trace("execute{}", ctx.pipeline().channel().remoteAddress().toString()); - адрес подключения


        // пример подключения

//
//        PreparedStatement ps = connection.prepareStatement("SELECT * FROM USERS_TERM_STATE");
////            ps.setInt(1, 1);
////                PreparedStatement ps = connection.prepareStatement("SELECT * FROM USERS_TERM_STATE WHERE id = ?");
////                ps.setInt(1, 1);
//        ResultSet rs = ps.executeQuery();
//        while (rs.next()) {
//            System.out.println("LOGIN = " + rs.getString("LOGIN") + ", USERNAME = " + rs.getString("USERNAME"));
//        }




//    public int checkDostup( int ID, String passwd) throws SQLException {
//        int dostup=0;
//        String TERMINAL_ID = GetTerminalString(ID);
//        String SQLText = "SELECT ADDRES, ID, BANK_ID FROM TERMINAL WHERE\n" +
//                "    TERMINAL_ID=\'"+TERMINAL_ID+"\' AND\n"+
//                "    BANK_ID =(SELECT BANK FROM USERS WHERE LOGIN=\'"+passwd+"\') AND\n" +
//                "    (SELECT count(*) FROM SMENA WHERE DATA_K is null and SMENA.id_terminal=TERMINAL.ID)>0";
//        ResultSet rs = DB_TERMINAL.resultQuery(SQLText);
//        while (rs.next()) {
//            dostup = rs.getInt("ID");//Integer.getInteger(rs.getString("ID"));
//        }
//        return dostup;
//    }

    }


    /**
     * Выполнить команду
     */
    final public void execute(DBContext dbContext) {
        Connection connection = null;
        try {
            //TODO connection = dbContext.getConnection(); // получить соеденение из пула
            result = new ArrayList<String>();

            //logger.info("execute");

            //TODO проверить аунтификацию, не выполнять команду
            if (checkUserNameAndPass(connection))
                doWorck(result, connection);
            else
                result.add("Error dostup");

        } catch (SQLException e) {
            //TODO SQLException
            logger.error("getConnection() and work", e);
            e.printStackTrace();
        } finally {
            if (connection != null)
                try {
                    connection.close(); // вернуть соеденение в пул
                } catch (SQLException e) {
                    //TODO SQLException
                    logger.error("connection.close()", e);
                    e.printStackTrace();
                }
        }
    }

    /**
     * переопределить в наследниках
     * @param result результат - массив строк
     * @param connection - соеденение к базе данных
     */
    public abstract void doWorck(ArrayList<String> result, Connection connection);


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
    // для теста
    @Override
    public void close() {
        //logger.info("close");
    }
}