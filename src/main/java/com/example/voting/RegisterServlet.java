package com.example.voting;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Create a new User object
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        // Implement registration logic using UserService
        UserService userService = new UserService(new JdbcUserDao());
        userService.registerUser(user);

        // Redirect to a success page or display a success message
        response.sendRedirect("success.jsp");
    }
}
