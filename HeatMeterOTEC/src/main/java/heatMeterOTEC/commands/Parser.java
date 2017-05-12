package heatMeterOTEC.commands;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * парсить данные в каманду
 */
public class Parser {
    private static final Logger logger = LoggerFactory.getLogger(Parser.class);

    /**
     * Пытается распарсить команду
     *
     * @param commandData данные команды
     * @return распарсеную команду
     */
    public static AbstractCommand tryParseCommand(String commandData) {
        AbstractCommand ret = null;
        //logger.info("commandName ({}) commandData = '{}'", commandName, commandData);




        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(commandData);

        JsonObject rootObject = jsonElement.getAsJsonObject(); // чтение главного объекта
        String commandName = rootObject.get("Command_Type").getAsString();



        switch (commandName.toUpperCase()) {
            case "STOP_SERVER":
                ret = StopServerCommand.tryParseCommand(commandData);
                break;

            case "STATISTIC_SERVER":
                ret = StatisticServerCommand.tryParseCommand(commandData);
                break;


            default: // неопознаная командв
                ret = CommandInsertHeat.tryParseCommand(commandData);
        }
        return ret;





//jsonString =  {"mSerialNumber":"","mDataTime":"May 12, 2017 10:11:36 AM","mPower":0.0,"mTemp1":0.0,"mTemp2":0.0,"mEnergy":0.0,"mManuals":0}

//        JsonParser parser = new JsonParser();
//        JsonElement jsonElement = parser.parse(jsonString);
//
//        JsonObject rootObject = jsonElement.getAsJsonObject(); // чтение главного объекта
//        String message = rootObject.get("mSerialNumber").getAsString();
//        //java.util.Date mDataTime = rootObject.get("mDataTime").;
//        double mPower = rootObject.get("mPower").getAsDouble();
//        float mTemp1 = rootObject.get("mTemp1").getAsFloat();
//        float mTemp2 = rootObject.get("mTemp2").getAsFloat();
//        double mEnergy = rootObject.get("mEnergy").getAsDouble();
//        int mManuals = rootObject.get("mManuals").getAsInt();

    }


}

