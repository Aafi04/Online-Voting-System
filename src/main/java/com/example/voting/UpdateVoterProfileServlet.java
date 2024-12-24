package com.example.voting;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateVoterProfileServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String voterId = request.getParameter("voterId");
        String name = request.getParameter("name");
        String dateOfBirth = request.getParameter("dateOfBirth");
        String city = request.getParameter("city");
        String contact = request.getParameter("contact");

        String url = "jdbc:mysql://localhost:3306/online_voting_system";
        String user = "root";
        String password = "root";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String checkSql = "SELECT COUNT(*) FROM voters WHERE voter_id = ?";
            try (PreparedStatement checkStatement = connection.prepareStatement(checkSql)) {
                checkStatement.setInt(1, Integer.parseInt(voterId));
                ResultSet resultSet = checkStatement.executeQuery();
                if (resultSet.next() && resultSet.getInt(1) > 0) {
                    // Update existing record
                    String updateSql = "UPDATE voters SET name = ?, date_of_birth = ?, city = ?, contact = ? WHERE voter_id = ?";
                    try (PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
                        updateStatement.setString(1, name);
                        updateStatement.setString(2, dateOfBirth);
                        updateStatement.setString(3, city);
                        updateStatement.setString(4, contact);
                        updateStatement.setInt(5, Integer.parseInt(voterId));

                        int rowsUpdated = updateStatement.executeUpdate();
                        if (rowsUpdated > 0) {
                            response.getWriter().write("Voter profile updated successfully.");
                        } else {
                            response.getWriter().write("Failed to update voter profile.");
                        }
                    }
                } else {
                    // Insert new record
                    String insertSql = "INSERT INTO voters (voter_id, name, date_of_birth, city, contact) VALUES (?, ?, ?, ?, ?)";
                    try (PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
                        insertStatement.setInt(1, Integer.parseInt(voterId));
                        insertStatement.setString(2, name);
                        insertStatement.setString(3, dateOfBirth);
                        insertStatement.setString(4, city);
                        insertStatement.setString(5, contact);

                        int rowsInserted = insertStatement.executeUpdate();
                        if (rowsInserted > 0) {
                            response.getWriter().write("Voter profile created successfully.");
                        } else {
                            response.getWriter().write("Failed to create voter profile.");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }
}
