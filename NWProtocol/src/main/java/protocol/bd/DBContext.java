package protocol.bd;


import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import protocol.Parameters;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;


/**
 * контекст базы данных, т.е. здесь подключение и еще что то
 */
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
        configToTerminalDB.setJdbcUrl(parameters.terminalDBDatabase); // jdbc url specific to your database, eg jdbc:mysql://127.0.0.1/yourdb
        configToTerminalDB.setUsername(parameters.terminalDBUserName);
        configToTerminalDB.setPassword(parameters.terminalDBPassword);
        configToTerminalDB.setPoolName("Terminal DB");
        configToTerminalDB.setMinConnectionsPerPartition(parameters.terminalDBMinPoolSize);
        configToTerminalDB.setMaxConnectionsPerPartition(parameters.terminalDBMaxPoolSize);
        configToTerminalDB.setPartitionCount(parameters.terminalDBMaxStatements);
        configToTerminalDB.setConnectionTimeoutInMs(parameters.terminalDBConnectionTimeout);
        configToTerminalDB.setIdleMaxAgeInSeconds(parameters.terminalDBMaxIdleTime);
        Properties propertiesToTerminalDB = new Properties();
        propertiesToTerminalDB.setProperty("encoding", parameters.terminalDBCharset);
        propertiesToTerminalDB.setProperty("sql_dialect", parameters.terminalDBSqlDialect);
        configToTerminalDB.setDriverProperties(propertiesToTerminalDB);
        pooledConToTerminalDB = new BoneCP(configToTerminalDB); // setup the connection pool

        //Working DB
        BoneCPConfig configToWorkingDB = new BoneCPConfig();
        configToWorkingDB.setJdbcUrl(parameters.workingDBDatabase); // jdbc url specific to your database, eg jdbc:mysql://127.0.0.1/yourdb
        configToWorkingDB.setUsername(parameters.workingDBUserName);
        configToWorkingDB.setPassword(parameters.workingDBPassword);
        configToWorkingDB.setPoolName("Working DB");
        configToWorkingDB.setMinConnectionsPerPartition(parameters.workingDBMinPoolSize);
        configToWorkingDB.setMaxConnectionsPerPartition(parameters.workingDBMaxPoolSize);
        configToWorkingDB.setPartitionCount(parameters.workingDBMaxStatements);
        configToWorkingDB.setConnectionTimeoutInMs(parameters.workingDBConnectionTimeout);
        configToWorkingDB.setIdleMaxAgeInSeconds(parameters.workingDBMaxIdleTime);
        Properties propertiesToWorkingDB = new Properties();
        propertiesToWorkingDB.setProperty("encoding", parameters.workingDBCharset);
        propertiesToWorkingDB.setProperty("sql_dialect", parameters.workingDBSqlDialect);
        configToTerminalDB.setDriverProperties(propertiesToWorkingDB);
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

