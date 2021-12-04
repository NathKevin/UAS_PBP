package com.example.uts;

import static androidx.databinding.library.baseAdapters.BR.user1;
import static com.android.volley.Request.Method.GET;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import com.example.uts.database.DatabaseDelivery;
import com.example.uts.databinding.ActivityLoginBinding;
import com.example.uts.databinding.FragmentHomeBinding;
import com.example.uts.model.Delivery;
import com.example.uts.model.DeliveryResponse;
import com.example.uts.model.Pengantar;
import com.example.uts.model.PengantarResponse;
import com.example.uts.model.User;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentHome extends Fragment {

    private RecyclerView rv_delivery;
    private List<Delivery> deliveryList;
    private DeliveryAdapter deliveryAdapter;
    private SwipeRefreshLayout srDelivery;
    User user;
    int id;
    FragmentHomeBinding binding;
    private RequestQueue queue;
    CircleImageView ivGambar;

    public FragmentHome() {
        // Required empty public constructor
    }

    public FragmentHome(User user){
        this.user=user ;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //binding layout fragment_home xml ke FragmentHome.java
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container,false);
        View view = binding.getRoot();
        binding.setUser1(user);
        binding.setFragment(this);
        queue = Volley.newRequestQueue(getContext());
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivGambar = view.findViewById(R.id.icon);
        if(user.getGambar()!=null){
            ivGambar.setImageBitmap(DataConverter.convertByteArray2Image(user.getGambar()));
        }

        rv_delivery = view.findViewById(R.id.rv_deliveryList);
        rv_delivery.setLayoutManager(new LinearLayoutManager(getActivity()));
        srDelivery = view.findViewById(R.id.srDelivery);

        getAllDelivery(user.getId());
    }

    public View.OnClickListener btnCreate = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent movePage = new Intent(getActivity(), DeliveryForm.class);
            movePage.putExtra("user2",user);
            startActivity(movePage);
        }
    };

    public void getAllDelivery(int id){
        srDelivery.setRefreshing(true);
        StringRequest stringRequest = new StringRequest(GET, DeliveryAPI.GET_ALL_URL + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                DeliveryResponse deliveryResponse =gson.fromJson(response,DeliveryResponse.class);
                deliveryAdapter = new DeliveryAdapter(deliveryResponse.getDeliveryList(), getActivity(), FragmentHome.this, user);
                rv_delivery.setAdapter(deliveryAdapter);
                Toast.makeText(getActivity(), deliveryResponse.getMessage(),Toast.LENGTH_SHORT).show();
                srDelivery.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                srDelivery.setRefreshing(false);
                try {
                    String responseBody = new String(error.networkResponse.data,
                            StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);
                    Toast.makeText(getActivity(),
                            errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(),
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

    @Override
    public void onResume() {
        super.onResume();
        getUser();
        getFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }

    private void getUser(){
        class GetUser extends AsyncTask<Void, Void, User> {

            @Override
            protected User doInBackground(Void... voids) {
                User newUser = DatabaseDelivery.getInstance(getActivity().getApplicationContext())
                        .getDatabase()
                        .userDao()
                        .getNew(user.getEmail());
                return newUser;
            }

            @Override
            protected void onPostExecute(User users) {
                super.onPostExecute(users);
                if(!(users == null)){
                    user = users;
                }
            }
        }
        GetUser getUser = new GetUser();
        getUser.execute();
    }

}
