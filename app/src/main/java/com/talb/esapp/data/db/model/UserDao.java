package com.talb.esapp.data.db.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insertUsers(List<User> users);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertNewUser(User user);

    @Query("SELECT * FROM users")
    List<User> getAllUsers();

    @Delete
    void delete(User user);

    @Update
    void updateUser(User user);
}
