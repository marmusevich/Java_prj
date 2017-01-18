package protocol.bd;


import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import protocol.Parameters;

import java.sql.Connection;
import java.sql.SQLException;


public class DBContext {
    // ссылки на пулы
    static private BoneCP pooledConToTerminalDB = null;
    static private BoneCP pooledConToWorkingDB = null;

    /**
     * инициация подключений к базе данных
     *
     * @param parameters
     * @throws Exception
     */
    public static void init(Parameters parameters) throws Exception {
        Class.forName("org.firebirdsql.jdbc.FBDriver");

        // Terminal DB
        BoneCPConfig configToTerminalDB = new BoneCPConfig();
        String propTerminalDB = "?encoding="+parameters.terminalDBEncoding+"&charSet="+parameters.terminalDBCharset+"&sql_dialect="+parameters.terminalDBSqlDialect;
        configToTerminalDB.setJdbcUrl(parameters.terminalDBDatabase + propTerminalDB);
        configToTerminalDB.setUsername(parameters.terminalDBUserName);
        configToTerminalDB.setPassword(parameters.terminalDBPassword);
        configToTerminalDB.setPoolName("Terminal DB");
        configToTerminalDB.setMinConnectionsPerPartition(parameters.terminalDBMinPoolSize);
        configToTerminalDB.setMaxConnectionsPerPartition(parameters.terminalDBMaxPoolSize);
        configToTerminalDB.setPartitionCount(parameters.terminalDBMaxStatements);
        configToTerminalDB.setConnectionTimeoutInMs(parameters.terminalDBConnectionTimeout);
        configToTerminalDB.setIdleMaxAgeInSeconds(parameters.terminalDBMaxIdleTime);
        pooledConToTerminalDB = new BoneCP(configToTerminalDB); // setup the connection pool

        //Working DB
        BoneCPConfig configToWorkingDB = new BoneCPConfig();
        String propWorkingDB = "?encoding="+parameters.workingDBEncoding+"&charSet="+parameters.workingDBCharset+"&sql_dialect="+parameters.workingDBSqlDialect;
        configToWorkingDB.setJdbcUrl(parameters.workingDBDatabase + propWorkingDB);
        configToWorkingDB.setUsername(parameters.workingDBUserName);
        configToWorkingDB.setPassword(parameters.workingDBPassword);
        configToWorkingDB.setPoolName("Working DB");
        configToWorkingDB.setMinConnectionsPerPartition(parameters.workingDBMinPoolSize);
        configToWorkingDB.setMaxConnectionsPerPartition(parameters.workingDBMaxPoolSize);
        configToWorkingDB.setPartitionCount(parameters.workingDBMaxStatements);
        configToWorkingDB.setConnectionTimeoutInMs(parameters.workingDBConnectionTimeout);
        configToWorkingDB.setIdleMaxAgeInSeconds(parameters.workingDBMaxIdleTime);
        pooledConToWorkingDB = new BoneCP(configToWorkingDB); // setup the connection pool
    }


    public static void close() {
        if (pooledConToTerminalDB != null)
            pooledConToTerminalDB.shutdown();

        if (pooledConToWorkingDB != null)
            pooledConToWorkingDB.shutdown();
    }


    /**
     * получить подключение к Terminal
     *
     * @return
     * @throws SQLException
     */
    static public Connection getConnectionToTerminalDB() throws SQLException {
        if (pooledConToTerminalDB != null)
            return pooledConToTerminalDB.getConnection();
        return null;
    }

    /**
     * получить подключение к Working
     *
     * @return
     * @throws SQLException
     */
    static public Connection getConnectionToWorkingDB() throws SQLException {
        if (pooledConToWorkingDB != null)
            return pooledConToWorkingDB.getConnection();
        return null;
    }


}

