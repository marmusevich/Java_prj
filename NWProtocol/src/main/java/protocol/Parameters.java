package protocol;

import com.sun.java.swing.plaf.windows.resources.windows;

import java.nio.charset.Charset;

/**
 * параметры
 */
public class Parameters {

    /**
     * использовать ли SSL
     */
    public boolean isSSL = System.getProperty("ssl") != null;

    /**
     * слушаемый порт
     */
    public int port = 5050;

    /**
     * кодировка для сообщений из сети
     */
    public Charset netCharset = Charset.forName("windows-1251");

    /**
     * количество слушающих сеть потоков
     */
    public int netBossThreads = 1;

    /**
     * количество потоков обрабатывающие подключения
     */
    public int netWorkerThreads = 1;

    /**
     * количество потоков выполняющих команды
     */
    public int commandExecutorThreads = 2;

    /**
     * ёмкость блокирующей очереди для команд
     */
    public int blockingQueueCapacity = 1000;

    /**
     * время ожидания постоновки команды в очередь в милисикундах
     */
    public long commandAdTimeout = 10;

    // ---------- ГРУППА БД -------------------
    //TerminalDB
    /**
     * максимальное количество подключений к базе
     */
    public int terminalDBMaxPoolSize = 5;
    /**
     * минимальное количество подключений
     */
    public int terminalDBMinPoolSize = 2;
    /**
     * количество закешированых запросов в одном подключении
     */
    public int terminalDBMaxStatements = 4;
    /**
     * тайм аут простоя подключенийя в секундах
     */
    public long terminalDBMaxIdleTime = 30 * 60;
    /**
     * время ожидания при подключении
     */
    public long terminalDBConnectionTimeout = 500;
    /**
     * кодировка для сообщений в базе - намиенование БД
     */
    public String terminalDBEncoding = "WIN1251";
    /**
     * кодировка для сообщений в базе - намиенование JAVA для преобразования
     */
    public String terminalDBCharset = "windows-1251";
    /**
     * диалект SQL
     */
    public String terminalDBSqlDialect = "3";
    /**
     * строка подключения к базе
     */
    //public String terminalDBDatabase = "jdbc:firebirdsql:localhost/3050:E:/a.marmusevich/TERMINAL/TERMINAL.FDB";
    public String terminalDBDatabase = "jdbc:firebirdsql:localhost/3050:c:/_lexa/java/DB_Firebird/TERMINAL.FDB";
    //"localhost/3050:E:/a.marmusevich/TERMINAL/TERMINAL.FDB"
    //"E:\a.marmusevich\TERMINAL\TERMINAL.FDB"

    /**
     * имя пользователя
     */
    public String terminalDBUserName = "SYSDBA";
    /**
     * пароль
     */
    public String terminalDBPassword = "masterkey";

    //Working DB
    /**
     * максимальное количество подключений к базе
     */
    public int workingDBMaxPoolSize = 5;
    /**
     * количество одновременных запросов в одном подключении
     */
    public int workingDBMinPoolSize = 2;
    /**
     * количество закешированых запросов в одном подключении
     */
    public int workingDBMaxStatements = 4;
    /**
     * тайм аут простоя подключенийя в секундах
     */
    public long workingDBMaxIdleTime = 30 * 60;
    /**
     * время ожидания при подключении
     */
    public long workingDBConnectionTimeout = 500;

    /**
     * кодировка для сообщений в базе - намиенование БД
     */
    public String workingDBEncoding = "WIN1251";
    /**
     * кодировка для сообщений в базе - намиенование JAVA для преобразования
     */
    public String workingDBCharset = "windows-1251";
    /**
     * диалект SQL
     */
    public String workingDBSqlDialect = "3";
    /**
     * строка подключения к базе
     */
    public String workingDBDatabase = "jdbc:firebirdsql:localhost/3050:c:/_lexa/java/DB_Firebird/WORKING.FDB";
    //"localhost/3050:E:/a.marmusevich/WORKING/WORKING.FDB"
    //"E:\a.marmusevich\WORKING\WORKING.FDB"
    /**
     * имя пользователя
     */
    public String workingDBUserName = "SYSDBA";
    /**
     * пароль
     */
    public String workingDBPassword = "masterkey";


    /**
     * здесь при необходимости, загрузить откудото или переопределить
     */
    public Parameters() {
        //TODO настройки загрузить откудото или переопределить
    }
}
