package org.souvenirshop.service;

import org.souvenirshop.dao.UserDao;
import org.souvenirshop.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public UserServiceImpl() {
        this.userDao = new UserDao();
    }

    @Override
    public User login(String username, String password) {
        List<User> users = userDao.getAll();
        return users.stream().filter(existedUser -> existedUser.getLogin().equals(username)
                && existedUser.getPassword().equals(password)).findFirst().orElse(null);
    }

    public void register(User user) {
        if (validateUser(user)) {
            userDao.save(user);
        } else {
            System.out.println("Failed to register user. User with login " + user.getLogin() + " already exists!");
        }
    }

    @Override
    public boolean validateUser(User user) {
        List<User> users = userDao.getAll();
        return users.stream().noneMatch(existedUser -> existedUser.getLogin().equals(user.getLogin()));
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }
}