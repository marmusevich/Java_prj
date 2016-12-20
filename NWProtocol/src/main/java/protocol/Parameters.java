package protocol;

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
    public int blockingQueueCapacity = 10;

    /**
     * время ожидания постоновки команды в очередь в милисикундах
     */
    public long commandAdTimeout = 10;

    // ---------- ГРУППА БД -------------------
    /**
     * кодировка для сообщений в базе
     */
    public Charset bdCharset = Charset.forName("windows-1251");


//    setMaxPoolSize(5);
//    setMinPoolSize(2);
//    setMaxStatements(10);
//    setMaxIdleTime(30 * 60 * 60);
//    setEncoding("WIN1251"); bdCharset.toString()
//    setSqlDialect("3");
//1 база    setDatabase("localhost/3050:E:/a.marmusevich/TERMINAL/TERMINAL.FDB");
//2 база    setDatabase("localhost/3050:E:/a.marmusevich/WORKING/WORKING.FDB");
//    setUserName("SYSDBA");
//    setPassword("masterkey");




    /**
     * здесь при необходимости, загрузить откудото или переопределить
     */
    public Parameters(){
    //TODO настройки загрузить откудото или переопределить



    }

}
