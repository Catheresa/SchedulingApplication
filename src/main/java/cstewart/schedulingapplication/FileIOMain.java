package cstewart.schedulingapplication;

import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/** A class created to notate when a user has logged in successfully or not. */
public class FileIOMain {

    /** A method to record login attempts to a .txt file.
     @param username along with whether the attempt was successful gets written into the .txt file.
     */
    public static void writeToFileLog(String username, boolean successful) throws IOException{
        // Filename variable
        String filename = "login_Activity.txt";
        // Create the timestamp
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        // Set desired format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // Set to Zone to UTC
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        // Convert the timestamp to UTC
        String utcString = dateFormat.format(timestamp);
        // Create FileWriter object to append to file
        FileWriter fwriter = new FileWriter(filename, true);
        // Create and open file
        PrintWriter outputFile = new PrintWriter(fwriter);
        // Create a different message depending upon successful or unsuccessful login attempt
        if (!successful){
            outputFile.println("'" + username + "'" + " attempted to log in unsuccessfully at " + utcString);
        }else{
            outputFile.println("'" + username + "'" + " logged in successfully at " + utcString);
        }
        // Close file
        outputFile.close();

    }

}
