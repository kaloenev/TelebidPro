package controller;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.EmailSender;
import controller.UserServlet;
import model.User;
import model.UserDao;
import org.junit.Before;
import org.junit.Test;

public class UserServletTest {

    private UserServlet userServlet;
    private UserDao userDao;

    @Before
    public void setUp() {
        userServlet = new UserServlet();
        userDao = mock(UserDao.class);
        userServlet.init();
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("register.jsp")).thenReturn(requestDispatcher);

        userServlet.doGet(request, response);

        verify(request).getRequestDispatcher("register.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPost() throws ServletException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        mockStatic(EmailSender.class);

        when(request.getParameter("firstName")).thenReturn("John");
        when(request.getParameter("lastName")).thenReturn("Doe");
        when(request.getParameter("username")).thenReturn("johndoe");
        when(request.getParameter("password")).thenReturn("password");
        when(request.getParameter("email")).thenReturn("johndoe@example.com");

        // Mocking controller.EmailSender.sendVerificationEmail(email, verificationCode);
        doNothing().when(EmailSender.class);
        EmailSender.sendVerificationEmail(anyString(), anyString());

        // Mocking model.UserDao.registerUser(user, verificationCode);
        when(userDao.registerUser(any(User.class), anyString())).thenReturn(1);

        userServlet.doPost(request, response);
    }
}
