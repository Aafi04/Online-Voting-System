package com.example.voting;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Implement login logic using UserService
        UserService userService = new UserService(new JdbcUserDao());
        User user = userService.loginUser(username, password);

        // Handle login success or failure
        if (user != null) {
            // Redirect to a welcome page or other appropriate page
            response.sendRedirect("welcome.jsp");
        } else {
            // Redirect to an error page or display an error message
            response.sendRedirect("error.jsp");
        }
    }
}
