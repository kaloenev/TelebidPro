package controller;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.EditProfileServlet;
import model.User;
import model.UserDao;
import org.junit.Before;
import org.junit.Test;

public class EditProfileServletTest {

    private EditProfileServlet servlet;
    private UserDao userDao;

    @Before
    public void setUp() {
        servlet = new EditProfileServlet();
        userDao = mock(UserDao.class);
        servlet.init();
    }

    @Test
    public void testDoGet_AuthenticatedUser() throws ServletException, IOException, SQLException {
        // Mocking objects
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        UserDao userDao = mock(UserDao.class);
        User user = new User("John", "Doe");

        // Setting up expectations
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("username")).thenReturn("testUser");
        when(userDao.selectUser("testUser")).thenReturn(user);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("editProfile.jsp")).thenReturn(dispatcher);
        when(userDao.updateUser(anyString(), anyString(), anyString())).thenReturn(true);

        servlet.doGet(request, response);

        // Verifying interactions
        verify(request).setAttribute("user", user);
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void testDoGet_UnauthenticatedUser() throws ServletException, IOException, SQLException {
        // Mocking objects
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        UserDao userDao = mock(UserDao.class);

        // Setting up expectations
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("username")).thenReturn(null);

        servlet.doGet(request, response);

        // Verifying interactions
        verify(response).sendRedirect("login.jsp");
    }

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
        when(request.getParameter("firstname")).thenReturn("Jane");
        when(request.getParameter("lastname")).thenReturn("Doe");
        when(userDao.updateUser(anyString(), anyString(), anyString())).thenReturn(true);

        servlet.doPost(request, response);

        // Verifying interactions
        verify(userDao).updateUser("Jane", "Doe", "testUser");
        verify(response).sendRedirect("editProfile.jsp");
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

        servlet.doPost(request, response);

        // Verifying interactions
        verify(response).sendRedirect("login.jsp");
        verify(userDao, never()).updateUser(anyString(), anyString(), anyString());
    }
}
