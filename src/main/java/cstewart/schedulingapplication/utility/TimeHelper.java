package cstewart.schedulingapplication.utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/** A class for assisting with obtaining time lists in EST. */
public class TimeHelper {
    private static ObservableList<LocalTime> startTimeList = FXCollections.observableArrayList();
    private static ObservableList<LocalTime> endTimeList = FXCollections.observableArrayList();
    public static final ZonedDateTime START_HOURS_EST = ZonedDateTime.of(LocalDate.now(), LocalTime.of(8,0), ZoneId.of("America/New_York"));

    /** The creation of a start time list. */
    public static ObservableList<LocalTime> getStartTimeList() {
        if(startTimeList.isEmpty()) {
            populateLists();
        }
        return startTimeList;
    }

    /** The creation of a end time list. */
    public static ObservableList<LocalTime> getEndTimeList() {
        if(endTimeList.isEmpty()) {
            populateLists();
        }
        return endTimeList;
    }

    /** A method that aids in populating lists. */
    private static void populateLists(){
        ZonedDateTime localStartTime = START_HOURS_EST.withZoneSameInstant(ZoneId.systemDefault());
        ZonedDateTime localEndTime = localStartTime.plusHours(14);

        while (localStartTime.isBefore(localEndTime)){
            startTimeList.add(localStartTime.toLocalTime());
            localStartTime = localStartTime.plusMinutes(15);
            endTimeList.add(localStartTime.toLocalTime());
        }
    }
}
