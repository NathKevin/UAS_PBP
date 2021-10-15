package com.example.uts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.uts.database.DatabaseUser;
import com.example.uts.model.User;
import com.example.uts.databinding.ActivityRegisterBinding;

public class Register extends AppCompatActivity {
    User user;
    ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);

        user = new User();
        binding.setUser(user);
        binding.setActivity(this);
        user.setPassword("");
        user.setEmail("");
        user.setNama("");
        user.setConfirmPassword("");
        user.setNoTelp("");
        user.setUang(0);
    }

    public View.OnClickListener btnSignUp = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(!user.getNama().equals("") && !user.getNoTelp().equals("") && !user.getEmail().equals("") &&
                    !user.getPassword().equals("") && !user.getConfirmPassword().equals("") &&
                    user.getPassword().equals(user.getConfirmPassword())){
                addUser();
            }else if(!user.getPassword().equals(user.getConfirmPassword())) {
                Toast.makeText(Register.this, "Confirm password salah", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(Register.this, "Data belum lengkap", Toast.LENGTH_SHORT).show();
            }
        }
    };

    public View.OnClickListener tvClickHereRegister = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent movePage = new Intent(Register.this, Login.class);
            movePage.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(movePage);
        }
    };

    public void addUser(){

        class AddUser extends AsyncTask<Void, Void, Void>{

            @Override
            protected Void doInBackground(Void... voids) {

                DatabaseUser.getInstance(getApplicationContext())
                        .getDatabase()
                        .userDao()
                        .insertUser(user);

                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                Toast.makeText(Register.this,"Register Berhasil",Toast.LENGTH_SHORT).show();
                user.setNama("");
                user.setNoTelp("");
                user.setEmail("");
                user.setPassword("");
                user.setConfirmPassword("");
            }
        }
        AddUser addUser = new AddUser();
        addUser.execute();
    }
}