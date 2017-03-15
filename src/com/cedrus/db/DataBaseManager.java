package com.cedrus.db;


import com.cedrus.logger.ApplicationLogger;
import com.cedrus.models.Customer;
import com.cedrus.models.Examination;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    private String createConnectionString(String inURL, String port, String databaseName) {
        return JDBC_STRING + inURL + ":" + port + "/" + databaseName + "?autoReconnect=true";
    }

    public void close() {
        try {
            connection.close();
            statement.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    public Integer getAttachmentId(Integer recordId) {
        ResultSet resultSet = null;
        String query = "select * from customer_attachment where record_id=" + recordId;
        Integer attachmentId = 0;
        try {
            if (connection != null) {
                resultSet = statement.executeQuery(query);
                if (resultSet.next()) {
                    attachmentId = resultSet.getInt(resultSet.findColumn("attachment_id"));
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
        return attachmentId;
    }

    public List<Examination> getExaminations(String customerId) {
        ResultSet resultSet = null;
        String query = "select * from customer_record where customer_id=" + customerId;
        List<Examination> records = new ArrayList<>();
        try {
            if (connection != null) {
                resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    Integer recordId = resultSet.getInt(resultSet.findColumn("record_id"));
                    String datetime = resultSet.getString(resultSet.findColumn("record_datetime"));
                    String doctor = resultSet.getString(resultSet.findColumn("doctor"));
                    String summary = resultSet.getString(resultSet.findColumn("summary_report"));
                    Examination record = new Examination();
                    record.setDate(datetime);
                    record.setDoctor(doctor);
                    record.setSummary(summary);
                    //record.setAttachmentId(attachId);
                    record.setRecordId(recordId);
                    records.add(record);
                }

                if (!records.isEmpty()) {
                    records.forEach(record -> {
                        record.setAttachmentId(getAttachmentId(record.getRecordId()));
                    });
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
        return records;
    }

    public List<Customer> getCustomerList() {
        ResultSet resultSet = null;
        String query = "select * from customer_profile";
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
                    String nextExaminationDateTime = resultSet.getString(resultSet.findColumn("next_examination_datetime"));

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
                    customer.setNextExaminationDateTime(nextExaminationDateTime);

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

    public boolean deleteCustomer(Customer customer) {
        String query = "DELETE FROM `mydb`.`customer_profile` WHERE `customer_id`=" + customer.getId() + ";";
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
        return true;

    }

    public boolean updateCustomer(Customer customer) {
        String query = "UPDATE `mydb`.`customer_profile` "
                + " SET `firstname`='" + customer.getFirstName() + "', "
                + "`lastname`='" + customer.getLastName() + "', "
                + "`address`='" + customer.getAddress() + "', "
                + "`phone`='" + customer.getPhone() + "', "
                + "`gender`='" + customer.getGender() + "', "
                + "`doctor`='" + customer.getDoctor() + "', "
                + "`direction`='" + customer.getDirection() + "'"
                + " WHERE `customer_id`=" + customer.getId() + ";";
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean addCustomer(Customer customer) {

        String query = "INSERT INTO `mydb`.`customer_profile` (`firstname`, `lastname`, `address`, `birthday`, `gender`, `phone`, `registration_date`, `doctor`, `direction`) ";

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

    public boolean addExaminationRecord(Examination examinationData) {
        String query = "INSERT INTO `mydb`.`customer_record` (`customer_id`, `record_datetime`, `doctor`, `summary_report`) ";
        query += "VALUES "
                + "('" + examinationData.getCustomerId() + "',"
                + "'" + examinationData.getDate() + "',"
                + "'" + examinationData.getDoctor() + "',"
                + "'" + examinationData.getSummary() + "')";

        String updQuery = "UPDATE `mydb`.`customer_profile` "
                + " SET `next_examination_datetime`='" + examinationData.getNextExaminationDateTime() + "'"
                + " WHERE `customer_id`=" + examinationData.getCustomerId() + ";";

        try {
            statement = connection.createStatement();
            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

            ResultSet rs = statement.getGeneratedKeys();
            Integer recordId = 0;
            if (rs.next()) {
                recordId = rs.getInt(1);
            }
            statement.executeUpdate(updQuery);

            if (examinationData.getAttachments() != null && !examinationData.getAttachments().isEmpty()) {
                attachFile(examinationData.getAttachments().get(0), examinationData.getCustomerId(), recordId);
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean attachFile(File fileName, Integer customerId, Integer recordId) {
        try {
            String sql = "INSERT INTO mydb.customer_attachment (customer_id, record_id, filename, attached_file) values (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, customerId);
            statement.setInt(2, recordId);
            statement.setString(3, fileName.getName());
            InputStream inputStream = new FileInputStream(fileName);

            statement.setBlob(4, inputStream);

            int row = statement.executeUpdate();
            if (row > 0) {
                return true;
            }
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public String getAttachmentFile(Integer attachmentId) {
        final int BUFFER_SIZE = 4096;
        String fileName = null;
        try {
            String sql = "SELECT * FROM customer_attachment WHERE attachment_id=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, attachmentId);

            ResultSet result = statement.executeQuery();
            if (result.next()) {
                Blob blob = result.getBlob("attached_file");
                fileName = result.getString("filename");
                InputStream inputStream = blob.getBinaryStream();
                OutputStream outputStream = new FileOutputStream(System.getProperty("user.dir") + "/temp/" + fileName);

                int bytesRead = -1;
                byte[] buffer = new byte[BUFFER_SIZE];
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                inputStream.close();
                outputStream.close();
                System.out.println("File saved");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return fileName;
    }

}
