package com.example.uts.UnitTesting;

import static com.android.volley.Request.Method.POST;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BaseHttpStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.uts.PengantarForm;
import com.example.uts.api.PengantarAPI;
import com.example.uts.model.Pengantar;
import com.example.uts.model.PengantarResponseData;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class PengantarService {

    RequestQueue queue;

    public void pengantar(final PengantarView view, Pengantar pengantar, final
    PengantarCallback callback) {

        StringRequest stringRequest = new StringRequest(POST, PengantarAPI.ADD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        PengantarResponseData pengantarResponse =
                                gson.fromJson(response, PengantarResponseData.class);

                        if (pengantarResponse.getMessage().equalsIgnoreCase("Add pengantar success")) {
                            callback.onSuccess(true, pengantarResponse.getPengantar());
                        } else {
                            callback.onError();
                            view.showPengantarError(pengantarResponse.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    String responseBody = new String(error.networkResponse.data,
                            StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);
                    callback.onError();
                    view.showPengantarError(errors.getString("message"));
                } catch (Exception e) {
                    callback.onError();
                    view.showPengantarError(e.getMessage());
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
                String requestBody = gson.toJson(pengantar);
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

    public Boolean getValid(final PengantarView view, Pengantar pengantar) {
        final Boolean[] bool = new Boolean[1];
        pengantar(view, pengantar, new PengantarCallback() {
            @Override
            public void onSuccess(boolean value, Pengantar pengantar) {
                bool[0] = true;
            }

            @Override
            public void onError() {
                bool[0] = false;
            }
        });
        return bool[0];
    }
}
