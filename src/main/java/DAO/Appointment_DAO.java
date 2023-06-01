package DAO;

import cstewart.schedulingapplication.model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.*;
import java.lang.String;
import java.time.temporal.ChronoUnit;


public class Appointment_DAO {

    private static final ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    private static final ObservableList<Appointment> allAppointmentViews = FXCollections.observableArrayList();
    private static final ObservableList<Appointment> allCustomerAppointments = FXCollections.observableArrayList();
//    private static final ObservableList<Appointment> allLocations = FXCollections.observableArrayList();
//    private static final ObservableList<Appointment> allAppointmentsByTypeMonth = FXCollections.observableArrayList();

    // Write code to provide an alert when there ia an appointment within 15 minutes of the user's log-in.
    // A custom message should be displayed in the user interface and include: AppointmentID, date, and time.
    // if user does not have any appointments within 15 minutes of logging in, display a custom message in the user...
    // interface indicating there are no upcoming appointments.
    public static ObservableList<Appointment> appointmentWithinMinutesOfLogin(int user_ID) throws SQLException {
        ObservableList<Appointment> filteredApptWithinMinutesOfLogin = FXCollections.observableArrayList();

        // This isn't working.  Will it work when I get it into the virtual environment?
        String sqlQuery = "SELECT * FROM appointments " +
                "WHERE date(Start) BETWEEN now()" +
                "AND DATE_ADD(NOW(), INTERVAL 15 MINUTE)" +
                "AND User_ID = ?";

        PreparedStatement preparedStatement = JDBC_DAO.getConnection().prepareStatement(sqlQuery);
        preparedStatement.setInt(1, user_ID);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            filteredApptWithinMinutesOfLogin.add(new Appointment(
                    resultSet.getInt("Appointment_ID"),
                    resultSet.getTimestamp("Start").toLocalDateTime()));
        }
        return filteredApptWithinMinutesOfLogin;
    }

    // Method that provides data for the Appointment view based on the number of days provided by user selection.
    public static ObservableList<Appointment> getView(int viewDays) throws SQLException {
        allAppointmentViews.clear();

        String sqlQuery = "SELECT a.*, c.* " +
                "FROM appointments a " +
                "JOIN contacts c ON a.Contact_ID = c.Contact_ID " +
                "WHERE date(Start) BETWEEN current_date() AND date_add(current_date(), interval " + viewDays + " day)";
            PreparedStatement preparedStatement = JDBC_DAO.getConnection().prepareStatement(sqlQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                allAppointmentViews.add(new Appointment(
                        resultSet.getInt("Appointment_ID"),
                        resultSet.getString("Title"),
                        resultSet.getString("Description"),
                        resultSet.getString("Location"),
                        resultSet.getString("Contact_Name"),
                        resultSet.getString("Type"),
                        resultSet.getTimestamp("Start").toLocalDateTime(),
                        resultSet.getTimestamp("End").toLocalDateTime(),
                        resultSet.getInt("Customer_ID"),
                        resultSet.getInt("User_ID")));
        }

        return allAppointmentViews;
    }
    public static ObservableList<String> getTypes() throws SQLException {
        ObservableList<String> filteredAppointmentListTypes = FXCollections.observableArrayList();
        ObservableList<Appointment> allAppointments = getAllAppointments();
        try {
            for (int i = 0; i < allAppointments.size(); i++) {
                Appointment searchedAppointment = allAppointments.get(i);

                String type = searchedAppointment.getType();
                if (!filteredAppointmentListTypes.contains(type)) {
                    filteredAppointmentListTypes.add(type);
                }
            }
            return filteredAppointmentListTypes;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static int countAppointmentByTypeAndMonth(String type, int month) {
        int count = 0;
        try {
            String sqlQuery = "SELECT COUNT(*) as cnt FROM appointments WHERE Type = ? AND MONTH(Start) = ?"; // Lookup count for SQL query (group by or aggregate functions)
            PreparedStatement preparedStatement = JDBC_DAO.getConnection().prepareStatement(sqlQuery);
            preparedStatement.setString(1, type);
            preparedStatement.setInt(2, month);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                count = resultSet.getInt("cnt");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }

//    public static String countAppointmentsByTypeAndMonth(String type, int month) throws SQLException {
////        ObservableList<Appointment> allAppointmentsByTypeMonth = FXCollections.observableArrayList();
////
////        int count = 0;
////
////        try {
//            String sqlQuery = "SELECT * FROM appointments WHERE Type = ? AND MONTH(Start) = ?"; // Lookup count for SQL query (group by or aggregate functions)
//            PreparedStatement preparedStatement = JDBC_DAO.getConnection().prepareStatement(sqlQuery);
////            preparedStatement.setString(1, type);
////            preparedStatement.setInt(2, month);
//            ResultSet resultSet = preparedStatement.executeQuery();
//
////            while (resultSet.next()) {
////                allAppointmentsByTypeMonth.add(new Appointment(
////                        resultSet.getInt("Appointment_ID"),
////                        resultSet.getString("Title"),
////                        resultSet.getString("Description"),
////                        resultSet.getString("Location"),
////                        resultSet.getString("Type"),
////                        resultSet.getTimestamp("Start").toLocalDateTime(),
////                        resultSet.getTimestamp("End").toLocalDateTime(),
////                        resultSet.getString("Create_Date"),
////                        resultSet.getString("Created_By"),
////                        resultSet.getString("Last_Update"),
////                        resultSet.getString("Last_Updated_By"),
////                        resultSet.getInt("Customer_ID"),
////                        resultSet.getInt("User_ID"),
////                        resultSet.getInt("Contact_ID")));
////            }
////
////            for (int i = 0; i < allAppointmentsByTypeMonth.size(); i++) {
////                allAppointmentsByTypeMonth.get(i);
////                count++;
////
////            }
////            return Integer.toString(count);
////        } catch (Exception e) {
////            throw new RuntimeException(e);
////        }
////    }

    /** A method that adds appointments to the SQL database. */
    public static void addAppointment(int Contact_ID, String Title, String Description, String Location, String Type, LocalDateTime Start, LocalDateTime End, int Customer_ID, int User_ID) {
        try {
            String sqlQuery = "INSERT INTO appointments(Contact_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID) VALUES(?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = JDBC_DAO.getConnection().prepareStatement(sqlQuery);
            preparedStatement.setInt(1, Contact_ID);
            preparedStatement.setString(2, Title);
            preparedStatement.setString(3, Description);
            preparedStatement.setString(4, Location);
            preparedStatement.setString(5, Type);
            preparedStatement.setTimestamp(6, Timestamp.valueOf(Start));
            preparedStatement.setTimestamp(7, Timestamp.valueOf(End));
            preparedStatement.setInt(8, Customer_ID);
            preparedStatement.setInt(9, User_ID);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * A method that updates an appointment in the SQL database.
     */
    public static void updateAppointment(int appointment_ID, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end,
                                         Timestamp create_Date, String create_By, Timestamp last_Update, String last_Updated_By, int customer_ID, int user_ID, int contact_ID) {
        try {
            String sqlQuery = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, " +
                    "last_Update = ?, last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
            PreparedStatement preparedStatement = JDBC_DAO.getConnection().prepareStatement(sqlQuery);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);
            preparedStatement.setString(3, location);
            preparedStatement.setString(4, type);
            preparedStatement.setTimestamp(5, Timestamp.valueOf(start));
            preparedStatement.setTimestamp(6, Timestamp.valueOf(end));
            preparedStatement.setTimestamp(7, last_Update);
            preparedStatement.setString(8, last_Updated_By);
            preparedStatement.setInt(9, customer_ID);
            preparedStatement.setInt(10, user_ID);
            preparedStatement.setInt(11, contact_ID);
            preparedStatement.setInt(12, appointment_ID);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** A method that deletes a selected appointment from the database. */
    public static void deleteFromDBByAppointmentID(int selectedAppointmentID) {

        try {
            String sqlQueryAppointments = "DELETE FROM appointments WHERE Appointment_ID = ?";
            PreparedStatement preparedStatement1 = JDBC_DAO.getConnection().prepareStatement(sqlQueryAppointments);
            preparedStatement1.setInt(1, selectedAppointmentID);
            preparedStatement1.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void deleteAppointmentFromDBByCustomerID(int selectedCustomerID){
        try {
            String sqlQueryAppointments = "DELETE FROM appointments WHERE Customer_ID = ?";
            PreparedStatement preparedStatement1 = JDBC_DAO.getConnection().prepareStatement(sqlQueryAppointments);
            preparedStatement1.setInt(1, selectedCustomerID);
            preparedStatement1.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


//    public static Appointment searchAppointmentByCustomerID(int customerID) throws SQLException {
//        ObservableList<String> filteredAppointmentList = FXCollections.observableArrayList();
//        ObservableList<Appointment> allAppointments = getAllAppointments();
//        Appointment selectedAppointment = Appointment_DAO.searchAppointmentByCustomerID(customerID);
//
//        for (int i = 0; i < allAppointments.size(); i++) {
//            Appointment searchedAppointment = allAppointments.get(i);
//
//            if (searchedAppointment.getCustomer_ID() == customerID) {
//                filteredAppointmentList.add(selectedAppointment);
//            }
//        }
//        return null;
//    }
//    public static ObservableList<Appointment> getAllAppointmentsByContact(Contact selectedContact) throws SQLException {
//        allContactAppointments.clear();
//        String sqlQuery = "SELECT * FROM client_schedule.appointments WHERE Contact_ID = 'contact_Id'";
//        PreparedStatement preparedStatement = JDBC_DAO.getConnection().prepareStatement(sqlQuery);
//        ResultSet resultSet = preparedStatement.executeQuery();
//
//        while (resultSet.next()) {
//            allContactAppointments.add(new Appointment(
//                    resultSet.getInt("Appointment_ID"),
//                    resultSet.getString("Title"),
//                    resultSet.getString("Description"),
//                    resultSet.getString("Location"),
//                    resultSet.getString("Type"),
//                    resultSet.getTimestamp("Start").toLocalDateTime(),
//                    resultSet.getTimestamp("End").toLocalDateTime(),
//                    resultSet.getString("Create_Date"),
//                    resultSet.getString("Created_By"),
//                    resultSet.getString("Last_Update"),
//                    resultSet.getString("Last_Updated_By"),
//                    resultSet.getInt("Customer_ID"),
//                    resultSet.getInt("User_ID"),
//                    resultSet.getInt("Contact_ID")));
//        }
//        return allContactAppointments;
//    }

    public static ObservableList<Appointment> getAllAppointmentsByCustomer(int customerID) throws SQLException {
        allCustomerAppointments.clear();
        String sqlQuery = "SELECT * FROM appointments WHERE Customer_ID = 'customer_Id'";
        PreparedStatement preparedStatement = JDBC_DAO.getConnection().prepareStatement(sqlQuery);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            allCustomerAppointments.add(new Appointment(
                    resultSet.getInt("Appointment_ID"),
                    resultSet.getString("Title"),
                    resultSet.getString("Description"),
                    resultSet.getString("Location"),
                    resultSet.getString("Type"),
                    resultSet.getTimestamp("Start").toLocalDateTime(),
                    resultSet.getTimestamp("End").toLocalDateTime(),
                    resultSet.getString("Create_Date"),
                    resultSet.getString("Created_By"),
                    resultSet.getString("Last_Update"),
                    resultSet.getString("Last_Updated_By"),
                    resultSet.getInt("Customer_ID"),
                    resultSet.getInt("User_ID"),
                    resultSet.getInt("Contact_ID")));
        }
        return allCustomerAppointments;
    }
    public static ObservableList<Appointment> lookupSpecificAppointment(int appointment_ID) throws SQLException {
        ObservableList<Appointment> specificAppointment = FXCollections.observableArrayList();
        ObservableList<Appointment> allAppointments = getAllAppointments();

        try {
            for (int i = 0; i < allAppointments.size(); i++) {
                Appointment searchedAppointment = allAppointments.get(i);

                if (searchedAppointment.getAppointment_ID() == appointment_ID) {
                    specificAppointment.add(searchedAppointment);
                }
            }
            return specificAppointment;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean appointmentExistsForCustomer(int customer_ID){
        boolean appointmentExists = false;
        for (int i = 0; i < allAppointments.size(); i++) {
            Appointment searchedAppointment = allAppointments.get(i);

            if (searchedAppointment.getCustomer_ID() == customer_ID) {
                appointmentExists = true;
            }
        }
        return appointmentExists;
    }
    public static ObservableList<Appointment> lookupAppointment(int contact_ID) throws SQLException {
        ObservableList<Appointment> filteredAppointmentList = FXCollections.observableArrayList();
        ObservableList<Appointment> allAppointments = getAllAppointments();

        try {
            for (int i = 0; i < allAppointments.size(); i++) {
                Appointment searchedAppointment = allAppointments.get(i);

                if (searchedAppointment.getContact_ID() == contact_ID) {
                    filteredAppointmentList.add(searchedAppointment);
                }
            }
            return filteredAppointmentList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ObservableList<Appointment> lookupLocation(String location) throws SQLException {
        ObservableList<Appointment> filteredAppointmentList = FXCollections.observableArrayList();
        ObservableList<Appointment> allAppointments = getAllAppointments();

        try {
            for (int i = 0; i < allAppointments.size(); i++) {
                Appointment searchedAppointment = allAppointments.get(i);

                if (searchedAppointment.getLocation().equals(location)) {
                    filteredAppointmentList.add(searchedAppointment);
                }
            }
            return filteredAppointmentList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ObservableList<String> getLocations() {
        ObservableList<String> filteredAppointmentListLocations = FXCollections.observableArrayList();

        for(int i = 0; i < allAppointments.size(); i++){
            Appointment searchedAppointment = allAppointments.get(i);

            String location = searchedAppointment.getLocation();
            if(!filteredAppointmentListLocations.contains(location)){
                filteredAppointmentListLocations.add(location);
            }
        }
        return filteredAppointmentListLocations;
    }


    /**
     * An observable list that obtains all appointments.
     */
    public static ObservableList<Appointment> getAllAppointments() throws SQLException {
        allAppointments.clear();
        String sqlQuery = "SELECT * FROM appointments";

        PreparedStatement preparedStatement = JDBC_DAO.getConnection().prepareStatement(sqlQuery);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            allAppointments.add(new Appointment(
                    resultSet.getInt("Appointment_ID"),
                    resultSet.getString("Title"),
                    resultSet.getString("Description"),
                    resultSet.getString("Location"),
                    resultSet.getString("Type"),
                    resultSet.getTimestamp("Start").toLocalDateTime(),
                    resultSet.getTimestamp("End").toLocalDateTime(),
                    resultSet.getString("Create_Date"),
                    resultSet.getString("Created_By"),
                    resultSet.getString("Last_Update"),
                    resultSet.getString("Last_Updated_By"),
                    resultSet.getInt("Customer_ID"),
                    resultSet.getInt("User_ID"),
                    resultSet.getInt("Contact_ID")));
        }
        return allAppointments;
    }
    public static ObservableList<Appointment> getAppointmentsTable() throws SQLException {
        allAppointments.clear();

        String sqlQuery = "SELECT a.*, c.*" +
                "FROM appointments a " +
                "JOIN contacts c ON a.Contact_ID = c.Contact_ID";

        PreparedStatement preparedStatement = JDBC_DAO.getConnection().prepareStatement(sqlQuery);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            allAppointments.add(new Appointment(
                    resultSet.getInt("Appointment_ID"),
                    resultSet.getString("Title"),
                    resultSet.getString("Description"),
                    resultSet.getString("Location"),
                    resultSet.getString("Contact_Name"),
                    resultSet.getString("Type"),
                    resultSet.getTimestamp("Start").toLocalDateTime(),
                    resultSet.getTimestamp("End").toLocalDateTime(),
                    resultSet.getInt("Customer_ID"),
                    resultSet.getInt("User_ID")));
        }
        return allAppointments;
    }
}
