package com.example.uts;

import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.POST;
import static com.android.volley.Request.Method.PUT;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.uts.api.DeliveryAPI;
import com.example.uts.api.PengantarAPI;
import com.example.uts.model.Delivery;
import com.example.uts.model.DeliveryResponse;
import com.example.uts.model.Pengantar;
import com.example.uts.model.PengantarResponse;
import com.example.uts.model.PengantarResponseData;
import com.example.uts.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class PengantarForm extends AppCompatActivity {

    TextInputEditText tvNama, tvNotelp;
    Button btnAdd;
    public Pengantar pengantar;
    public RequestQueue queue;
    User user;
    Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengantar_form);

        queue = Volley.newRequestQueue(PengantarForm.this);
        tvNama = findViewById(R.id.etNamaPengantar);
        tvNotelp = findViewById(R.id.etNoTelpPengantar);
        btnAdd = findViewById(R.id.btnAddPengantar);

        user = getIntent().getParcelableExtra("dataUser");
        id = getIntent().getIntExtra("dataPengantar",-1);
        if(id==-1){
            tvNama.setText("");
            tvNotelp.setText("");
        }else{
            getPengantar(id);
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(id!=-1){
                    Toast.makeText(PengantarForm.this, "MASUK UPDATE ", Toast.LENGTH_SHORT).show();
                    updatePengantar(id);
                }else{
                    Toast.makeText(PengantarForm.this, "MASUK CREATE", Toast.LENGTH_SHORT).show();
                    createPengantar();
                }
                Intent balik = new Intent(PengantarForm.this, MainActivity.class);
                balik.putExtra("user",user);
                balik.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(balik);
            }
        });
    }

    private void getPengantar(int id){
        StringRequest stringRequest = new StringRequest(GET,
                PengantarAPI.GET_BY_ID_URL + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                PengantarResponseData pengantarResponse =
                        gson.fromJson(response, PengantarResponseData.class);
                Pengantar p = pengantarResponse.getPengantar();
                tvNama.setText(p.getNama());
                tvNotelp.setText(p.getNoTelp());
                //pengantar.setNama(p.getNama());
                //pengantar.setNoTelp(p.getNoTelp());
                Toast.makeText(PengantarForm.this,
                        pengantarResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    String responseBody = new String(error.networkResponse.data,
                            StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);
                    Toast.makeText(PengantarForm.this, errors.getString("message"),
                            Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(PengantarForm.this, e.getMessage(),
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

    private void createPengantar(){
        Pengantar temp = new Pengantar();
        temp.setNama(tvNama.getText().toString());
        temp.setNoTelp(tvNotelp.getText().toString());
        StringRequest stringRequest = new StringRequest(POST, PengantarAPI.ADD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        PengantarResponseData pengantarResponse =
                                gson.fromJson(response, PengantarResponseData.class);
                        Toast.makeText(PengantarForm.this,
                                pengantarResponse.getMessage(),
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
                    Toast.makeText(PengantarForm.this,
                            errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(PengantarForm.this, e.getMessage(),
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
                String requestBody = gson.toJson(temp);
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


    private void updatePengantar(int id){
        Pengantar temp = new Pengantar();
        temp.setNama(tvNama.getText().toString());
        temp.setNoTelp(tvNotelp.getText().toString());

        // Membuat request baru untuk mengedit data mahasiswa
        StringRequest stringRequest = new StringRequest(PUT,
                PengantarAPI.UPDATE_URL + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                PengantarResponseData pengantarResponse =
                        gson.fromJson(response, PengantarResponseData.class);
                Toast.makeText(PengantarForm.this,
                        pengantarResponse.getMessage(), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(PengantarForm.this,
                            errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(PengantarForm.this, e.getMessage(),
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
                 /* Serialisasi data dari java object PengantarResponse
                 menjadi JSON string menggunakan Gson */
                String requestBody = gson.toJson(temp);
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
}