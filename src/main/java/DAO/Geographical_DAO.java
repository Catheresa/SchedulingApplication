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

public class Geographical_DAO {

    private static final ObservableList<Division> allDivisions = FXCollections.observableArrayList();
    private static final ObservableList<Country> allCountries = FXCollections.observableArrayList();

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
                //JDBC_DAO.closeConnection();
            }
        }
        return allDivisions;
    }
//    public static Division identifyCountryByDivision(int divisionID) throws SQLException {
//        ObservableList<Division> identifiedDivision = getAllDivisions();
//        int customerCountry = 1;
//
//        for (int i = 0; i < allDivisions.size(); i++) {
//            int identifiedCountry = allDivisions.get(i).getCountry_ID();
////            if (searchedCountry.getDivision_ID() == divisionID) {
////                int  customerDivision = searchedCustomer.getDivision_ID();
////                if(customerDivision >= 54 && customerDivision <= 72 ){
////                    customerCountry = 2;
////                }else if(customerDivision >=101 && customerDivision <= 104){
////                    customerCountry = 3;
////                }else{customerCountry = 1;}
////            }
//        } return customerCountry;
//    }
    //Lambda
//    public static ObservableList<Division> getDivisionsByCountry(int country_ID) throws SQLException {
//        //could do lambda here.
//        ObservableList<Division> filteredList = FXCollections.observableArrayList();
//        try {
//            for(Division div: getAllDivisions()){
//                if(div.getCountry_ID() == country_ID){
//                    filteredList.add(div);
//                }
//            }
//        } catch (SQLException e) {
////            throw new RuntimeException(e);
//            throw e;
//        }
//        return filteredList;


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

    public static ObservableList<Division> getAllDivisionsByCountry(int country_ID) throws SQLException {
        ObservableList<Division>  list = getAllDivisions().stream()
                .filter(d -> d.getCountry_ID() == country_ID)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        return list;
    }

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
    public static ObservableList<Division> lookupDivision(String division) throws SQLException {
        ObservableList<Division> filteredDivisionList = FXCollections.observableArrayList();
        ObservableList<Division> allDivisions = getAllDivisions();

        try {
            for (int i = 0; i < allDivisions.size(); i++) {
                Division searchedDivision= allDivisions .get(i);

                if (searchedDivision.getDivision().equals(division)) {
                    filteredDivisionList .add(searchedDivision);
                }
            }
            return filteredDivisionList ;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
