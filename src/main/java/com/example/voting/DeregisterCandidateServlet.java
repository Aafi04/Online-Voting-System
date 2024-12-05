package com.example.voting;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeregisterCandidateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String candidateId = request.getParameter("candidate_id");

        // Database connection parameters
        String jdbcURL = "jdbc:mysql://localhost:3306/online_voting_system";
        String dbUser = "root";
        String dbPassword = "root";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection
            connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);

            // Create a SQL query
            String sql = "DELETE FROM candidates WHERE id = ?";

            // Create a PreparedStatement
            statement = connection.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(candidateId));

            // Execute the update
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                response.sendRedirect("candidate_management.html?success=true");
            } else {
                response.sendRedirect("candidate_management.html?success=false");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.sendRedirect("candidate_management.html?success=false");
        } finally {
            // Close resources
            try {
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
