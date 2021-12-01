package com.example.uts;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.uts.database.DatabaseUser;
import com.example.uts.model.User;
import com.example.uts.preferences.UserPreferences;
import com.example.uts.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;

public class Login extends AppCompatActivity implements Serializable {
    
    User user;
    ActivityLoginBinding binding;
    private UserPreferences userPreferences;
    private Intent mainActivity;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        userPreferences = new UserPreferences(Login.this);

        // Inisialisasi Objek dan Variabel
        user = new User();
        binding.setUser(user);
        binding.setActivity(this);
        user.setEmail("");
        user.setPassword("");

        mAuth = FirebaseAuth.getInstance();
        checkLogin();
    }

    //Response click listener buat button login
    public View.OnClickListener btnLogin = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (validation()){
                mAuth.signInWithEmailAndPassword(user.email,user.password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            if(mAuth.getCurrentUser().isEmailVerified()){
                                login(user.getEmail(), user.getPassword());
                            }else{
                                Toast.makeText(Login.this,"Verify Email First", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(Login.this,"Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
                    mainActivity = new Intent(Login.this, MainActivity.class);
                    // membuat intent
                    userPreferences.setLogin(user.getEmail(),user.getPassword());
                    // memasukan id ke intent
                    mainActivity.putExtra("user", user);
                    // bersihin top activity biar ga bisa di back
                    mainActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(mainActivity);
                    finish();
                } else {
                    Toast.makeText(Login.this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                }
            }
        }
        login get = new login();
        get.execute();
    }

    private void checkLogin() {
        User userLogin= userPreferences.getUserLogin();
        if (userPreferences.checkLogin()) {
            login(userLogin.email,userLogin.password);
            finish();
        }
    }
}