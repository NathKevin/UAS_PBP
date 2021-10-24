package com.example.uts;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.uts.database.DatabaseUser;
import com.example.uts.databinding.FragmentProfileBinding;
import com.example.uts.model.User;

public class FragmentProfile extends Fragment {

    User user;
    FragmentProfileBinding binding;

    public FragmentProfile(){

    }

    public FragmentProfile(User user){
        this.user = user;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        View view = binding.getRoot();
        binding.setUser(user);
        binding.setFragment(this);
        user.setConfirmPassword("");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Button btnSave = view.findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!user.getNama().equals("") && !user.getNoTelp().equals("") && !user.getEmail().equals("") &&
                        !user.getPassword().equals("") && !user.getConfirmPassword().equals("")
                         && user.getPassword().equals(user.getConfirmPassword())){
                    updateUser();
                    user.setConfirmPassword("");
                }else if(!user.getPassword().equals(user.getConfirmPassword())){
                    Toast.makeText(getActivity(), "Confirm password salah", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), "Data belum lengkap", Toast.LENGTH_SHORT).show();
                }
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    private void updateUser(){
        class UpdateUser extends AsyncTask<Void, Void, Void>{

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseUser.getInstance(getActivity().getApplicationContext())
                        .getDatabase()
                        .userDao()
                        .updateUser(user);
                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                Toast.makeText(getActivity(), "Update Profile Berhasil", Toast.LENGTH_SHORT).show();
            }
        }
        UpdateUser updateUser = new UpdateUser();
        updateUser.execute();
    }

}
