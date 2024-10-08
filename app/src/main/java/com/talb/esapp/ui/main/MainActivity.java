package com.talb.esapp.ui.main;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.talb.esapp.R;
import com.talb.esapp.ui.about.AboutFragment;
import com.talb.esapp.ui.userfragment.UserFragment;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private MainContract.Presenter presenter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Setting up UserFragment
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new UserFragment())
                    .commit();
        }

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Removed the default toolbar title, as I implemented a custom textview in the toolbar
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    // About button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    // About button onClick
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = R.id.action_about;
        if (item.getItemId() == id)
        {
            showAboutFragment();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Displaying the About fragment
    private void showAboutFragment() {
        AboutFragment aboutFragment = new AboutFragment();
        aboutFragment.show(getSupportFragmentManager(), "about_fragment");
    }
}
