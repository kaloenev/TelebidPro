package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

import model.UserDao;

@WebServlet("/setPassResetLink")
public class PassResetLinkServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UserDao userDao;

    @Override
    public void init() {
        userDao = new UserDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("username") != null) {
            // model.User is authenticated
            String username = (String) session.getAttribute("username");
            String verificationCode = UUID.randomUUID().toString();

            String email = userDao.setPassResetLink(username, verificationCode);
            EmailSender.sendVerificationEmail(email, verificationCode);

            if (email != null) {
                // Success
                response.sendRedirect("password_reset_success.jsp");
            } else {
                // Error
                response.sendRedirect("password_reset_error.jsp");
            }
        }
    }
}
