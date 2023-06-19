package DAO;

import cstewart.schedulingapplication.model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.*;
import java.lang.String;
import java.time.temporal.ChronoUnit;

/** A class used to identify and manipulate database fields for appointments. */
public class Appointment_DAO {

    private static final ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    private static final ObservableList<Appointment> allAppointmentViews = FXCollections.observableArrayList();
    private static final ObservableList<Appointment> allCustomerAppointments = FXCollections.observableArrayList();


    /** A method utilized to identify if an appointment exists within 15 minutes of login and notify user, regardless
     of outcome.
     @param user_ID  lookup appointment by ID.
     @return filtered list of appointments within minutes of login*/
    public static ObservableList<Appointment> appointmentWithinMinutesOfLogin(int user_ID) throws SQLException {
        ObservableList<Appointment> filteredApptWithinMinutesOfLogin = FXCollections.observableArrayList();

        String sqlQuery = "SELECT * FROM appointments WHERE User_ID = ?";

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

    /** A method to determine if there are any overlapping appointments.
     @param tempDate along with tempStart, tempEnd, and tempCustomer_ID to identify overlapping appointments.
     @return a boolean value stating whether an overlap exists.
     */
    public static boolean isOverlappingAppointment(LocalDate tempDate, LocalTime tempStart, LocalTime tempEnd, int tempCustomer_ID, int tempAppointment_ID) {  // supposed to be customer
        boolean overlapExists = false;

        // Convert tempDate and tempTime arguments into a single LocalDateTime object.
        LocalDateTime startConverted = tempDate.atTime(tempStart);
        LocalDateTime endConverted = tempDate.atTime(tempEnd);

        // Convert Instant to a Timestamp which is used for a specific instant in time.
        Timestamp bookAppointmentStart = Timestamp.valueOf(startConverted);
        Timestamp bookAppointmentEnd = Timestamp.valueOf(endConverted);

        try {
            for (int i = 0; i < allAppointments.size(); i++) {
                Appointment searchedAppointment = allAppointments.get(i);
                if(searchedAppointment.getCustomer_ID()!=tempCustomer_ID || searchedAppointment.getAppointment_ID()==tempAppointment_ID){
                    continue;
                }
                Timestamp existingAppointmentStart = Timestamp.valueOf(searchedAppointment.getStart());
                Timestamp existingAppointmentEnd = Timestamp.valueOf(searchedAppointment.getEnd());

                if((bookAppointmentStart.after(existingAppointmentStart) && bookAppointmentStart.before(existingAppointmentEnd) ||
                        bookAppointmentStart.equals(existingAppointmentStart))
                        && (bookAppointmentEnd.before(existingAppointmentEnd) || bookAppointmentEnd.equals(existingAppointmentEnd)
                        || bookAppointmentEnd.after(existingAppointmentEnd))){
                    overlapExists = true;
                    System.out.println(bookAppointmentStart + " is after " + existingAppointmentStart + " AND " + bookAppointmentStart
                            + " is before " + existingAppointmentEnd + " AND " + bookAppointmentEnd + " is before " + existingAppointmentEnd +
                            " OR " + bookAppointmentEnd + " is equal to " + existingAppointmentEnd + " OR "
                            + bookAppointmentEnd + " is after " + existingAppointmentEnd + ", Overlap: " + overlapExists);
                }
                if((bookAppointmentStart.before(existingAppointmentStart) && bookAppointmentEnd.after(existingAppointmentStart) ||
                        bookAppointmentStart.equals(existingAppointmentStart))
                        && (bookAppointmentEnd.before(existingAppointmentEnd) || bookAppointmentEnd.equals(existingAppointmentEnd)
                        ||bookAppointmentEnd.after(existingAppointmentEnd))){
                    overlapExists = true;
                    System.out.println(bookAppointmentStart + " is before " + existingAppointmentStart + " AND " + bookAppointmentEnd
                            + " is after " + existingAppointmentStart + " AND " + bookAppointmentEnd + " is before "
                            + existingAppointmentEnd + " OR " + bookAppointmentEnd + " is equal to " + existingAppointmentEnd + " OR "
                            + bookAppointmentEnd + " is after " + existingAppointmentEnd + ", Overlap: " + overlapExists);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return overlapExists;
    }

    /** A method that provides data for the Appointment view based upon user selection.
     @param viewDays return list of appointments based on "viewDays".
     @return a list of all appointments within a specified period of days.
     */
        public static ObservableList<Appointment> getView(int viewDays) throws SQLException {
        allAppointmentViews.clear();

        String sqlQuery = "SELECT a.*, c.* " +
                "FROM appointments a " +
                "JOIN contacts c ON a.Contact_ID = c.Contact_ID " +
                "WHERE date(Start) BETWEEN current_date() AND date_add(current_date(), interval " + viewDays + " day)"; // cause SQL Inject.  ? and then bind to variable passing.  Parameterized queries.
            PreparedStatement preparedStatement = JDBC_DAO.getConnection().prepareStatement(sqlQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                allAppointmentViews.add(new Appointment(
                        resultSet.getInt("Appointment_ID"),
                        resultSet.getString("Title"),
                        resultSet.getString("Description"),
                        resultSet.getString("Location"),
                        resultSet.getString("Type"),
                        resultSet.getTimestamp("Start").toLocalDateTime(),
                        resultSet.getTimestamp("End").toLocalDateTime(),
                        resultSet.getInt("Customer_ID"),
                        resultSet.getInt("User_ID"),
                        resultSet.getInt("Contact_ID"),
                        resultSet.getString("Contact_Name")));
        }
        return allAppointmentViews;
    }

    /** A method that obtains a list of "Types" captured in the database and returns that list to be utilized in a combo box.
     @return a filtered list of appointments by type.
     */
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

    /** A method that counts the number of appointments by the user selected "type" and "month".
     * @param month plus type, counting appointments by "type" and "month".
     * @return a count of appointments by "type" and "month."
     * */
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

    /** A method that adds appointments to the SQL database.
     @param Customer_ID plus additional parameters, adding a customer appointment.
     */
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

    /** A method that updates an appointment in the SQL database.
     @param appointment_ID update appointment information in the database.
     */
    public static void updateAppointment(int appointment_ID, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end,
                                          int customer_ID, int user_ID, int contact_ID) {
        try {
            String sqlQuery = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, " +
                    "Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
            PreparedStatement preparedStatement = JDBC_DAO.getConnection().prepareStatement(sqlQuery);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);
            preparedStatement.setString(3, location);
            preparedStatement.setString(4, type);
            preparedStatement.setTimestamp(5, Timestamp.valueOf(start));
            preparedStatement.setTimestamp(6, Timestamp.valueOf(end));
            preparedStatement.setInt(7, customer_ID);
            preparedStatement.setInt(8, user_ID);
            preparedStatement.setInt(9, contact_ID);
            preparedStatement.setInt(10, appointment_ID);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /** A method that deletes a selected appointment from the database.
     @param selectedAppointmentID delete appointment by ID.
     */
    public static void deleteFromDatabaseByAppointmentID(int selectedAppointmentID) {
        try {
            String sqlQueryAppointments = "DELETE FROM appointments WHERE Appointment_ID = ?";
            PreparedStatement preparedStatement1 = JDBC_DAO.getConnection().prepareStatement(sqlQueryAppointments);
            preparedStatement1.setInt(1, selectedAppointmentID);
            preparedStatement1.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /** A method that deletes a selected appointment from the database.
     @param selectedCustomerID delete appointment by ID.
     */
    public static void deleteFromDatabaseByCustomerID(int selectedCustomerID) {
        try {
            String sqlQueryAppointments = "DELETE FROM appointments WHERE Customer_ID = ?";
            PreparedStatement preparedStatement1 = JDBC_DAO.getConnection().prepareStatement(sqlQueryAppointments);
            preparedStatement1.setInt(1, selectedCustomerID);
            preparedStatement1.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** A method that returns a list of appointments by the contact ID.
     @param contact_ID return appointments by ID.
     @return a filtered list of appointments by the contact ID.
     */
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

    /** An observable list that obtains a list of all appointments.
     @return a list of all appointments. */
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
                    resultSet.getTimestamp("End").toLocalDateTime(), // performs a conversion with get Timestamp
                    resultSet.getInt("Customer_ID"),
                    resultSet.getInt("User_ID"),
                    resultSet.getInt("Contact_ID")));
        }
        return allAppointments;
    }

    /** A method that returns a joined list of appointments and contact information.
     @return a list of all appointments with contact information.
     */
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
                    resultSet.getString("Type"),
                    resultSet.getTimestamp("Start").toLocalDateTime(),
                    resultSet.getTimestamp("End").toLocalDateTime(),
                    resultSet.getInt("Customer_ID"),
                    resultSet.getInt("User_ID"),
                    resultSet.getInt("Contact_ID"),
                    resultSet.getString("Contact_Name")));
        }
        return allAppointments;
    }
}
