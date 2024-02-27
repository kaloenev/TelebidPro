package controller;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.UserDao;

@WebServlet("/verify")
public class VerificationServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UserDao userDao;

    public void init() {
        userDao = new UserDao();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String verificationCode = request.getParameter("code");
        try {
            if (userDao.enableUser(verificationCode)) {
                // Verification successful
                response.sendRedirect("login.jsp");
            } else {
                // Verification failed
                response.sendRedirect("Error.jsp");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
