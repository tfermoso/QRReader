package com.spinoffpyme.qrreader.Database;

import com.spinoffpyme.qrreader.Model.User;

import java.util.List;

/**
 * Created by Tomás on 20/04/2018.
 */

public class UserRepository implements IUserDataSource {
    private IUserDataSource mLocalDataSource;
    private static UserRepository mInstace;

    public UserRepository(IUserDataSource mLocalDataSource) {
        this.mLocalDataSource = mLocalDataSource;
    }

    public static UserRepository getInstance(IUserDataSource mLocalDataSource) {
        if (mInstace == null) {
            mInstace = new UserRepository(mLocalDataSource);
        }
        return mInstace;
    }

    @Override
    public User getUserById(int userId) {
        return mLocalDataSource.getUserById(userId);
    }

    public User getUserByQR(String qr) {
        return mLocalDataSource.getUserByQR(qr);
    }
    @Override
    public List<User> getAllUsers() {
        return mLocalDataSource.getAllUsers();
    }

    @Override
    public void inserUser(User... users) {
        mLocalDataSource.inserUser(users);
    }

    @Override
    public void updateUsser(User... users) {
        mLocalDataSource.updateUsser(users);
    }

    @Override
    public void deleteUser(User user) {
        mLocalDataSource.deleteUser(user);
    }

    @Override
    public void deleteAllUsers() {
        mLocalDataSource.deleteAllUsers();
    }
}

