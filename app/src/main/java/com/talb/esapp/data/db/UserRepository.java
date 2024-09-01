package com.talb.esapp.data.db;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.talb.esapp.data.db.model.User;
import com.talb.esapp.data.network.ApiClient;
import com.talb.esapp.data.network.ApiService;
import com.talb.esapp.data.network.model.UserResponse;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// The repository class handles communications with the DB and API service
public class UserRepository {

    private ApiService apiService;
    private UserDatabase userDatabase;
    private ExecutorService executorService;

    public UserRepository(Context context) {
        apiService = ApiClient.getRetrofit().create(ApiService.class);
        userDatabase = Room.databaseBuilder(context, UserDatabase.class, "user_db").build();
        executorService = Executors.newSingleThreadExecutor();
    }

    // Fetch user list
    public LiveData<List<User>> getUsers() {
        MutableLiveData<List<User>> usersLiveData = new MutableLiveData<>();

        executorService.execute(() -> {
            // Get user list from the DB
            List<User> localUsers = userDatabase.userDao().getAllUsers();
            if (localUsers.isEmpty()) {
                // Fetch from API if local DB is empty
                apiService.getUsers(1, 10).enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<User> users = response.body().getData();
                            usersLiveData.postValue(users);
                            // Save to local database
                            executorService.execute(() -> userDatabase.userDao().insertUsers(users));
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        // Handle failure (to do)
                    }
                });
            } else {
                usersLiveData.postValue(localUsers);
            }
        });

        return usersLiveData;
    }

    // erase/edit/add user data to DB
    public void eraseUser(User user) {
        new Thread(() -> userDatabase.userDao().delete(user)).start();
    }

    public void editUser(User user) {
        new Thread(() -> userDatabase.userDao().updateUser(user)).start();
    }

    public void addUser(User user) {
        new Thread(() -> userDatabase.userDao().insertNewUser(user)).start();
    }
}
