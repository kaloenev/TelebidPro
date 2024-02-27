package controller;

import static org.mockito.Mockito.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.NewPasswordServlet;
import model.UserDao;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;

public class NewPasswordServletTest {

    @Test
    public void testDoPost_PasswordChangeSuccess() throws ServletException, IOException, SQLException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        UserDao userDao = mock(UserDao.class);

        when(request.getParameter("verificationLink")).thenReturn("verificationLink");
        when(request.getParameter("newPassword")).thenReturn("newPassword");
        when(userDao.setNewPassword("verificationLink", "newPassword")).thenReturn(true);

        NewPasswordServlet servlet = new NewPasswordServlet();
        servlet.init();
        servlet.doPost(request, response);

        verify(response).sendRedirect("password_changed_success.jsp");
    }

    @Test
    public void testDoPost_PasswordChangeError() throws ServletException, IOException, SQLException, IOException, SQLException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        UserDao userDao = mock(UserDao.class);

        when(request.getParameter("verificationLink")).thenReturn("verificationLink");
        when(request.getParameter("newPassword")).thenReturn("newPassword");
        when(userDao.setNewPassword("verificationLink", "newPassword")).thenReturn(false);

        NewPasswordServlet servlet = new NewPasswordServlet();
        servlet.init();
        servlet.doPost(request, response);

        verify(response).sendRedirect("password_changed_error.jsp");
    }
}
