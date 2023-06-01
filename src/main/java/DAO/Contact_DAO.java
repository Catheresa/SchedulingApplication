package DAO;

import cstewart.schedulingapplication.model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Collectors;

public class Contact_DAO {

    private static final ObservableList<Contact> allContacts = FXCollections.observableArrayList();

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
    // Lambda expression
    public static Contact lookupContact(int contactID) throws SQLException {
        Contact contact = null;
        ObservableList<Contact> list = getAllContacts().stream().filter(contact1 -> contact1.getContact_ID() == contactID)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        if(list.size() > 0){
            contact = list.get(0);
        }

        return contact;
    }

}
