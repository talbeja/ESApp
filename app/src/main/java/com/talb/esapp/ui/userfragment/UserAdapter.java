package com.talb.esapp.ui.userfragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.talb.esapp.R;
import com.talb.esapp.data.db.model.User;

import java.util.List;

// Adapter class for the user list RecyclerView
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> users;
    private  OnItemClickListener listener;

    // Interface to handle clicks on the buttons inside the CardView
    public interface OnItemClickListener {
        void onDeleteButtonClick(User user);
        void onEditButtonClick(User user, int position);
    }

    public UserAdapter(List<User> users, OnItemClickListener listener) {
        this.users = users;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = users.get(position);
        String userFullName = user.getFirstName() + " " + user.getLastName();
        holder.userName.setText(userFullName);
        holder.userEmail.setText(user.getEmail());

        // Handle the expanding/collapsing of the button bar on each CardView
        holder.itemView.findViewById(R.id.hidden_button_bar)
                .setVisibility(holder.isExpanded ? View.VISIBLE : View.GONE);

        holder.itemView.findViewById(R.id.details_bar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.isExpanded) {
                    holder.itemView.findViewById(R.id.hidden_button_bar).setVisibility(View.GONE);
                    holder.isExpanded = false;
                }
                else {
                    holder.itemView.findViewById(R.id.hidden_button_bar).setVisibility(View.VISIBLE);
                    holder.isExpanded = true;
                }
            }
        });


        // Button listeners to handle functionality of edit/delete
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDeleteButtonClick(users.get(holder.getAdapterPosition()));
                deleteUser(holder.getAdapterPosition());
            }
        });

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onEditButtonClick(users.get(holder.getAdapterPosition()), holder.getAdapterPosition());
            }
        });

        // Use Glide to load images (using placeholder if needed)
        Glide.with(holder.itemView.getContext())
                .load(user.getAvatar()) // URL for the avatar
                .placeholder(R.drawable.ic_avatar_placeholder)
                .into(holder.userAvatar);


    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    // The three functions below update the UI when a change to the list has been made
    private void deleteUser(int position) {
        users.remove(position);
        notifyItemRemoved(position);
    }

    public void editUser(int position, User user) {
        users.get(position).setAll(user);
        notifyItemChanged(position);
    }

    public void addNewUser(User user) {
        users.add(user);
        notifyItemInserted(users.size() - 1);
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        public ImageView userAvatar;
        public TextView userName;
        public TextView userEmail;
        public Button deleteButton;
        public Button editButton;
        public boolean isExpanded = false;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userAvatar = itemView.findViewById(R.id.user_avatar);
            userName = itemView.findViewById(R.id.user_name);
            userEmail = itemView.findViewById(R.id.user_email);
            deleteButton = itemView.findViewById(R.id.delete_button);
            editButton = itemView.findViewById(R.id.edit_button);
        }
    }
}
