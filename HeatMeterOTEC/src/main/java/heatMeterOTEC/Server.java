package heatMeterOTEC;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import heatMeterOTEC.bd.DBContext;
import heatMeterOTEC.net.NetServer;

import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

import com.google.gson.Gson;

import heatMeterOTEC.commands.*;

//TODO правильно перехватывать исключения из потоков

/**
 *
 */
public final class Server {
    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    private static CommandServer commandServer;
    private static NetServer netServer;
    private static Parameters parameters;

    /**
     * @param args
     */
    public static void main(String[] args) {
        logger.info(" Before start server...");



        CommandInsertHeat command = new CommandInsertHeat();

        // convert to json
        Gson gson = new Gson();
        String jsonString = gson.toJson(command);
//jsonString =  {"Serial_Number":"","Data Time":"May 12, 2017 2:39:22 PM","Power":0.0,"Temp 1":0.0,"Temp 2":0.0,"Energy":0.0,"Manuals":0,"Command_Type":"class heatMeterOTEC.commands.CommandInsertHeat","User_Name":"","User_Pass":""}
//jsonString =  "{\"mDataTime\":\"May 12, 2017 09:11:36 AM\"}";
        System.out.println("json " + jsonString);

//        // convert from json
//        CommandInsertHeat newCommand = gson.fromJson(jsonString, CommandInsertHeat.class);
//        System.out.println("newCommand ->  " + newCommand.toString());


        AbstractCommand newCommand = Parser.tryParseCommand(jsonString);
        System.out.println("newCommand ->  " + newCommand.toString());

        //start();
    }


    /**
     * запустить
     */
    public static void start() {
        parameters = new Parameters();
        try {
            //инитить пул БД
            DBContext.init(parameters);

            commandServer = new CommandServer(parameters.commandExecutorThreads, parameters.blockingQueueCapacity, parameters.commandAdTimeout);

            netServer = new NetServer(parameters.netBossThreads, parameters.netWorkerThreads);
            netServer.ConfigureSSL(parameters.isSSL);
            netServer.start(parameters.port, parameters.netCharset);
        } catch (Exception e) {
            stop();
            logger.error(" server stoped on start. ERROR= ", e);

            e.printStackTrace();

        }
    }

    /**
     * остановить сервер
     */
    public static void stop() {
        DBContext.close();
        if (netServer != null)
            netServer.stop();
        if (commandServer != null)
            commandServer.stop(false);

        logger.info("server stop");
    }

    public static CommandServer getCommandServer() {
        return commandServer;
    }

    public static NetServer getNetServer() {
        return netServer;
    }

    public static Parameters getParameters() {
        return parameters;
    }

}