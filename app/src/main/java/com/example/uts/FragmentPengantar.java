package com.example.uts;

import static com.android.volley.Request.Method.GET;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.uts.adapter.DeliveryAdapter;
import com.example.uts.adapter.PengantarAdapter;
import com.example.uts.api.DeliveryAPI;
import com.example.uts.api.PengantarAPI;
import com.example.uts.model.Delivery;
import com.example.uts.model.DeliveryResponse;
import com.example.uts.model.Pengantar;
import com.example.uts.model.PengantarResponse;
import com.example.uts.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentPengantar extends Fragment {

    SwipeRefreshLayout srRefresh;
    SearchView svPengantar;
    FloatingActionButton fab_add;
    private RecyclerView rv_pengantar;
    private List<Pengantar> pengantarList;
    private PengantarAdapter pengantarAdapter;
    private RequestQueue queue;
    User user;

    public FragmentPengantar() {
        // Required empty public constructor
    }
    
    public FragmentPengantar(User user){
        this.user = user;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pengantar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        srRefresh = view.findViewById(R.id.srPengantar);
        svPengantar = view.findViewById(R.id.sv_pengantar);
        rv_pengantar = view.findViewById(R.id.rv_pengantarList);
        rv_pengantar.setLayoutManager(new LinearLayoutManager(getActivity()));
        queue = Volley.newRequestQueue(getContext());
        fab_add = view.findViewById(R.id.fab_add);
        
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PengantarForm.class);
                startActivity(intent);
            }
        });
    }

    public void getAllPengantar(){
        srRefresh.setRefreshing(true);
        StringRequest stringRequest = new StringRequest(GET, PengantarAPI.GET_ALL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                PengantarResponse pengantarResponse =gson.fromJson(response,PengantarResponse.class);
                pengantarAdapter = new PengantarAdapter(pengantarResponse.getPengantarList(), getContext(), FragmentPengantar.this, user);
                Toast.makeText(getActivity(), pengantarResponse.getMessage(),Toast.LENGTH_SHORT).show();
                srRefresh.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                srRefresh.setRefreshing(false);
                try {
                    String responseBody = new String(error.networkResponse.data,
                            StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);
                    Toast.makeText(getActivity(),
                            errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                return headers;
            }
        };
        queue.add(stringRequest);
    }
}