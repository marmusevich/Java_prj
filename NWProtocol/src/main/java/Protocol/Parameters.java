package protocol;

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
    public int netWorkerThreads = 2;

    /**
     * количество потоков выполняющих команды
     */
    public int commandExecutorThreads = 4;

    /**
     * здесь при необходимости, загрузить откудото или переопределить
     */
    public Parameters(){
    //TODO настройки загрузить откудото или переопределить
    }

}
