package myrmik;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.sql.*;


/**
 * Created by asus on 22.02.2017.
 */
public class StartPoint {
    private static final String FILE_NAME = "DK_021_2015.txt";

    public static void main(String[] args) {
        StartPoint sp = new StartPoint();
        sp.main();
    }


    public void main() {

//        try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:test");
//
//
//                try (Statement statement = connection.createStatement()) {
//
//        connection.setAutoCommit(false);
//
//        try {
//            statement.execute("insert into user(name) values('kesha')");
//            connection.commit();
//        } catch (SQLException e)  {
//            connection.rollback();
//        }
//
//        connection.setAutoCommit(true);
//    }


        Connection con = null;
        //получить подключение
        try {
            Class.forName("org.h2.Driver");
            con = DriverManager.getConnection("jdbc:h2:mem:test", "sa", "");

            readFile(con);



        con.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //прочитать файл и записать в бд
    public void readFile(Connection con) {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(FILE_NAME), Charset.forName("windows-1251") ))){
            String line;
            while ((line = reader.readLine()) != null) {
                if( line != null && line.length() > 0 ){

                    String[] words = line.split("\t");
                    if( words != null && words.length == 2 ) {
                        String code = words[0].trim();
                        String caption = words[1].trim();
                        updateDB(con, count, code, caption);
                        count++;
                    }
                    else {
                        System.out.println("line is not world  ->" + line);
                    }
                }
                else {
                    System.out.println("line is NULL ->" + line);

                }
            }
            System.out.println("count = " + count);
        } catch (IOException e) {
            // log error
            count = -1;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

    }
    public void updateDB(Connection con, int numer, String code, String caption) throws SQLException {

    }
}
