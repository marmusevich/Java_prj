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
     * кодировка для сообщений из сети
     */
    public Charset netCharset = Charset.forName("windows-1251");

    /**
     * кодировка для сообщений в базе
     */
    public Charset bdCharset = Charset.forName("windows-1251");

    /**
     * здесь при необходимости, загрузить откудото или переопределить
     */
    public Parameters(){
    //TODO настройки загрузить откудото или переопределить
    }

}
