package com.example.voting;

public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void registerUser(User user) {
        userDao.saveUser(user);
    }

    public User loginUser(String username, String password) {
        User user = userDao.getUserByUsername(username);
        // Add password verification logic here
        return user;
    }
}
