package heatMeterOTEC.bd;


import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import heatMeterOTEC.Parameters;

import java.sql.Connection;
import java.sql.SQLException;


public class DBContext {
    // ссылки на пулы
    static private BoneCP pooledConToTerminalDB = null;

	//	User ID=root;Password=myPassword;Host=localhost;Port=5432;Database=myDataBase;
	//	Pooling=true;Min Pool Size=0;Max Pool Size=100;Connection Lifetime=0;
	
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

    }


    public static void close() {
        if (pooledConToTerminalDB != null)
            pooledConToTerminalDB.shutdown();

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

}

