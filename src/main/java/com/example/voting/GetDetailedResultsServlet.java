package com.example.voting;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetDetailedResultsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("GetDetailedResultsServlet: Received request"); // Log message

        // Database connection parameters
        String jdbcURL = "jdbc:mysql://localhost:3306/online_voting_system";
        String dbUser = "root";
        String dbPassword = "root";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        List<DetailedResult> results = new ArrayList<>();

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection
            connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);

            // Create a SQL query
            String sql = "SELECT u.username, c.name AS candidate_name " + "FROM votes v "
                    + "JOIN users u ON v.user_id = u.id "
                    + "JOIN candidates c ON v.candidate_id = c.id";

            // Create a PreparedStatement
            statement = connection.prepareStatement(sql);

            // Execute the query
            resultSet = statement.executeQuery();

            // Process the result set
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String candidateName = resultSet.getString("candidate_name");
                System.out.println("User: " + username + ", Candidate: " + candidateName); // Debug
                                                                                           // statement
                results.add(new DetailedResult(username, candidateName));
            }

            // Convert the list of results to JSON
            String json = new Gson().toJson(results);

            // Set the response content type to JSON
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // Write the JSON to the response
            PrintWriter out = response.getWriter();
            out.write(json);
            out.flush();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
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

    private class DetailedResult {
        @SuppressWarnings("unused")
        private String username;
        @SuppressWarnings("unused")
        private String candidateName;

        public DetailedResult(String username, String candidateName) {
            this.username = username;
            this.candidateName = candidateName;
        }
    }
}
