package com.example.voting;

import java.sql.*;

public class JdbcVoteDao implements VoteDao {

    private Connection getConnection() throws SQLException {
        // Replace with your database connection details
        String url = "jdbc:mysql://localhost:3306/online_voting_system";
        String user = "root";
        String password = "root";

        return DriverManager.getConnection(url, user, password);
    }

    @Override
    public void saveVote(Vote vote) {
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO votes (user_id, candidate_id) VALUES (?, ?)")) {
            statement.setInt(1, vote.getUserId());
            statement.setInt(2, vote.getCandidateId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately, e.g., log the error, throw a custom exception
        }
    }
}
