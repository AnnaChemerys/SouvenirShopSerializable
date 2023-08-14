package org.souvenirshop.dao;

import org.souvenirshop.model.User;

import java.util.List;

public class UserDao extends AbstractDao<User> {

    @Override
    protected String getFileName() {
        return FileNames.USERS.getFileName();
    }

    @Override
    public void update(User user) {
        List<User> tempList = items;
        for (User userTemp : tempList) {
            if (userTemp.equals(user)) {
                userTemp.setRole(user.getRole());
                userTemp.setPassword(user.getPassword());
            }
        }
        fileOperation.writeIntoFile(tempList, filename);
        items = tempList;
    }

    public User getByLogin(String login) {
        List<User> tempList = items;
        for (User userTemp : tempList) {
            if (userTemp.getLogin().equals(login)) {
                return userTemp;
            }
        }
        return null;
    }
}