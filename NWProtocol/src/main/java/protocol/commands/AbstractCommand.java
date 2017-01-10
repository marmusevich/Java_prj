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
    /**
     * название параметров имени и пароля пользователя
     */
    public static final String UserParametrName = "ID_TERM";
    public static final String PassParametrName = "PASSWORD";
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
     * @param connectionToTerminalDB - подключение к БД
     * @return
     */
    final public boolean checkUserNameAndPass(Connection connectionToTerminalDB) throws SQLException {
        return GetTerminalIDAndCheckSmenaIsOpen(connectionToTerminalDB) != 0;
    }

    /**
     * Получает ID терминала с проверкой на открытую смену
     *
     * @param connectionToTerminalDB
     * @return
     * @throws SQLException
     */
    final public int GetTerminalIDAndCheckSmenaIsOpen(Connection connectionToTerminalDB) throws SQLException {
        int terminalID = 0;

        String SQLText =
                "SELECT ADDRES, ID, BANK_ID FROM TERMINAL WHERE " +
                        "    TERMINAL_ID= ? AND BANK_ID =(SELECT BANK FROM USERS WHERE LOGIN= ?) AND " +
                        "    (SELECT count(*) FROM SMENA WHERE DATA_K is null and SMENA.id_terminal=TERMINAL.ID)>0";

        PreparedStatement ps = connectionToTerminalDB.prepareStatement(SQLText);
        ps.setString(1, userAuthenticationData.name);
        ps.setString(2, userAuthenticationData.pass);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            terminalID = rs.getInt("ID");
        }
        ps.close();

        return terminalID;
    }

    /**
     * Получим внутренний ID терминала
     *
     * @param connectionToTerminalDB
     * @param terminalId             Идентификатор терминала, обязательный параметр;
     * @return
     * @throws SQLException
     */
    //
    final public int GetTerminalID(Connection connectionToTerminalDB, String terminalId) throws SQLException {
        int terminalID = 0;

        String SQLText = "SELECT ID FROM TERMINAL WHERE terminal_id= ? ";

        PreparedStatement ps = connectionToTerminalDB.prepareStatement(SQLText);
        ps.setString(1, terminalId);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            terminalID = rs.getInt("ID");
        }
        ps.close();

        return terminalID;

//        function TDM1.GetTermnalID(ID_TERMINAL: string):integer;
//        begin
//        Result:=0;
//        sqlFreeReturn.Close;
//        sqlFreeReturn.SelectSQL.Text:='SELECT ID FROM TERMINAL WHERE terminal_id=:ID_TERMINAL';
//        sqlFreeReturn.ParamByName('ID_TERMINAL').asString:=ID_TERMINAL;
//        sqlFreeReturn.Open;
//        Result:=sqlFreeReturn.FieldByName('ID').asInteger;

    }




//todo  передавать ошибки, при соеденении и т.п.
//        AContext.Connection.Socket.WriteLn('200 OK',TEncoding.UTF8);
//        GET_USLUGA.Free;
//        end
//        else
//                    except
//                    Result:='500 Error open SQL';
//                    result:='500 Error connect FIB';
//        AContext.Connection.Socket.WriteLn(Results);
//        AContext.Connection.Socket.Close;
//        end
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
            } else
                sendError(ErrorFactory.Error.AccessDenied);

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
     *
     * @param result                 результат - массив строк
     * @param connectionToTerminalDB - соеденение к базе данных
     * @param connectionToWorkingDB  - соеденение к базе данных
     */
    public abstract void doWorck(ArrayList<String> result, Connection connectionToTerminalDB, Connection connectionToWorkingDB) throws SQLException;

    /**
     * Вернуть результат
     *
     * @return набор строк результата
     */
    final public ArrayList<String> getResult() {
        return result;
    }


    /**
     * отправить результат если он есть
     */
    final public void sendResult() {
        //TODO проверить а активно ли соеденение
        //TODO  убивать не активные каналы
        logger.info("sendResult {}", ctx.pipeline().channel().remoteAddress().toString());

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