package com.example.uts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.uts.databinding.ActivityLoginBinding;
import com.example.uts.databinding.FragmentHomeBinding;
import com.example.uts.model.User;

public class FragmentHome extends Fragment {

    User user;
    FragmentHomeBinding binding;

    public FragmentHome(User user){
        this.user=user ;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //binding layout fragment_home xml ke FragmentHome.java
        binding = DataBindingUtil.setContentView(getActivity(), R.layout.fragment_home);
        user = new User();
        binding.setUser(user);
        binding.setFragment(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public View.OnClickListener btnCreate = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent movePage = new Intent(getActivity(), DeliveryForm.class);
            movePage.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(movePage);
        }
    };


}
