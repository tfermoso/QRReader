package com.spinoffpyme.qrreader.Local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.spinoffpyme.qrreader.Model.User;

/**
 * Created by Tomás on 20/04/2018.
 */

@Database(entities = User.class,version=UserDatabase.DATABASE_VERSION,exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME="Database-Room";

    public abstract UserDAO userDAO();

    private static UserDatabase mInstance;

    public static UserDatabase getmInstance(Context context){
        if(mInstance==null){
            mInstance= Room.databaseBuilder(context,UserDatabase.class,DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return mInstance;
    }

}
