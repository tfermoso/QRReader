package com.spinoffpyme.qrreader.Local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.spinoffpyme.qrreader.Model.User;

import java.util.List;

/**
 * Created by Tomás on 20/04/2018.
 */

@Dao
public interface UserDAO {
    @Query("SELECT * FROM users WHERE id=:userId")
    User getUserById(int userId);

    @Query("SELECT * FROM users WHERE qr=:qr")
    User getUserByQR(String qr);

    @Query("SELECT * FROM users")
    List<User> getAllUsers();

    @Insert
    void inserUser(User... users);

    @Update
    void updateUsser(User... users);

    @Delete
    void deleteUser(User user);

    @Query("DELETE FROM users")
    void deleteAllUsers();
}