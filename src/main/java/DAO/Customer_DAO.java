package DAO;

import cstewart.schedulingapplication.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;



public class Customer_DAO {

    private static final ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private static final ObservableList<Customer> allCustomersDivisionsCountries = FXCollections.observableArrayList();

    /**
     * An observable list that obtains all customers.
     */
    public static ObservableList<Customer> getAllCustomersDivisionsCountries() throws SQLException {
        allCustomersDivisionsCountries.clear();
        String sqlQuery = "SELECT c.*, f.*, t.* " +
                "FROM customers c " +
                "JOIN first_level_divisions f ON c.Division_ID = f.Division_ID " +
                "JOIN countries t ON f.Country_ID = t.Country_ID ";
        PreparedStatement preparedStatement = JDBC_DAO.getConnection().prepareStatement(sqlQuery);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            allCustomersDivisionsCountries.add(new Customer(
                    resultSet.getInt("customer_ID"),
                    resultSet.getString("customer_Name"),
                    resultSet.getInt("country_ID"),
                    resultSet.getString("country"),
                    resultSet.getString("address"),
                    resultSet.getInt("division_ID"),
                    resultSet.getString("division"),
                    resultSet.getString("postal_Code"),
                    resultSet.getString("phone")));
        }
        return allCustomersDivisionsCountries;
    }

    /** A method that adds customers to the database.*/
    public static void addCustomer(String customer_Name, String address, String postal_Code,String phone, Timestamp create_Date,
                                    String created_By, Timestamp last_Update, String last_Updated_By, int division_ID) throws SQLException {
        try {
            String sqlQuery = "INSERT INTO client_schedule.customers(customer_Name, address, postal_Code, phone, create_Date," +
                    "                                                   created_By, last_Update, last_Updated_By, division_ID) VALUES(?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = JDBC_DAO.getConnection().prepareStatement(sqlQuery);
            preparedStatement.setString(1, customer_Name);
            preparedStatement.setString(2, address);
            preparedStatement.setString(3, postal_Code);
            preparedStatement.setString(4, phone);
            preparedStatement.setTimestamp(5, create_Date);
            preparedStatement.setString(6, created_By);
            preparedStatement.setTimestamp(7, last_Update);
            preparedStatement.setString(8, last_Updated_By);
            preparedStatement.setInt(9, division_ID);
            int rowAffected = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateCustomer(String customer_Name, String address, String postal_Code,String phone, Timestamp create_Date,
                                      String created_By, Timestamp last_Update, String last_Updated_By, int division_ID, int tempCustomerID ){
        try {

            String sqlQuery = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, create_Date = ?, " +
                              "created_By = ?, last_Update = ?, last_Updated_By = ?, Division_ID = ? where Customer_ID =" + tempCustomerID;
            PreparedStatement preparedStatement = JDBC_DAO.getConnection().prepareStatement(sqlQuery);
            preparedStatement.setString(1, customer_Name);
            preparedStatement.setString(2, address);
            preparedStatement.setString(3, postal_Code);
            preparedStatement.setString(4, phone);
            preparedStatement.setTimestamp(5, create_Date);
            preparedStatement.setString(6, created_By);
            preparedStatement.setTimestamp(7, last_Update);
            preparedStatement.setString(8, last_Updated_By);
            preparedStatement.setInt(9, division_ID);

            int rowAffected = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * A method to iterate through a customer list and grab all customers meeting ID criteria provided.
     *
     * @param customerID look up customer by ID.
     */
    public static Customer lookupCustomer(int customerID) throws SQLException {
        ObservableList<Customer> allCustomers = getAllCustomers();

        for (int i = 0; i < allCustomers.size(); i++) {
            Customer searchedCustomer = allCustomers.get(i);

            if (searchedCustomer.getCustomer_ID() == customerID) {
                return searchedCustomer;
            }
        }
        return null;
    }

    /**
     * A method to iterate through a customer list and grab all customer meeting Name criteria provided.
     *
     * @param partialName look up customer by name.
     */
    public static ObservableList<Customer> lookupCustomer(String partialName) throws SQLException {
        ObservableList<Customer> filteredCustomerList = FXCollections.observableArrayList();
        ObservableList<Customer> allCustomers = getAllCustomers();

        for (Customer searchedCustomer : allCustomers) {
            if (searchedCustomer.getCustomer_Name().toLowerCase().contains(partialName.toLowerCase())) {
                filteredCustomerList.add(searchedCustomer);
            }
        }
        return filteredCustomerList;
    }

    public static int identifyCustomerCountry(int customerID) throws SQLException {
        ObservableList<Customer> allCustomers = getAllCustomers();
        int customerCountry = 1;

        for (int i = 0; i < allCustomers.size(); i++) {
            Customer searchedCustomer = allCustomers.get(i);
            if (searchedCustomer.getCustomer_ID() == customerID) {
                int  customerDivision = searchedCustomer.getDivision_ID();
                if(customerDivision >= 54 && customerDivision <= 72 ){
                    customerCountry = 2;
                }else if(customerDivision >=101 && customerDivision <= 104){
                    customerCountry = 3;
                }else{customerCountry = 1;}
            }
        } return customerCountry;
    }
    public static ObservableList<Customer> getCustomerByDivision(String division) throws SQLException {
        allCustomers.clear();
        String sqlQuery = "SELECT * FROM customers " +
                "JOIN first_level_divisions ON first_level_divisions.Division_ID = customers.Division_ID " +
                "WHERE Division = ?";
        PreparedStatement preparedStatement = JDBC_DAO.getConnection().prepareStatement(sqlQuery);
        preparedStatement.setString(1, division);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            allCustomers.add(new Customer(
                    resultSet.getInt("Customer_ID"),
                    resultSet.getString("Customer_Name"),
                    resultSet.getString("Address"),
                    resultSet.getString("Postal_Code"),
                    resultSet.getString("Phone")));
        }
        return allCustomers;
    }

    /** A method that deletes a customer from the database and any associated appointments.*/
    public static void deleteCustomerFromDB(int selectedCustomerID) {
        try {
            String sqlQueryCustomer = "DELETE FROM customers WHERE Customer_ID = ?";
            PreparedStatement preparedStatement2 = JDBC_DAO.getConnection().prepareStatement(sqlQueryCustomer);
            preparedStatement2.setInt(1, selectedCustomerID);
            int rowsAffected = preparedStatement2.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * An observable list that obtains all customers.
     */
    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        allCustomers.clear();
        String sqlQuery = "SELECT * FROM customers";
        PreparedStatement preparedStatement = JDBC_DAO.getConnection().prepareStatement(sqlQuery);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            allCustomers.add(new Customer(
                    resultSet.getInt("customer_ID"),
                    resultSet.getString("customer_Name"),
                    resultSet.getString("address"),
                    resultSet.getString("postal_Code"),
                    resultSet.getString("phone"),
                    resultSet.getString("create_Date"),
                    resultSet.getString("created_By"),
                    resultSet.getString("last_Update"),
                    resultSet.getString("last_Updated_By"),
                    resultSet.getInt("division_ID")));
        }
        return allCustomers;
    }


}
