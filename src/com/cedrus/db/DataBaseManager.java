package com.cedrus.db;


import com.cedrus.logger.ApplicationLogger;
import com.cedrus.models.Customer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DataBaseManager {

    private static final String JDBC_STRING = "jdbc:mysql://";
    private static final String DEFAULT_PORT = "3306";
    private static final String DABASE_NAME = "mydb";
    private final static String databaseUrl = "127.0.0.1";
    private final static String username = "root";
    private final static String password = "55Molaadebisi555";
    private ApplicationLogger logger = new ApplicationLogger(this.getClass());
    private MySqlClient mySqlClient;
    private Connection connection;
    private Statement statement;

    public void connect() {
        String connectionString = createConnectionString(databaseUrl, DEFAULT_PORT, DABASE_NAME);
        mySqlClient = new MySqlClient(connectionString, username, password);
        try {
            connection = mySqlClient.getConnection();
            statement = connection.createStatement();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    public void close() {
        try {
            connection.close();
            statement.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    private String createConnectionString(String inURL, String port, String databaseName) {
        return JDBC_STRING + inURL + ":" + port + "/" + databaseName + "?autoReconnect=true";
    }

    public List<Customer> getCustomerList() {
        ResultSet resultSet = null;
        String query = "select * from cedrus_customer_data";
        List<Customer> customers = new ArrayList<>();
        try {
            if (connection != null) {
                resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    String id = resultSet.getString(resultSet.findColumn("customer_id"));
                    String firstName = resultSet.getString(resultSet.findColumn("firstname"));
                    String lastName = resultSet.getString(resultSet.findColumn("lastname"));
                    String birthday = resultSet.getString(resultSet.findColumn("birthday"));
                    String address = resultSet.getString(resultSet.findColumn("address"));
                    String gender = resultSet.getString(resultSet.findColumn("gender"));
                    String phone = resultSet.getString(resultSet.findColumn("phone"));
                    String registrationDate = resultSet.getString(resultSet.findColumn("registration_date"));
                    String doctor = resultSet.getString(resultSet.findColumn("doctor"));
                    String direction = resultSet.getString(resultSet.findColumn("direction"));

                    Customer customer = new Customer();
                    customer.setId(Integer.parseInt(id));
                    customer.setFirstName(firstName);
                    customer.setLastName(lastName);
                    customer.setAddress(address);
                    customer.setBirthday(birthday);
                    customer.setGender(gender);
                    customer.setPhone(phone);
                    customer.setRegistration_date(registrationDate);
                    customer.setDoctor(doctor);
                    customer.setDirection(direction);


                    customers.add(customer);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        }
        return customers;
    }

    public boolean addCustomer(Customer customer) {

        String query = "INSERT INTO `mydb`.`cedrus_customer_data` (`firstname`, `lastname`, `address`, `birthday`, `gender`, `phone`, `registration_date`, `doctor`, `direction`) ";

        query += "VALUES "
                + "('" + customer.getFirstName() + "',"
                + "'" + customer.getLastName() + "',"
                + "'" + customer.getAddress() + "',"
                + "'" + customer.getBirthday() + "',"
                + "'" + customer.getGender() + "',"
                + "'" + customer.getPhone() + "',"
                + "'" + customer.getRegistration_date() + "',"
                + "'" + customer.getDoctor() + "',"
                + "'" + customer.getDirection() + "')";

        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }

        return true;
    }
}
