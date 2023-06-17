package DAO;

import cstewart.schedulingapplication.model.Appointment;
import cstewart.schedulingapplication.model.Country;
import cstewart.schedulingapplication.model.Customer;
import cstewart.schedulingapplication.model.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javax.xml.stream.Location;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Collectors;

/** A class used to identify and manipulate database fields related to geographical data. */
public class Geographical_DAO {

    private static final ObservableList<Division> allDivisions = FXCollections.observableArrayList();
    private static final ObservableList<Country> allCountries = FXCollections.observableArrayList();

    /** A method to obtain a list of all divisions ID's, divisions, and country ID's.
     @return a list of divisions. */
    public static ObservableList<Division> getAllDivisions() throws SQLException {
        if(allDivisions.isEmpty()) {
            try {
                String sqlQuery = "SELECT * FROM client_schedule.first_level_divisions";
                PreparedStatement preparedStatement = JDBC_DAO.getConnection().prepareStatement(sqlQuery);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    allDivisions.add(new Division(
                            resultSet.getInt("division_ID"),
                            resultSet.getString("division"),
                            resultSet.getInt("country_ID")));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                return allDivisions;
            }
        }
        return allDivisions;
    }

    /** A method to obtain a country based on a division ID.
     @param division_ID returns a country by division ID.
     @return a country based on division.
     */
    public static Country getCountryByDivision(int division_ID){
        try {
            String sqlQuery = "SELECT * FROM countries as c inner join first_Level_Divisions as d on c.country_ID = d.country_ID AND d.division_ID = ?";
            PreparedStatement statement = JDBC_DAO.getConnection().prepareStatement(sqlQuery);
            statement.setInt(1, division_ID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
               Country c = new Country(
                        resultSet.getInt("country_ID"),
                        resultSet.getString("country"));
               return c;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  null;
    }

    /** Lambda Expression - A method to get all states/divisions by the country ID.
     @param country_ID list divisions by country ID.
     @return a list of state/provinces by country.
     */
    public static ObservableList<Division> getAllDivisionsByCountry(int country_ID) throws SQLException {
        ObservableList<Division>  list = getAllDivisions().stream()
                .filter(d -> d.getCountry_ID() == country_ID)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        return list;
    }

    /** A method to get a list of states/provinces (divisions).
     @return a list of states/provinces.
     */
    public static ObservableList<String> getDivisions() throws SQLException {
        ObservableList<String> filteredDivisionList = FXCollections.observableArrayList();
        ObservableList<Division> allDivisions = getAllDivisions();
        try {
            for (int i = 0; i < allDivisions.size(); i++) {
                Division searchedDivision = allDivisions.get(i);

                String division = searchedDivision.getDivision();
                if (!filteredDivisionList.contains(division)) {
                    filteredDivisionList.add(division);
                }
            }
            return filteredDivisionList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /** A method to query the SQL database and return division data by ID.
     @param division_ID returns division data by ID.
     @return a state/province based on a division ID.
     */
    public static Division getDivision(int division_ID){
        try {
            String sqlQuery = "SELECT * FROM first_Level_Divisions WHERE division_ID = ?";
            PreparedStatement statement = JDBC_DAO.getConnection().prepareStatement(sqlQuery);
            statement.setInt(1, division_ID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                Division c = new Division(
                        division_ID,
                        resultSet.getString("division"),
                        resultSet.getInt("country_ID"));
                return c;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  null;
    }

    /** A method to query the SQL database and return a list of all countries.
     @return a list of all countries.
     */
    public static ObservableList<Country> getAllCountries() throws SQLException{
        try {
            String sqlQuery = "SELECT * FROM client_schedule.countries";
            PreparedStatement statement = JDBC_DAO.getConnection().prepareStatement(sqlQuery);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                allCountries.add(new Country(
                        resultSet.getInt("country_ID"),
                        resultSet.getString("country")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  allCountries;
    }
}
