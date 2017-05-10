package heatMeterOTEC.bd;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import heatMeterOTEC.Parameters;
import java.sql.Connection;
import java.sql.SQLException;



//INSERT INTO test VALUES (3, 'test3');

public class DBContext  {
    // ссылки на пулы
    static private BoneCP pooledConnection = null;

    /**
     * инициация подключений к базе данных
     *
     * @param parameters
     * @throws Exception
     */
    public static void init(Parameters parameters) throws Exception {
        Class.forName("org.postgresql.Driver");

        BoneCPConfig config = new BoneCPConfig();
        config.setJdbcUrl(parameters.dbDatabase);
        config.setUsername(parameters.dbUserName);
        config.setPassword(parameters.dbPassword);
        config.setPoolName("DB");
        config.setMinConnectionsPerPartition(parameters.dbMinPoolSize);
        config.setMaxConnectionsPerPartition(parameters.dbMaxPoolSize);
        config.setPartitionCount(parameters.dbMaxStatements);
        config.setConnectionTimeoutInMs(parameters.dbConnectionTimeout);
        config.setIdleMaxAgeInSeconds(parameters.dbMaxIdleTime);
        pooledConnection = new BoneCP(config); // setup the connection pool
    }


    public static void close() {
        if (pooledConnection != null)
            pooledConnection.shutdown();

    }


    /**
     * получить подключение к Terminal
     *
     * @return
     * @throws SQLException
     */
    static public Connection getConnectionDB() throws SQLException {
        if (pooledConnection != null)
            return pooledConnection.getConnection();
        return null;
    }

}