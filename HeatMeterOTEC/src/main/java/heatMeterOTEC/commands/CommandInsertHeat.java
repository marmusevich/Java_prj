package heatMeterOTEC.commands;

import com.google.gson.annotations.SerializedName;
import heatMeterOTEC.bd.DBContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 */
public class CommandInsertHeat extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(CommandInsertHeat.class);

    {
        mCommandType = CommandInsertHeat.class.toString();
    }

    @SerializedName("Serial_Number")
    private String mSerialNumber = "";
    @SerializedName("Data Time")
    private java.util.Date mDataTime = new java.util.Date();
    @SerializedName("Power")
    private double mPower = 0.0;
    @SerializedName("Temp 1")
    private float mTemp1 = 0;
    @SerializedName("Temp 2")
    private float mTemp2 = 0;
    @SerializedName("Energy")
    private double mEnergy = 0.0;
    @SerializedName("Manuals")
    private int mManuals = 0;



//    $result2 = ibase_query ($dbh, "INSERT INTO PARAMS (HEATMETER_ID, DATA, TIMES, POWER, TEMP1, TEMP2, ENERGY1, MANUALS)
//                            VALUES((select id from heatmeter where sn='$serial_number'), '$data', '$time', '0', '0', '0', '$energy', 1)") or die(ibase_errmsg());

    @Override
    public void doWorck(ArrayList<String> result, Connection connection) throws SQLException {

        System.out.println("newCommand ->  " + toString());



        //        String SQLText = " INSERT INTO TERMINAL_ERRORS (ID_TERMINAL ,ERROR_MSG)" +
//                " VALUES (?, ?) ";
//
//        int id_term = GetTerminalIDAndCheckSmenaIsOpen(connection);
//        PreparedStatement ps = connection.prepareStatement(SQLText);
//        ps.setInt(1, id_term);
//        ps.setString(1, error_msg);
//        int countChangeString = ps.executeUpdate();
//        if (countChangeString != -1) { // ok
//
//        } else { //error
//            result.add("500 Error insert record");
//        }
//        ps.close();



//        connection = DBContext.getConnectionDB();
//        String SQLText = " SELECT id, title FROM test ";
//        Statement ps = connection.createStatement();// prepareStatement(SQLText);
//        ResultSet rs = ps.executeQuery(SQLText);
//
//        while (rs.next()) {
//            System.out.println( "id = " + rs.getInt("id") +
//                    " - title =" +rs.getString("title").trim()
//            );
//        }
//
//        ps.close();
//        connection.close();

        //todo как возращать результат для сетерных команд
    }


    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        return super.toString() + " --> " +
                "Serial Number = <" + mSerialNumber + ">, " +
                "Data Time = " + dateFormat.format(mDataTime) + ", " +
                "Power = " + mPower + ", " +
                "(Temp 1 = " + mTemp1 + ", " +
                "Temp 2 = " + mTemp2 + "), " +
                "Energy = " + mEnergy + ", " +
                "Manuals = " + mManuals + ", ";
    }
}
