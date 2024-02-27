package controller;

import java.io.IOException;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.LoginDao;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private LoginDao loginDao;

    @Override
    public void init() {
        loginDao = new LoginDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("login.jsp");
            requestDispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            response.sendRedirect("Error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String captchaInput = request.getParameter("captcha");

        // Retrieve the stored CAPTCHA from session
        HttpSession session = request.getSession();
        String storedCaptcha = (String) session.getAttribute("captcha");

        // Validate CAPTCHA
        if (captchaInput == null || !captchaInput.equals(storedCaptcha)) {
            // CAPTCHA incorrect
            HttpSession userSession = request.getSession();
            userSession.setAttribute("user", username);
            userSession.setAttribute("captchaError", "Incorrect CAPTCHA");
            response.sendRedirect("login.jsp");
            return; // Exit method
        }

        // CAPTCHA correct, continue with login validation
        LoginBean loginBean = new LoginBean();
        loginBean.setUsername(username);
        loginBean.setPassword(password);

        try {
            if (loginDao.validate(loginBean)) {
                // Successful login
                HttpSession userSession = request.getSession();
                userSession.setAttribute("username", username);
                response.sendRedirect("loginsuccess.jsp");
            } else {
                // Invalid login
                HttpSession userSession = request.getSession();
                userSession.setAttribute("user", username);
                response.sendRedirect("login.jsp");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
