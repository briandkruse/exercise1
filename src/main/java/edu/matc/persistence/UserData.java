package edu.matc.persistence;

import edu.matc.entity.User;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.*;

/**
 * Access users in the user table.
 *
 * @author pwaite and bkruse
 */
public class UserData {

    private final Logger logger = Logger.getLogger(this.getClass());

    /**
     * creates sql if user selects Display all user
     *
     * @return sql select statment
     */
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users";
        return runQuery(sql);
    }

    /**
     * creates sql if user searches last name
     *
     * @param searchTerm the last name entered by the user
     * @return sql select statment
     */
    public List<User> searchByLastName(String searchTerm) {
        String sql = "SELECT * FROM users WHERE last_name = '" + searchTerm + "'";
        return runQuery(sql);
    }

    /**
     * Runs the query based on constructed sql select statement
     *
     * @param sql the constructed sql select statement
     * @return the list of users
     */
    public List<User> runQuery(String sql) {
        List<User> users = new ArrayList<User>();
        Database database = Database.getInstance();
        Connection connection = null;

        try {
            database.connect();
            connection = database.getConnection();
            Statement selectStatement = connection.createStatement();
            ResultSet results = selectStatement.executeQuery(sql);
            createUserList(results, users);
            database.disconnect();
        } catch (SQLException e) {
            logger.info("SearchUser.getAllUsers()...SQL Exception: ", e);
        } catch (Exception e) {
            logger.info("SearchUser.getAllUsers()...Exception: ", e);
        }
        return users;
    }

    /**
     * Populates the user list based on query results
     *
     * @param results the query results
     * @param users   the list of users to be displayed
     * @throws SQLException
     */
    private void createUserList(ResultSet results, List<User> users) throws SQLException {
        while (results.next()) {
            User employee = createUserFromResults(results);
            users.add(employee);
        }
    }

    /**
     * creates users based on results from the database
     *
     * @param results the data results from the database
     * @return the user object created
     * @throws SQLException
     */
    private User createUserFromResults(ResultSet results) throws SQLException {
        User user = new User();
        user.setLastName(results.getString("last_name"));
        user.setFirstName(results.getString("first_name"));
        user.setUserid(results.getString("id"));
        user.setDateOfBirth(convertDate(results));
        return user;
    }

    /**
     * Converts the date string to an object for calculation
     *
     * @param results the users date of birth from the database
     * @return dateTime object with date of birth
     * @throws SQLException
     */
    private LocalDate convertDate(ResultSet results) throws SQLException {
        String rawDate = results.getString("date_of_birth");
        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-mm-dd");
        LocalDate dateTime = dtf.parseLocalDate(rawDate);
        return dateTime;


    }

}