package model;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.User;
import model.UserDao;
import org.junit.Test;

public class UserDaoTest {

    @Test
    public void testRegisterUser() throws SQLException {
        final Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        UserDao userDao = new UserDao() {
            @Override
            protected Connection getConnection() {
                return connection;
            }
        };

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        User user = new User();
        int result = userDao.registerUser(user, "verificationLink");

        assertEquals(1, result);
    }

    @Test
    public void testEnableUser() throws SQLException {
        final Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);
        UserDao userDao = new UserDao() {
            @Override
            protected Connection getConnection() {
                return connection;
            }
        };

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = userDao.enableUser("verificationLink");

        assertTrue(result);
    }

    @Test
    public void testSetPassResetLink() throws SQLException {
        final Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);
        UserDao userDao = new UserDao() {
            @Override
            protected Connection getConnection() {
                return connection;
            }
        };

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("email")).thenReturn("test@example.com");

        String email = userDao.setPassResetLink("username", "verificationLink");

        assertEquals("test@example.com", email);
    }

    @Test
    public void testSetNewPassword() throws SQLException {
        final Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);
        UserDao userDao = new UserDao() {
            @Override
            protected Connection getConnection() {
                return connection;
            }
        };

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = userDao.setNewPassword("verificationLink", "newPassword");

        assertTrue(result);
    }

    @Test
    public void testCheckVerificationLink() throws SQLException {
        final Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);
        UserDao userDao = new UserDao() {
            @Override
            protected Connection getConnection() {
                return connection;
            }
        };

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);

        boolean result = userDao.checkVerificationLink("verificationLink");

        assertTrue(result);
    }

    @Test
    public void testUpdateUser() throws SQLException {
        final Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        UserDao userDao = new UserDao() {
            @Override
            protected Connection getConnection() {
                return connection;
            }
        };

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = userDao.updateUser("John", "Doe", "johndoe");

        assertTrue(result);
    }

    @Test
    public void testDeleteUser() throws SQLException {
        final Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        UserDao userDao = new UserDao() {
            @Override
            protected Connection getConnection() {
                return connection;
            }
        };

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = userDao.deleteUser("johndoe");

        assertTrue(result);
    }
}
