package protocol.bd;

//import org.firebirdsql.*;

import org.firebirdsql.pool.*;
import protocol.Parameters;

import javax.sql.PooledConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * контекст базы данных, т.е. здесь подключение и еще что то
 */
public class DBContext {
    // ссылки на пулы
    static private PooledConnection pooledConToTerminalDB = null;
    static private PooledConnection pooledConToWorkingDB = null;

    /**
     * инициация подключений к базе данных
     * @param parameters
     * @throws Exception
     */
    public static void init(Parameters parameters) throws Exception{
        //todo заменить пул соеденений

        // Terminal DB
        org.firebirdsql.pool.FBConnectionPoolDataSource poolToTerminalDB = new FBConnectionPoolDataSource();
        poolToTerminalDB.setMaxPoolSize(parameters.terminalDBMaxPoolSize);
        poolToTerminalDB.setMinPoolSize(parameters.terminalDBMinPoolSize);
        poolToTerminalDB.setMaxStatements(parameters.terminalDBMaxStatements);
        poolToTerminalDB.setMaxIdleTime(parameters.terminalDBMaxIdleTime);
        poolToTerminalDB.setEncoding(parameters.terminalDBCharset.name());
        poolToTerminalDB.setSqlDialect(parameters.terminalDBSqlDialect);
        poolToTerminalDB.setDatabase(parameters.terminalDBDatabase);
        poolToTerminalDB.setUserName(parameters.terminalDBUserName);
        poolToTerminalDB.setPassword(parameters.terminalDBPassword);
        // obtain a physical connection to the database
        pooledConToTerminalDB = poolToTerminalDB.getPooledConnection();


        //Working DB

        org.firebirdsql.pool.FBConnectionPoolDataSource poolToWorkingDB = new FBConnectionPoolDataSource();
        poolToWorkingDB.setMaxPoolSize(parameters.workingDBMaxPoolSize);
        poolToWorkingDB.setMinPoolSize(parameters.workingDBMinPoolSize);
        poolToWorkingDB.setMaxStatements(parameters.workingDBMaxStatements);
        poolToWorkingDB.setMaxIdleTime(parameters.workingDBMaxIdleTime);
        poolToWorkingDB.setEncoding(parameters.workingDBCharset.name());
        poolToWorkingDB.setSqlDialect(parameters.workingDBSqlDialect);
        poolToWorkingDB.setDatabase(parameters.workingDBDatabase);
        poolToWorkingDB.setUserName(parameters.workingDBUserName);
        poolToWorkingDB.setPassword(parameters.workingDBPassword);
        // obtain a physical connection to the database
        pooledConToWorkingDB = poolToWorkingDB.getPooledConnection();
    }

    /**
     * получить подключение к Terminal
     * @return
     * @throws SQLException
     */
    static public Connection getConnectionToTerminalDB() throws SQLException {
        if(pooledConToTerminalDB != null)
                return pooledConToTerminalDB.getConnection();

        return null;
    }

    /**
     * получить подключение к Working
     * @return
     * @throws SQLException
     */
    static public Connection getConnectionToWorkingDB() throws SQLException {
        if(pooledConToWorkingDB != null)
            return pooledConToWorkingDB.getConnection();

        return null;
    }



}

