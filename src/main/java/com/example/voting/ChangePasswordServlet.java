package com.example.voting;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChangePasswordServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.html");
            return;
        }

        String username = (String) session.getAttribute("username");
        String currentPassword = request.getParameter("current_password");
        String newPassword = request.getParameter("new_password");

        // Database connection parameters
        String jdbcURL = "jdbc:mysql://localhost:3306/online_voting_system";
        String dbUser = "root";
        String dbPassword = "root";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection
            connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);

            // Verify current password
            String verifyPasswordSql = "SELECT * FROM users WHERE username = ? AND password = ?";
            statement = connection.prepareStatement(verifyPasswordSql);
            statement.setString(1, username);
            statement.setString(2, currentPassword);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Update password
                String updatePasswordSql = "UPDATE users SET password = ? WHERE username = ?";
                statement = connection.prepareStatement(updatePasswordSql);
                statement.setString(1, newPassword);
                statement.setString(2, username);
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    response.setStatus(HttpServletResponse.SC_OK);
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } finally {
            // Close resources
            try {
                if (resultSet != null)
                    resultSet.close();
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
