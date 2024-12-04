package com.example.voting;

import javax.servlet.ServletException;
// import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {
    private UserDao userDao = new JdbcUserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Create a new User object
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        // Save the user using UserDao
        userDao.saveUser(user);

        // Redirect to a success page
        response.sendRedirect("success.jsp");
    }
}
