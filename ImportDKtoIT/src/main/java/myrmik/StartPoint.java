package myrmik;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;


/**
 * Created by asus on 22.02.2017.
 */
public class StartPoint {
    private static final Logger logger = LoggerFactory.getLogger(StartPoint.class);

    private static final String FILE_NAME = "DK_021_2015.txt";

    public static void main(String[] args) {
        //logger.info(" Before start server...");

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(FILE_NAME), Charset.forName("windows-1251") ))){
            String line;
            int count = 1;

            while ((line = reader.readLine()) != null) {
                if( line != null && line.length() > 0 ){

                    String[] words = line.split("\t");
                    if( words != null && words.length == 2 ) {
                        //System.out.println("words.length != 2 ["+words.length+"] line = " + line);

                        String code = words[0].trim();
                        String caption = words[1].trim();
                        System.out.println("(" + code + ") - (" + caption + ")");

                        // добавить в базу

                        count++;
                    }
                }
            }
            System.out.println("count = " + count);
        } catch (IOException e) {
            // log error
        }
    }

    //public static void main(String[] args) {



}
