package protocol.bd;

//import org.firebirdsql.*;

import org.firebirdsql.pool.*;

import javax.sql.PooledConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * контекст базы данных, т.е. здесь подключение и еще что то
 */
public class DBContext {

    //E:\a.marmusevich\TERMINAL\TERMINAL.FDB
    //E:\a.marmusevich\WORKING\WORKING.FDB

    // заглушка

    //todo заменить пул соеденений

//пример подключения с пулом
    public static void main(String[] args) throws Exception {
        org.firebirdsql.pool.FBConnectionPoolDataSource pool = new org.firebirdsql.pool.FBConnectionPoolDataSource();
//        org.firebirdsql.pool.FBPooledConnection pool = new org.firebirdsql.pool.FBPooledConnection();

        pool.setMaxPoolSize(5);
        pool.setMinPoolSize(2);
        pool.setMaxStatements(10);
        pool.setMaxIdleTime(30 * 60 * 60);
        pool.setEncoding("WIN1251");
        //pool.setSqlDialect("3");
        pool.setDatabase("localhost/3050:E:/a.marmusevich/TERMINAL/TERMINAL.FDB");
        //pool.setDatabase("localhost/3050:E:/a.marmusevich/WORKING/WORKING.FDB");
        pool.setUserName("SYSDBA");
        pool.setPassword("masterkey");
        // obtain a physical connection to the database
        PooledConnection pooledCon = pool.getPooledConnection();
        // obtain a wrapped connection
        Connection connection = pooledCon.getConnection();
        try {

            PreparedStatement ps = connection.prepareStatement("SELECT * FROM USERS_TERM_STATE");
//            ps.setInt(1, 1);
//                PreparedStatement ps = connection.prepareStatement("SELECT * FROM USERS_TERM_STATE WHERE id = ?");
//                ps.setInt(1, 1);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    System.out.println("LOGIN = " + rs.getString("LOGIN") + ", USERNAME = " + rs.getString("USERNAME"));
                }
        } finally {
            // release the connection back to pool
            connection.close();
        }

    }


    PooledConnection pooledCon;

    public  void init() throws Exception {
        org.firebirdsql.pool.FBConnectionPoolDataSource pool = new FBConnectionPoolDataSource();
//        org.firebirdsql.pool.FBPooledConnection pool = new org.firebirdsql.pool.FBPooledConnection();

        pool.setMaxPoolSize(5);
        pool.setMinPoolSize(2);
        pool.setMaxStatements(10);
        pool.setMaxIdleTime(30 * 60 * 60);
        pool.setEncoding("WIN1251");
        //pool.setSqlDialect("3");
        pool.setDatabase("localhost/3050:E:/a.marmusevich/TERMINAL/TERMINAL.FDB");
        //pool.setDatabase("localhost/3050:E:/a.marmusevich/WORKING/WORKING.FDB");
        pool.setUserName("SYSDBA");
        pool.setPassword("masterkey");
        // obtain a physical connection to the database
        pooledCon = pool.getPooledConnection();

    }

    public Connection getConnection() throws SQLException {
        return pooledCon.getConnection();
    }








//
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
//
//    private String GetTerminalString(int ID) throws SQLException {
//        String ID_TERMINAL="";
//        //System.out.println("ID_TERMINAL="+ID_TERMINAL);
//
//        String SQLText = "SELECT TERMINAL_ID, ID FROM TERMINAL WHERE id=" + String.valueOf(ID);
//        ResultSet rs = DB_TERMINAL.resultQuery(SQLText);
//        while (rs.next()) {
//            ID_TERMINAL = rs.getString("TERMINAL_ID");
//        }
//        return ID_TERMINAL;
//    }

}

