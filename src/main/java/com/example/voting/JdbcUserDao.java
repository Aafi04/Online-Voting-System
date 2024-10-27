package com.example.voting;

import java.sql.*;

public class JdbcUserDao implements UserDao {

    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/online_voting_system";
        String user = "root";
        String password = "root";

        return DriverManager.getConnection(url, user, password);
    }

    @Override
    public void saveUser(User user) {
        try (Connection connection = getConnection();
                PreparedStatement statement = connection
                        .prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)")) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Implement the getUserByUsername method as needed
    @Override
    public User getUserByUsername(String username) {
        User user = null;
        try (Connection connection = getConnection();
                PreparedStatement statement =
                        connection.prepareStatement("SELECT * FROM users WHERE username = ?")) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));

                user.setPassword(resultSet.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Consider logging the error or throwing a custom exception
        }
        return user;
    }
}

