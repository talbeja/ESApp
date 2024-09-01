package com.talb.esapp.ui.userfragment;

import com.talb.esapp.data.db.model.User;

import java.util.List;

// Contract between User view and presenter
public interface UserContract {
    interface View {
        void showUsers(List<User> users);
        void showNoUsers();
    }

    interface Presenter {
        void loadUsers();
        void removeUser(User user);
        void editUser(User user);
        void addUser(User user);
    }
}
