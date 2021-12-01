package com.example.uts;

import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.POST;
import static com.android.volley.Request.Method.PUT;
import static com.example.uts.notification.AppChannel.CHANNEL_1_ID;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.uts.api.DeliveryAPI;
import com.example.uts.database.DatabaseDelivery;
import com.example.uts.databinding.ActivityDeliveryFormBinding;
import com.example.uts.model.Delivery;
import com.example.uts.model.DeliveryResponse;
import com.example.uts.model.User;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DeliveryForm extends AppCompatActivity {

    private NotificationManagerCompat notificationManager;
    private RadioButton radioButton;
    private RadioGroup radioGroup;
    private ImageButton btnMapPickup;
    private ImageButton btnMapDestination;
    Delivery delivery;
    ActivityDeliveryFormBinding binding;
    User user=null;
    Integer id_delivery=null;
    String addressPickup, addressDestination;
    private RequestQueue queue;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_delivery_form);
        //Ambil data user yang ada dari fragmentHome,Mainactivity,Login
        queue = Volley.newRequestQueue(this);
        user = getIntent().getParcelableExtra("user2");
        if(user==null){
            user = getIntent().getParcelableExtra("dataUser");
        }
        id_delivery = getIntent().getIntExtra("dataDelivery", -1);

        delivery = new Delivery();
        binding.setDelivery(delivery);
        binding.setActivity(this);

        if(id_delivery == -1){
            delivery.setIdUser(user.getId());
            delivery.setIdPengantar(-1);
            delivery.setAddPickup("Solo");
            delivery.setAddTujuan("Jojga");
            delivery.setFragile("Yes");
            delivery.setNamaPenerima("Kevin2");
            delivery.setTipe("Food");
        }else{
            getDelivery(id_delivery);
        }

        radioGroup = findViewById(R.id.rg_fragile);

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
                if(id_delivery == -1){
                    bookDelivery();
                    Toast.makeText(DeliveryForm.this, "MASUK BOOK", Toast.LENGTH_SHORT).show();
                }else{
                    updateDelivery(id_delivery);
                }
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

    private void getDelivery(int id){
        StringRequest stringRequest = new StringRequest(GET,
                DeliveryAPI.GET_BY_ID_URL + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                DeliveryResponse deliveryResponse =
                        gson.fromJson(response, DeliveryResponse.class);
                Delivery deliv = deliveryResponse.getDeliveryList().get(0);
                delivery.setIdDelivery(deliv.getIdDelivery());
                delivery.setIdUser(deliv.getIdUser());
                delivery.setIdPengantar(deliv.getIdPengantar());
                delivery.setAddPickup(deliv.getAddPickup());
                delivery.setAddTujuan(deliv.getAddTujuan());
                delivery.setNamaPenerima(deliv.getNamaPenerima());
                delivery.setFragile(deliv.getFragile());
                delivery.setTipe(deliv.getTipe());
                Toast.makeText(DeliveryForm.this,
                        deliveryResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    String responseBody = new String(error.networkResponse.data,
                            StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);
                    Toast.makeText(DeliveryForm.this, errors.getString("message"),
                            Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(DeliveryForm.this, e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            // Menambahkan header pada request
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                return headers;
            }
        };
        // Menambahkan request ke request queue
        queue.add(stringRequest);
    }

    private void bookDelivery(){
        Delivery deliv = new Delivery(
                delivery.getNamaPenerima(),
                delivery.getTipe(),
                delivery.getFragile(),
                delivery.getAddPickup(),
                delivery.getAddTujuan(),
                user.getId(),
                -1); //INI RANDOM INGATTTTTTTTTTTTTTTTTTTT

        StringRequest stringRequest = new StringRequest(POST, DeliveryAPI.ADD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                         /* Deserialiasai data dari response JSON dari API
                         menjadi java object MahasiswaResponse menggunakan Gson */
                        DeliveryResponse deliveryResponse =
                                gson.fromJson(response, DeliveryResponse.class);
                        Toast.makeText(DeliveryForm.this,
                                deliveryResponse.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        Intent returnIntent = new Intent();
                        setResult(Activity.RESULT_OK, returnIntent);
                        finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    String responseBody = new String(error.networkResponse.data,
                            StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);
                    Toast.makeText(DeliveryForm.this,
                            errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(DeliveryForm.this, e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            // Menambahkan header pada request
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                return headers;
            }
            // Menambahkan request body berupa object mahasiswa
            @Override
            public byte[] getBody() throws AuthFailureError {
                Gson gson = new Gson();
                String requestBody = gson.toJson(deliv);
                return requestBody.getBytes(StandardCharsets.UTF_8);
            }
            // Mendeklarasikan content type dari request body yang ditambahkan
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        // Menambahkan request ke request queue
        queue.add(stringRequest);
    }

    private void updateDelivery(int id){
        Delivery deliv = new Delivery(
                delivery.getNamaPenerima(),
                delivery.getTipe(),
                delivery.getFragile(),
                delivery.getAddPickup(),
                delivery.getAddTujuan(),
                delivery.getIdUser(),
                delivery.getIdPengantar());
        // Membuat request baru untuk mengedit data mahasiswa
        StringRequest stringRequest = new StringRequest(PUT,
                DeliveryAPI.UPDATE_URL + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                DeliveryResponse deliveryResponse =
                        gson.fromJson(response, DeliveryResponse.class);
                Toast.makeText(DeliveryForm.this,
                        deliveryResponse.getMessage(), Toast.LENGTH_SHORT).show();
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    String responseBody = new String(error.networkResponse.data,
                            StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);
                    Toast.makeText(DeliveryForm.this,
                            errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(DeliveryForm.this, e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            // Menambahkan header pada request
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                return headers;
            }
            // Menambahkan request body berupa object mahasiswa
            @Override
            public byte[] getBody() throws AuthFailureError {
                Gson gson = new Gson();
                 /* Serialisasi data dari java object MahasiswaResponse
                 menjadi JSON string menggunakan Gson */
                String requestBody = gson.toJson(deliv);
                return requestBody.getBytes(StandardCharsets.UTF_8);
            }
            // Mendeklarasikan content type dari request body yang ditambahkan
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        // Menambahkan request ke request queue
        queue.add(stringRequest);
    }

//    public void bookDelivery(){
//
//        class BookDelivery extends AsyncTask<Void, Void, Void>{
//
//            @Override
//            protected Void doInBackground(Void... voids) {
//                DatabaseDelivery.getInstance(getApplicationContext())
//                        .getDatabase()
//                        .deliveryDao()
//                        .insertDelivery(delivery);
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Void unused) {
//                super.onPostExecute(unused);
//                Toast.makeText(DeliveryForm.this,"Booking Berhasil",Toast.LENGTH_SHORT).show();
//                delivery.setAddPickup("");
//                delivery.setAddTujuan("");
//                delivery.setFragile("");
//                delivery.setIdUser(0);
//                delivery.setNamaPenerima("");
//                delivery.setTipe("");
//                Toast.makeText(DeliveryForm.this, "Booking Berhasil", Toast.LENGTH_SHORT).show();
//            }
//        }
//        BookDelivery bookDelivery = new BookDelivery();
//        bookDelivery.execute();
//    }

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