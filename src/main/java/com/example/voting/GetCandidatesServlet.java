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

public class GetCandidatesServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Database connection parameters
        String jdbcURL = "jdbc:mysql://localhost:3306/online_voting_system";
        String dbUser = "root";
        String dbPassword = "root";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        List<Candidate> candidates = new ArrayList<>();

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection
            connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);

            // Create a SQL query
            String sql = "SELECT * FROM candidates";

            // Create a PreparedStatement
            statement = connection.prepareStatement(sql);

            // Execute the query
            resultSet = statement.executeQuery();

            // Process the result set
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String party = resultSet.getString("party");
                candidates.add(new Candidate(id, name, party));
            }

            // Convert the list of candidates to JSON
            String json = new Gson().toJson(candidates);

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

    private class Candidate {
        @SuppressWarnings("unused")
        private int id;
        @SuppressWarnings("unused")
        private String name;
        @SuppressWarnings("unused")
        private String party;

        public Candidate(int id, String name, String party) {
            this.id = id;
            this.name = name;
            this.party = party;
        }
    }
}
