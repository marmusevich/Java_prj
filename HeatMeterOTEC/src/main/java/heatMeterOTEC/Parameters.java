package heatMeterOTEC;

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
    public int port = 5051;

    /**
     * кодировка для сообщений из сети
     */
    public Charset netCharset = Charset.forName("utf-16");

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
    /**
     * максимальное количество подключений к базе
     */
    public int dbMaxPoolSize = 5;
    /**
     * минимальное количество подключений
     */
    public int dbMinPoolSize = 2;
    /**
     * количество закешированых запросов в одном подключении
     */
    public int dbMaxStatements = 4;
    /**
     * тайм аут простоя подключенийя в секундах
     */
    public long dbMaxIdleTime = 30 * 60;
    /**
     * время ожидания при подключении
     */
    public long dbConnectionTimeout = 500;
   /**
     * строка подключения к базе
     */
    //public String terminalDBDatabase = "jdbc:firebirdsql:localhost/3050:c:/_lexa/java/TERMINAL/TERMINAL.FDB";
    public String dbDatabase = "jdbc:postgresql://localhost/heatMeterOTEC";

   /**
     * имя пользователя
     */
    public String dbUserName = "javaServer";
   /**
     * пароль
     */
    public String dbPassword = "123456";



    /**
     * здесь при необходимости, загрузить откудото или переопределить
     */
    public Parameters() {
        //TODO настройки загрузить откудото или переопределить
    }
}
