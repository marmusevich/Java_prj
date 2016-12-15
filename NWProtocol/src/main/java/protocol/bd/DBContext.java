package protocol.bd;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.Reference;
import javax.naming.StringRefAddr;



/**
 * контекст базы данных, т.е. здесь подключение и еще что то
 */
public class DBContext {
    // заглушка

//пример подключения с пулом
    public static void main(String[] args) throws Exception {
        Reference ref = new Reference("org.firebirdsql.pool.FBConnectionPoolDataSource");
        ref.add(new StringRefAddr("maxPoolSize", "5"));
        ref.add(new StringRefAddr("minPoolSize", "2"));
        ref.add(new StringRefAddr("maxStatements", "10"));
        ref.add(new StringRefAddr("maxIdleTime", "108000"));
        ref.add(new StringRefAddr("database","localhost/3050:C:/db/employee.fdb"));
        ref.add(new StringRefAddr("user", "SYSDBA"));
        ref.add(new StringRefAddr("password", "masterkey"));
        Context ctx = new InitialContext();
        ctx.bind("jdbc/test", ref);
    }

}

