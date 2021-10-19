package com.example.uts;

import static com.example.uts.notification.AppChannel.CHANNEL_1_ID;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.databinding.DataBindingUtil;

import android.app.Notification;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.uts.database.DatabaseDelivery;
import com.example.uts.databinding.ActivityDeliveryFormBinding;
import com.example.uts.model.Delivery;
import com.example.uts.model.User;

public class DeliveryForm extends AppCompatActivity {

    private NotificationManagerCompat notificationManager;
    private RadioButton radioButton;
    private RadioGroup radioGroup;
    private ImageButton btnMapPickup;
    private ImageButton btnMapDestination;
    Delivery delivery;
    ActivityDeliveryFormBinding binding;
    User user;
    String addressPickup, addressDestination;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_delivery_form);
        //Ambil data user yang ada dari fragmentHome,Mainactivity,Login
        user = getIntent().getParcelableExtra("user2");

        delivery = new Delivery();
        binding.setDelivery(delivery);
        binding.setActivity(this);

        radioGroup = findViewById(R.id.rg_fragile);
        delivery.setAddPickup("");
        delivery.setAddTujuan("");
        delivery.setFragile("");
        delivery.setIdUser(user.getId());
        delivery.setNamaPenerima("");
        delivery.setTipe("");

        notificationManager = NotificationManagerCompat.from(this);
        btnMapPickup = findViewById(R.id.ib_maps_pickup);
        btnMapDestination = findViewById(R.id.ib_maps_destination);

        btnMapPickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveMap = new Intent(DeliveryForm.this, Geolocation.class);
                startActivityForResult(moveMap,1);
            }
        });

        btnMapDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveMap = new Intent(DeliveryForm.this, Geolocation.class);
                startActivityForResult(moveMap,2);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                addressPickup = data.getStringExtra("address");
                delivery.setAddPickup(addressPickup);

            }
        }else if(requestCode == 2){
            if(resultCode == RESULT_OK){
                addressDestination = data.getStringExtra("address");
                delivery.setAddTujuan(addressDestination);
            }
        }
    }

    public void checkFragile(){
        int radioID = radioGroup.getCheckedRadioButtonId();

        if (radioID == -1) {
            delivery.setFragile("");
        } else {
            radioButton = findViewById(radioID);
            delivery.setFragile(radioButton.getText().toString());
        }
    }

    public View.OnClickListener btnBook = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            checkFragile();
            if(!delivery.getNamaPenerima().equals("") && !delivery.getTipe().equals("")
                && !delivery.getAddPickup().equals("") && !delivery.getAddTujuan().equals("")
                && !delivery.getFragile().equals("")){
                bookDelivery();
                sendOnChannel1(view);
                Intent balik = new Intent(DeliveryForm.this, MainActivity.class);
                balik.putExtra("user", user);
                balik.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(balik);
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
                Toast.makeText(DeliveryForm.this, "Booking Berhasil", Toast.LENGTH_SHORT).show();
            }
        }
        BookDelivery bookDelivery = new BookDelivery();
        bookDelivery.execute();
    }

    public void sendOnChannel1(View v){
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.freeway)
                .setContentTitle("Freeway")
                .setContentText("Delivery has been booked")
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();

        notificationManager.notify(1,notification);
    }

}