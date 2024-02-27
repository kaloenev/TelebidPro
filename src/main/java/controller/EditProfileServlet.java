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
import model.User;

@WebServlet("/editProfile")
public class EditProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDao userDao;

    @Override
    public void init() {
        userDao = new UserDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("username") != null) {
            // model.User is authenticated
            String username = (String) session.getAttribute("username");

            // Retrieve user's first and last name from the database
            User user = userDao.selectUser(username);

            // Set user object as an attribute in request scope
            request.setAttribute("user", user);

            try {
                // Forward to the JSP for editing
                request.getRequestDispatcher("editProfile.jsp").forward(request, response);
            } catch (ServletException | IOException e) {
                response.sendRedirect("Error.jsp");
            }

        } else {
            response.sendRedirect("login.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("username") != null) {
            // model.User is authenticated
            String username = (String) session.getAttribute("username");
            // Get user input from the form
            String firstname = request.getParameter("firstname");
            String lastname = request.getParameter("lastname");
            // Update user profile in the database
            // Assuming updateUserProfile is a method that updates the user's profile in the database
            try {
                userDao.updateUser(firstname, lastname, username);
            } catch (SQLException e) {
                response.sendRedirect("Error.jsp");
            }
            response.sendRedirect("editProfile.jsp");
        } else {
            response.sendRedirect("login.jsp");
        }
    }
}
