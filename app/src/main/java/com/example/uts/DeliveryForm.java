package com.example.uts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.uts.databinding.ActivityDeliveryFormBinding;
import com.example.uts.databinding.ActivityLoginBinding;
import com.example.uts.model.Delivery;
import com.example.uts.model.User;

public class DeliveryForm extends AppCompatActivity {

    Delivery delivery;
    ActivityDeliveryFormBinding binding;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_form);

        delivery = new Delivery();
        binding.setDelivery(delivery);
        binding.setActivity(this);

        delivery.setAddPickup("");
        delivery.setAddTujuan("");
        delivery.setFragile("");
        delivery.setIdUser(0);
        delivery.setNamaPenerima("");
        delivery.setTipe("");
    }


}