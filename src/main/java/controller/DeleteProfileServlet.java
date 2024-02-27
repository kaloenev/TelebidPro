package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

import model.UserDao;

@WebServlet("/deleteProfile")
public class DeleteProfileServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UserDao userDao;

    @Override
    public void init() {
        userDao = new UserDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("username") != null) {
            // User is authenticated
            String username = (String) session.getAttribute("username");
            // Delete user profile from the database
            try {
                userDao.deleteUser(username);
            } catch (SQLException e) {
                response.sendRedirect("Error.jsp");
            }
            // Invalidate session after deleting profile
            session.invalidate();
            response.sendRedirect("index.jsp"); // Redirect to home page after deletion
        } else {
            response.sendRedirect("login.jsp");
        }
    }
}
