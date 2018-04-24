package com.spinoffpyme.qrreader.Database;

import com.spinoffpyme.qrreader.Model.User;

import java.util.List;

/**
 * Created by Tomás on 20/04/2018.
 */

public interface IUserDataSource {
    User getUserById(int userId);
    List<User> getAllUsers();
    void inserUser(User... users);
    void updateUsser(User... users);
    void deleteUser(User user);
    void deleteAllUsers();

    User getUserByQR(String qr);
}