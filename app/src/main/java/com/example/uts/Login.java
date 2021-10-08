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

public class Login extends AppCompatActivity {
    
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
    }

    //Response click listener buat button login
    public View.OnClickListener btnLogin = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
           if (user.getEmail().equals("edwin") && user.getPassword().equals("edwin")){
               Toast.makeText(Login.this, "mantab", Toast.LENGTH_SHORT).show();
           }
        }
    };

    public View.OnClickListener btnClickHereRegis = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    private void getID(String email, String password) {
        class getID extends AsyncTask<Void, Void, Integer> {

            @Override
            protected Integer doInBackground(Void... voids) {
                int id = DatabaseUser.getInstance(getApplicationContext())
                        .getDatabase()
                        .userDao()
                        .getId(email, password);

                return id;
            }

            @Override
            protected void onPostExecute(Integer id) {
                super.onPostExecute(id);
                if (id) { //jika data ditemukan
                    // membuat intent
                    Intent mainActivity = new Intent(Login.this, MainActivity.class);

                    // memasukan id ke intent
                    mainActivity.putExtra("id", id);
                    // bersihin top activity biar ga bisa di back
                    mainActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(mainActivity);
                } else {
                    Toast.makeText(Login.this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                }
            }
        }
        getID get = new getID();
        get.execute();
    }
}