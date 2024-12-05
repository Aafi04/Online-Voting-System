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

public class GetCompiledResultsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("GetCompiledResultsServlet: Received request"); // Log message

        // Database connection parameters
        String jdbcURL = "jdbc:mysql://localhost:3306/online_voting_system";
        String dbUser = "root";
        String dbPassword = "root";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        List<Result> results = new ArrayList<>();

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection
            connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);

            // Create a SQL query
            String sql = "SELECT c.name AS candidate_name, COUNT(v.id) AS votes " + "FROM votes v "
                    + "JOIN candidates c ON v.candidate_id = c.id " + "GROUP BY c.name";

            // Create a PreparedStatement
            statement = connection.prepareStatement(sql);

            // Execute the query
            resultSet = statement.executeQuery();

            // Process the result set
            while (resultSet.next()) {
                String candidateName = resultSet.getString("candidate_name");
                int votes = resultSet.getInt("votes");
                System.out.println("Candidate: " + candidateName + ", Votes: " + votes); // Debug
                                                                                         // statement
                results.add(new Result(candidateName, votes));
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

    private class Result {
        @SuppressWarnings("unused")
        private String candidateName;
        @SuppressWarnings("unused")
        private int votes;

        public Result(String candidateName, int votes) {
            this.candidateName = candidateName;
            this.votes = votes;
        }
    }
}
