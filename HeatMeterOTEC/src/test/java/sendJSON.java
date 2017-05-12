import com.google.gson.Gson;
import heatMeterOTEC.Parameters;
import heatMeterOTEC.commands.CommandInsertHeat;

import java.net.*;
import java.io.*;

/**
 * Created by a.marmusevich on 12.05.2017.
 */
public class sendJSON {

    public static void main(String[] args) {
        CommandInsertHeat command = new CommandInsertHeat();

        // convert to json
        Gson gson = new Gson();
        String jsonString = gson.toJson(command);
        //jsonString =  {"Serial_Number":"","Data Time":"May 12, 2017 2:39:22 PM","Power":0.0,"Temp 1":0.0,"Temp 2":0.0,"Energy":0.0,"Manuals":0,"Command_Type":"class heatMeterOTEC.commands.CommandInsertHeat","User_Name":"","User_Pass":""}
        //System.out.println("json " + jsonString);

        Parameters parameters = new Parameters();


        try{
            Socket socket = new Socket("127.0.0.1", parameters.port);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            for(int i = 0; i < 1; i++){
                System.out.println("i = " + i + " --> " + jsonString);
                out.println(jsonString);
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
