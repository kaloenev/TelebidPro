package model;

import java.sql.*;

public class UserDao {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/mydatabase";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "20010205KuR@";

    private static final String ENABLE_USER_BY_VERIFIC = "update users set isEnabled = ?, verification_link = ? " +
            "where verification_link = ?;";

    private static final String RESET_PASSWORD = "update users set password = ?, verification_link = ? " +
            "where verification_link = ?;";

    private static final String UPDATE_USER_BY_USERNAME = "update users set first_name = ?, last_name = ? " +
            "where username = ?;";

    private static final String PASS_RESET_LINK_BY_USERNAME = "update users set verification_link = ? " +
            "where username = ?;";

    private static final String DELETE_USERS_SQL = "delete from users where username = ?;";

    private static final String INSERT_USERS_SQL = "INSERT INTO users" +
            "  (id, first_name, last_name, username, password, email, isEnabled, verification_link) VALUES " +
            " (?, ?, ?, ?, ?, ?, ?, ?);";

    private static final String SELECT_USER_BY_USERNAME = "select username, first_name, last_name from users " +
            "where username =?";

    private static final String SELECT_EMAIL_BY_USERNAME = "select username, email from users " +
            "where username =?";

    private static final String SELECT_BY_VERIFICATION = "select username, verification_link from users " +
            "where verification_link = ?";

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public int registerUser(User user, String verificationLink) {
        int result = 0;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {

            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setString(4, user.getUsername());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setString(6, user.getEmail());
            preparedStatement.setBoolean(7, false);
            preparedStatement.setString(8, verificationLink);

            System.out.println(preparedStatement);
            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // process sql exception
            printSQLException(e);
        }
        return result;
    }

    public boolean enableUser(String verificationLink) throws SQLException {
        if (verificationHelper(verificationLink)) return false;
        boolean rowUpdated;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(ENABLE_USER_BY_VERIFIC)) {

            statement.setBoolean(1, true);
            statement.setString(2, null);
            statement.setString(3, verificationLink);

            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    private boolean verificationHelper(String verificationLink) {

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_VERIFICATION)) {
            preparedStatement.setString(1, verificationLink);
            ResultSet rs = preparedStatement.executeQuery();

            if (!rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return false;
    }

    public User selectUser(String username) {
        User user = null;

        try (Connection connection = getConnection();

             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_USERNAME)) {
            preparedStatement.setString(1, username);
            System.out.println(preparedStatement);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                user = new User(firstName, lastName);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return user;
    }

    public String setPassResetLink(String username, String verificationLink) {
        int result;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(PASS_RESET_LINK_BY_USERNAME)) {

            preparedStatement.setString(1, verificationLink);
            preparedStatement.setString(2, username);

            System.out.println(preparedStatement);
            result = preparedStatement.executeUpdate();

            if (result == 0) {
                return null;
            }

        } catch (SQLException e) {
            printSQLException(e);
        }
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EMAIL_BY_USERNAME)) {
            preparedStatement.setString(1, username);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();

            String email = null;
            if (rs.next()) {
                email = rs.getString("email");
            }
            return email;
        } catch (SQLException e) {
            printSQLException(e);
        }
        return null;
    }

    public boolean setNewPassword(String verificationLink, String newPassword) throws SQLException {
        if (verificationHelper(verificationLink)) return false;
        boolean rowUpdated;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(RESET_PASSWORD)) {

            statement.setString(1, newPassword);
            statement.setString(2, null);
            statement.setString(3, verificationLink);

            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    public boolean checkVerificationLink(String verificationLink) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_VERIFICATION)) {
            preparedStatement.setString(1, verificationLink);
            ResultSet rs = preparedStatement.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            printSQLException(e);
        }
        return false;
    }

    public boolean updateUser(String firstName, String lastName, String username) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER_BY_USERNAME)) {

            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, username);

            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    public boolean deleteUser(String username) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_USERS_SQL)) {

            statement.setString(1, username);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
