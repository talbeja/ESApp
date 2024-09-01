package com.talb.esapp.ui.userfragment;

import android.content.Context;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.talb.esapp.data.db.UserRepository;
import com.talb.esapp.data.db.model.User;

import java.util.List;

// The User presenter handles all data related operations and communication with User repository
public class UserPresenter implements UserContract.Presenter {

    private UserContract.View view;
    private UserRepository repository;

    public UserPresenter(UserContract.View view, Context context) {
        this.view = view;
        this.repository = new UserRepository(context);
    }

    // Get user list data from the repository, as well as handle an empty list
    @Override
    public void loadUsers() {
        LiveData<List<User>> usersLiveData = repository.getUsers();
        usersLiveData.observe((LifecycleOwner) view, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                if (users != null && !users.isEmpty()) {
                    view.showUsers(users);
                } else {
                    view.showNoUsers();
                }
            }
        });
    }

    // The three functions below pass relevant user information for changes to be made in the DB
    @Override
    public void removeUser(User user) {
        repository.eraseUser(user);
    }

    @Override
    public void editUser(User user) {
        repository.editUser(user);
    }

    @Override
    public void addUser(User user) {
        repository.addUser(user);
    }
}
