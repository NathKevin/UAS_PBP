package com.example.uts;

import androidx.annotation.NonNull;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    User user;
    ActivityRegisterBinding binding;

    FirebaseAuth mAuth;

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

        mAuth = FirebaseAuth.getInstance();
    }

    public View.OnClickListener btnSignUp = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(!user.getNama().equals("") && !user.getNoTelp().equals("") && !user.getEmail().equals("") &&
                    !user.getPassword().equals("") && !user.getConfirmPassword().equals("") &&
                    user.getPassword().equals(user.getConfirmPassword())){
                mAuth.createUserWithEmailAndPassword(user.email,user.password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        addUser();
                                        Intent movePage = new Intent(Register.this, Login.class);
                                        movePage.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(movePage);
                                    }else{
                                        Toast.makeText(Register.this,"Verivication not sended" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(Register.this,"Register Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
                Toast.makeText(Register.this,"Register Berhasil, Check Email for Verify",Toast.LENGTH_SHORT).show();
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