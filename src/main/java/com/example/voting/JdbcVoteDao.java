package com.example.voting;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcVoteDao implements VoteDao {

    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/online_voting_system";
        String user = "root";
        String password = "root";

        return DriverManager.getConnection(url, user, password);
    }

    @Override
    public void recordVote(String username, int candidateId) throws SQLException {
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO votes (user_id, candidate_id) VALUES ((SELECT id FROM users WHERE username = ?), ?)")) {
            System.out.println(
                    "Recording vote for user: " + username + " and candidate: " + candidateId);
            statement.setString(1, username);
            statement.setInt(2, candidateId);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new vote was recorded successfully!");
            } else {
                System.out.println("No rows inserted.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
