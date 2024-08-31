package com.talb.esapp.ui.userfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.talb.esapp.R;
import com.talb.esapp.data.db.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserFragment extends Fragment implements UserContract.View, UserAdapter.OnItemClickListener {

    private Button addButton;
    private RecyclerView recyclerView;
    private UserAdapter adapter;
    private UserContract.Presenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_view);
        addButton = view.findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddButtonClick();
            }
        });

        presenter = new UserPresenter(this, getContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        presenter.loadUsers();
    }

    @Override
    public void showUsers(List<User> users) {
        adapter = new UserAdapter(users, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showNoUsers() {
        List<User> mockUsers = new ArrayList<>();
        mockUsers.add(new User(1, "No", "users found", "Please report error", "https://via.placeholder.com/150"));

        adapter = new UserAdapter(mockUsers, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDeleteButtonClick(User user) {
        presenter.removeUser(user);
    }

    @Override
    public void onEditButtonClick(User user, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_edit_user, null);
        builder.setView(dialogView);

        EditText editFirstName = dialogView.findViewById(R.id.edit_first_name);
        EditText editLastName = dialogView.findViewById(R.id.edit_last_name);
        EditText editEmail = dialogView.findViewById(R.id.edit_email);

        // Pre-fill the fields with the existing user data
        editFirstName.setText(user.getFirstName());
        editLastName.setText(user.getLastName());
        editEmail.setText(user.getEmail());

        builder.setPositiveButton("Save", (dialog, which) -> {
            String newFirstName = editFirstName.getText().toString();
            String newLastName = editLastName.getText().toString();
            String newEmail = editEmail.getText().toString();

            // Update the user object
            user.setFirstName(newFirstName);
            user.setLastName(newLastName);
            user.setEmail(newEmail);

            // Update the user in the database
            presenter.editUser(user);

            // Notify the adapter about the update
            adapter.editUser(position, user);
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void onAddButtonClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_edit_user, null);
        builder.setView(dialogView);

        EditText editFirstName = dialogView.findViewById(R.id.edit_first_name);
        EditText editLastName = dialogView.findViewById(R.id.edit_last_name);
        EditText editEmail = dialogView.findViewById(R.id.edit_email);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String newFirstName = editFirstName.getText().toString();
            String newLastName = editLastName.getText().toString();
            String newEmail = editEmail.getText().toString();

            // Create the user object
            User user = new User(newFirstName, newLastName, newEmail);

            // Update the user in the database
            presenter.addUser(user);

            // Notify and update adapter on changes
            adapter.addNewUser(user);
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

