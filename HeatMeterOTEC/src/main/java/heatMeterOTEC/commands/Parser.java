package heatMeterOTEC.commands;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;


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

        Gson gson = new Gson();

        if (commandName.equalsIgnoreCase(CommandInsertHeat.class.toString())  || commandName.equalsIgnoreCase(""))
            ret = gson.fromJson(commandData, CommandInsertHeat.class); // по умолчанию передача данных
        else if (commandName.equalsIgnoreCase(StopServerCommand.class.toString()))
            ret = gson.fromJson(commandData, StopServerCommand.class);
        else if (commandName.equalsIgnoreCase(StatisticServerCommand.class.toString()))
            ret = gson.fromJson(commandData, StatisticServerCommand.class);
        else
            ret = gson.fromJson(commandData, UnknownCommand.class);

        return ret;

    }
}

