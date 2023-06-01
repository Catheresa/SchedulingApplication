package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTime_DAO {

    public static String convertUTCDateTimeToString(ZonedDateTime utcZDT) {
        //utcZDT example: "2023-05-11T18:00Z[UTC]"
        // Intended to extract the date = "2023-05-11"
        String tempDate = String.valueOf(ZonedDateTime.parse(utcZDT.toString().substring(0,10)));
        System.out.println("tempDate: " + tempDate);
        // Intended to extract "18:00Z[UTC]"
        String tempTime = String.valueOf(ZonedDateTime.parse(utcZDT.toString().substring(11)));
        System.out.println("tempTime: " + tempTime);
        // Intended to extract "18:00"
        tempTime = tempTime.substring(11,5);

        String utcDateTimeString = tempDate + " " + tempTime + ":00";

        return utcDateTimeString;
    }

    public static String getLocalDateTimeString(ZonedDateTime localZDT){
        String tempDate = "placeholder";
        String tempTime = "placeholder";
        String localDateTimeString = tempDate + " " + tempTime + ":00";

        return localDateTimeString;
    }

    public static ObservableList<String> getMonths() {
        ObservableList<String> list = FXCollections.observableList(Arrays.asList("January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"));
        return list;
    }
    public static int convertMonthStringToInteger(String month) throws ParseException {
        String monthString = month;
        int monthNumber = Month.valueOf(monthString.toUpperCase()).getValue();
        System.out.println(month + " converted to a number is " + monthNumber);

        return monthNumber;
    }

}
