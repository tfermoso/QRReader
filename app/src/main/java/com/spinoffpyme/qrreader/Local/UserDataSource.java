package com.spinoffpyme.qrreader.Local;

import com.spinoffpyme.qrreader.Database.IUserDataSource;
import com.spinoffpyme.qrreader.Model.User;

import java.util.List;

/**
 * Created by Tomás on 20/04/2018.
 */

public class UserDataSource implements IUserDataSource {
    private  UserDAO userDAO;
    private static UserDataSource mInstance;
    public UserDataSource(UserDAO userDAO){
        this.userDAO=userDAO;
    }
    public static  UserDataSource getInstance(UserDAO userDAO){
        if(mInstance==null){
            mInstance=new UserDataSource(userDAO);
        }
        return mInstance;
    }

    @Override
    public User getUserById(int userId) {
        return userDAO.getUserById(userId);
    }

    public User getUserByQR(String qr){
        return  userDAO.getUserByQR(qr);
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public void inserUser(User... users) {
        userDAO.inserUser(users);
    }

    @Override
    public void updateUsser(User... users) {
        userDAO.updateUsser(users);
    }

    @Override
    public void deleteUser(User user) {
        userDAO.deleteUser(user);
    }

    @Override
    public void deleteAllUsers() {
        userDAO.deleteAllUsers();
    }
}
