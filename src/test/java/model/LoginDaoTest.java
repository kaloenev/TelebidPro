package model;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import controller.LoginBean;
import model.LoginDao;
import org.junit.Test;

public class LoginDaoTest {

    @Test
    public void testValidate_SuccessfulLogin() throws SQLException, ClassNotFoundException {
        // Mocking objects
        final Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);
        LoginBean loginBean = mock(LoginBean.class);

        // Setting up expectations
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getBoolean("isEnabled")).thenReturn(true);
        when(loginBean.getPassword()).thenReturn("");

        // Testing the method
        LoginDao loginDao = new LoginDao() {
            @Override
            protected Connection getConnection() {
                return connection;
            }
        };
        boolean result = loginDao.validate(loginBean);

        // Verifying the result
        assertTrue(result);
    }

    @Test
    public void testValidate_IncorrectLogin() throws SQLException, ClassNotFoundException {
        // Mocking objects
        final Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);
        LoginBean loginBean = mock(LoginBean.class);
        LoginDao loginDao = new LoginDao() {
            @Override
            protected Connection getConnection() {
                return connection;
            }
        };

        // Setting up expectations
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);
        when(loginBean.getPassword()).thenReturn("");

        // Testing the method
        boolean result = loginDao.validate(loginBean);

        // Verifying the result
        assertFalse(result);
    }
}
