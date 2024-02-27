package controller;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

import model.UserDao;

@WebServlet("/setNewPass")
public class NewPasswordServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UserDao userDao;

    @Override
    public void init() {
        userDao = new UserDao();
    }


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String verificationLink = request.getParameter("verificationLink");
        String newPassword = request.getParameter("newPassword");

        boolean success = false;
        try {
            success = userDao.setNewPassword(verificationLink, newPassword);
        } catch (SQLException e) {
            response.sendRedirect("password_changed_error.jsp");
        }

        if (success) {
            // Success
            response.sendRedirect("password_changed_success.jsp");
        } else {
            // Error
            response.sendRedirect("password_changed_error.jsp");
        }
    }
}