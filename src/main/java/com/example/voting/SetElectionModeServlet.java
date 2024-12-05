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

public class SetElectionModeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String mode = request.getParameter("mode");
        String startTime = request.getParameter("start_time");
        String endTime = request.getParameter("end_time");

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
            String sql =
                    "UPDATE election_mode SET mode = ?, start_time = ?, end_time = ? WHERE id = 1";

            // Create a PreparedStatement
            statement = connection.prepareStatement(sql);
            statement.setString(1, mode);
            if ("timeless".equals(mode)) {
                statement.setNull(2, java.sql.Types.TIMESTAMP);
                statement.setNull(3, java.sql.Types.TIMESTAMP);
            } else {
                statement.setString(2, startTime);
                statement.setString(3, endTime);
            }

            // Execute the update
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                response.sendRedirect("election_mode.html?success=true");
            } else {
                response.sendRedirect("election_mode.html?success=false");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.sendRedirect("election_mode.html?success=false");
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
