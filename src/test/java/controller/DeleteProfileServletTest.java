package controller;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.DeleteProfileServlet;
import model.UserDao;
import org.junit.Test;

public class DeleteProfileServletTest {

    @Test
    public void testDoPost_AuthenticatedUser() throws ServletException, IOException, SQLException {
        // Mocking objects
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        UserDao userDao = mock(UserDao.class);

        // Setting up expectations
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("username")).thenReturn("testUser");
        when(userDao.deleteUser(anyString())).thenReturn(true);

        // Testing the method
        DeleteProfileServlet servlet = new DeleteProfileServlet();
        servlet.init();
        servlet.doPost(request, response);

        // Verifying interactions
        verify(userDao).deleteUser("testUser");
        verify(session).invalidate();
        verify(response).sendRedirect("index.jsp");
    }

    @Test
    public void testDoPost_UnauthenticatedUser() throws ServletException, IOException, SQLException {
        // Mocking objects
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        UserDao userDao = mock(UserDao.class);

        // Setting up expectations
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("username")).thenReturn(null);

        // Testing the method
        DeleteProfileServlet servlet = new DeleteProfileServlet();
        servlet.init();
        servlet.doPost(request, response);

        // Verifying interactions
        verify(userDao, never()).deleteUser(anyString());
        verify(session, never()).invalidate();
        verify(response).sendRedirect("login.jsp");
    }
}
