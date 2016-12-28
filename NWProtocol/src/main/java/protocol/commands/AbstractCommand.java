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


    protected String userName;
    protected String userPass;
    /**
     *  установить имя пользователя и пароль
     * @param userName - пользователь
     * @param userPass - пароль
     */
    final public void setUserNameAndPass(String userName, String userPass){
        this.userName = userName;
        this.userPass = userPass;

        //logger.info("setUserNameAndPass: userName = ({}) userPass = ({})", userName, userPass);
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
        ps.setString(1, userName);
        ps.setString(2, userPass);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            dostup = rs.getInt("ID");//Integer.getInteger(rs.getString("ID"));

            System.out.println("dostup=" + dostup +     " -> ADDRES = " + rs.getString("ADDRES") + ", ID = " + rs.getString("ID") + "BANK_ID = " + rs.getString("BANK_ID"));
        }



        return true;
        //return dostup != 0;



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
    final public void execute() {
        Connection connectionToTerminalDB = null;
        Connection connectionToWorkingDB = null;
        try {
            connectionToTerminalDB = DBContext.getConnectionToTerminalDB();
            connectionToWorkingDB = DBContext.getConnectionToWorkingDB();
            //TODO connection = dbContext.getConnection(); // получить соеденение из пула

            if (checkUserNameAndPass(connectionToTerminalDB)) {
                result = new ArrayList<String>();

                doWorck(result, connectionToTerminalDB, connectionToWorkingDB);

                result.add("   userName = ("+userName+") userPass = ("+userPass+")" );
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
    public abstract void doWorck(ArrayList<String> result, Connection connectionToTerminalDB, Connection connectionToWorkingDB);





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