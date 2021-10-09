package com.example.uts;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.uts.database.DatabaseUser;
import com.example.uts.model.User;
import com.example.uts.preferences.UserPreferences;
import com.example.uts.databinding.ActivityLoginBinding;

import java.io.Serializable;

public class Login extends AppCompatActivity implements Serializable {
    
    User user;
    ActivityLoginBinding binding;
    private UserPreferences userPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        // Inisialisasi Objek dan Variabel
        user = new User();
        binding.setUser(user);
        binding.setActivity(this);
        user.setEmail("");
        user.setPassword("");

    }

    //Response click listener buat button login
    public View.OnClickListener btnLogin = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (validation()){
                login(user.getEmail(), user.getPassword());
            }
        }
    };

    public View.OnClickListener btnClickHereRegis = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent regis = new Intent(Login.this, Register.class);
            regis.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(regis);
        }
    };

    public boolean validation() {
        if (user.getEmail().equals("") || user.getPassword().equals("")) {
            Toast.makeText(Login.this, "Data belum lengkap", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void login(String email, String password) {
        class login extends AsyncTask<Void, Void, User> {

            @Override
            protected User doInBackground(Void... voids) {
                User user = DatabaseUser.getInstance(getApplicationContext())
                        .getDatabase()
                        .userDao()
                        .login(email, password);

                return user;
            }

            @Override
            protected void onPostExecute(User user) {
                super.onPostExecute(user);
                if (user != null) { //jika data ditemukan
                    // membuat intent
                    Intent mainActivity = new Intent(Login.this, MainActivity.class);

                    // memasukan id ke intent
                    mainActivity.putExtra("user", user);
                    // bersihin top activity biar ga bisa di back
                    mainActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(mainActivity);
                } else {
                    Toast.makeText(Login.this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                }
            }
        }
        login get = new login();
        get.execute();
    }
}