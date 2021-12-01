package com.example.uts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.uts.model.User;
import com.example.uts.preferences.UserPreferences;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public static final int LAUNCH_ADD_ACTIVITY = 123;
    //Ambil data user yang ada di login
    User user;
    private UserPreferences userPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userPreferences = new UserPreferences(MainActivity.this);
        user = getIntent().getParcelableExtra("user");

        changeFragment(new FragmentHome(user));
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navlistener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navlistener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if(item.getItemId() == R.id.home){
                changeFragment(new FragmentHome(user));
            }else if(item.getItemId() == R.id.profile){
                changeFragment(new FragmentProfile(user));
            }
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Disini kita menghubungkan menu yang telah kita buat dengan activity ini
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.logout,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menu_logout){
            //  Jika menu yang dipilih adalah menu Exit, maka tampilkan sebuah dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure want to log out?")
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //  Keluar dari aplikasi
                            userPreferences.logout();
                            Toast.makeText(MainActivity.this, "Log out", Toast.LENGTH_SHORT).show();
                            checkLogin();
                        }
                    })
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void changeFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_layout,fragment)
                .commit();
    }

    public void checkLogin() {
        // this function will check if user login, akan memunculkan toast jika tidak redirect ke login activity
        if (!userPreferences.checkLogin()) {
            startActivity(new Intent(MainActivity.this, Login.class));
            MainActivity.this.finish();
        }
    }

}