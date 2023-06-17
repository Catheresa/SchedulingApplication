package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/** A class used to identify and manipulate database fields related to dates and times. */
public class DateTime_DAO {

    /** A method to obtain a list of months to utilize in a dropdown selection.
     @return a list of months.
     */
    public static ObservableList<String> getMonths() {
        ObservableList<String> list = FXCollections.observableList(Arrays.asList("January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"));
        return list;
    }

    /** A method to convert string months to a number.
     @param month converts string month to a number.
     @return a list of months in number format. */
    public static int convertMonthStringToInteger(String month) throws ParseException {
        String monthString = month;
        int monthNumber = Month.valueOf(monthString.toUpperCase()).getValue();

        return monthNumber;
    }

}
