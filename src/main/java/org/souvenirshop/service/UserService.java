package org.souvenirshop.service;

import org.souvenirshop.model.User;

import java.util.List;

public interface UserService {

    User login(String username, String password);

    void register(User user);

    boolean validateUser(User user);

    List<User> getAll();
}
