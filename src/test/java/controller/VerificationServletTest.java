package controller;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.VerificationServlet;
import model.UserDao;
import org.junit.Before;
import org.junit.Test;

public class VerificationServletTest {

    private VerificationServlet verificationServlet;
    private UserDao userDao;

    @Before
    public void setUp() {
        verificationServlet = new VerificationServlet();
        userDao = mock(UserDao.class);
        verificationServlet.init();
    }

    @Test
    public void testDoGet_VerificationSuccess() throws ServletException, IOException, SQLException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("code")).thenReturn("verificationCode");
        when(userDao.enableUser("verificationCode")).thenReturn(true);

        verificationServlet.doGet(request, response);

        verify(response).sendRedirect("login.jsp");
    }

    @Test
    public void testDoGet_VerificationFailure() throws ServletException, IOException, SQLException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("code")).thenReturn("verificationCode");
        when(userDao.enableUser("verificationCode")).thenReturn(false);

        verificationServlet.doGet(request, response);

        verify(response).sendRedirect("Error.jsp");
    }
}