package com.talb.esapp.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.talb.esapp.data.db.model.User;
import com.talb.esapp.data.db.model.UserDao;

@Database(entities = {User.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
