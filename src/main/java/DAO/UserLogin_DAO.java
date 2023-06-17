package DAO;

import cstewart.schedulingapplication.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Collectors;

/** A class used to identify and manipulate database fields for user and login information. */
public class UserLogin_DAO {
    private static final ObservableList<User> allUsers = FXCollections.observableArrayList();

    /** A method to obtain a list of all users data from the SQL database.
     @return a list of all users.
     */
    public static ObservableList<User> getAllUsers() throws SQLException {
        if(allUsers.isEmpty()) {
            String sqlQuery = "SELECT * FROM client_schedule.users";

            PreparedStatement preparedStatement = JDBC_DAO.getConnection().prepareStatement(sqlQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                allUsers.add(new User(
                        resultSet.getInt("user_ID"),
                        resultSet.getString("user_Name")));
            }
        }
        return allUsers;
    }

    /** A method that determines if valid credentials were entered to gain access to the program.
     @param userName along with password to validate login.
     @return a user.
     */
    public static User validUser(String userName, String pass) throws SQLException {
        String sqlQuery = "Select * FROM users WHERE BINARY User_Name = ? AND BINARY Password = ?";

        PreparedStatement ps = JDBC_DAO.getConnection().prepareStatement(sqlQuery);
        ps.setString(1, userName);
        ps.setString(2, pass);
        ResultSet rs = ps.executeQuery();
        User user = null;
        try {
            if (rs.next()) {
                user = new User(rs.getInt("user_ID"), rs.getString("user_Name"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }
}
