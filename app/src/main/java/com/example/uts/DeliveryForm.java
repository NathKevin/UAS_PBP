package com.example.uts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.uts.database.DatabaseDelivery;
import com.example.uts.databinding.ActivityDeliveryFormBinding;
import com.example.uts.model.Delivery;
import com.example.uts.model.User;

public class DeliveryForm extends AppCompatActivity {

    Delivery delivery;
    ActivityDeliveryFormBinding binding;
    User user;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_delivery_form);
        //Ambil data user yang ada dari fragmentHome,Mainactivity,Login
        user = getIntent().getParcelableExtra("user2");

        delivery = new Delivery();
        binding.setDelivery(delivery);
        binding.setActivity(this);

        delivery.setAddPickup("");
        delivery.setAddTujuan("");
        delivery.setFragile("");
        delivery.setIdUser(user.getId());
        delivery.setNamaPenerima("");
        delivery.setTipe("");

    }

    public void checkFragile(RadioGroup RG, int id){
        if(id==1){
            delivery.setFragile("YES");
        }else{
            delivery.setFragile("NO");
        }
    }

    public View.OnClickListener btnBook = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(!delivery.getNamaPenerima().equals("") && !delivery.getTipe().equals("")
                && !delivery.getAddPickup().equals("") && !delivery.getAddTujuan().equals("")
                && !delivery.getFragile().equals("")){
                bookDelivery();
                finish();
            }else{
                Toast.makeText(DeliveryForm.this, "Data belum lengkap", Toast.LENGTH_SHORT).show();
            }
        }
    };

    public void bookDelivery(){

        class BookDelivery extends AsyncTask<Void, Void, Void>{

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseDelivery.getInstance(getApplicationContext())
                        .getDatabase()
                        .deliveryDao()
                        .insertDelivery(delivery);
                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                Toast.makeText(DeliveryForm.this,"Booking Berhasil",Toast.LENGTH_SHORT).show();
                delivery.setAddPickup("");
                delivery.setAddTujuan("");
                delivery.setFragile("");
                delivery.setIdUser(0);
                delivery.setNamaPenerima("");
                delivery.setTipe("");
            }
        }
        BookDelivery bookDelivery = new BookDelivery();
        bookDelivery.execute();
    }

}