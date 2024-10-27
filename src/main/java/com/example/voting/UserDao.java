package com.example.voting;

public interface UserDao {
    void saveUser(User user);

    User getUserByUsername(String username);
}
