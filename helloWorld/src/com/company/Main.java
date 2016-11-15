package com.company;
import java.io.*;
import java.net.*;


public class Main {

    public static void main(String[] args) {
        // write your code here

        try
        {
            Socket s = new Socket("time-A.timefreq.bldrdoc.gov",13);

            BufferedReader in = new BufferedReader   (new InputStreamReader(s.getInputStream()));
            boolean more = true;
            while (more)
            {
                String line = in.readLine();
                if (line == null)
                    more = false;
                else
                    System.out.println(line);
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}
