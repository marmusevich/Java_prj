package protocol;

import org.firebirdsql.pool.FBConnectionPoolDataSource;

import java.nio.charset.Charset;

/**
 * Created by asus on 04.12.2016.
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
    public int terminalDBMaxStatements = 10;
    /**
     * тайм аут простоя подключенийя в секундах
     */
    public int terminalDBMaxIdleTime = 30 * 60;
    /**
     * кодировка для сообщений в базе
     */
    public Charset terminalDBCharset = Charset.forName("windows-1251");
    /**
     * диалект SQL
     */
    public String terminalDBSqlDialect = "3";
    /**
     * строка подключения к базе
     */
    public String terminalDBDatabase = "localhost/3050:E:/a.marmusevich/TERMINAL/TERMINAL.FDB";
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
    public int workingDBMaxStatements  = 10;
    /**
     * тайм аут простоя подключенийя в секундах
     */
    public int workingDBMaxIdleTime = 30 * 60;
    /**
     * кодировка для сообщений в базе
     */
    public Charset workingDBCharset = Charset.forName("windows-1251");
    /**
     * диалект SQL
     */
    public String workingDBSqlDialect = "3";
    /**
     * строка подключения к базе
     */
    public String workingDBDatabase = "localhost/3050:E:/a.marmusevich/WORKING/WORKING.FDB";
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
    public Parameters(){
    //TODO настройки загрузить откудото или переопределить
    }
}
