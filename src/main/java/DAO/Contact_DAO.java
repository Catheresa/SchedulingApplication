package DAO;

import cstewart.schedulingapplication.model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Collectors;

/** A class used to identify and manipulate database fields for contacts. */
public class Contact_DAO {
    private static final ObservableList<Contact> allContacts = FXCollections.observableArrayList();

    /** An observable list that obtains a list of all contacts.
     @return a list of all contacts.
     */
    public static ObservableList<Contact> getAllContacts() throws SQLException {
        if(allContacts.isEmpty()) {
            String sqlQuery = "SELECT * FROM client_schedule.contacts";

            PreparedStatement preparedStatement = JDBC_DAO.getConnection().prepareStatement(sqlQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                allContacts.add(new Contact(
                        resultSet.getInt("contact_ID"),
                        resultSet.getString("contact_Name"),
                        resultSet.getString("email")));
            }
        }
        return allContacts;
    }
}
