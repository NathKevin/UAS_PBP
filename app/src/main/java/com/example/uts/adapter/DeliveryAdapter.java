package com.example.uts.adapter;

import static com.android.volley.Request.Method.DELETE;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.uts.DeliveryForm;
import com.example.uts.FragmentHome;
import com.example.uts.MainActivity;
import com.example.uts.R;
import com.example.uts.api.DeliveryAPI;
import com.example.uts.databinding.AdapterRecyclerViewBinding;
import com.example.uts.model.Delivery;
import com.example.uts.model.DeliveryResponseData;
import com.example.uts.model.User;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeliveryAdapter extends RecyclerView.Adapter<DeliveryAdapter.ViewHolder> {
    private List<Delivery> deliveryList;
    private Context context;
    private FragmentHome fragmentHome;
    private RequestQueue queue;
    User user;


    public class ViewHolder extends RecyclerView.ViewHolder{
        private AdapterRecyclerViewBinding binding;

        public ViewHolder( AdapterRecyclerViewBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }

        public void bind(Delivery delivery){
            binding.setDeliver(delivery);
            binding.executePendingBindings();
        }
    }

    public DeliveryAdapter(List<Delivery> deliveryList, Context context, FragmentHome fragmentHome, User user) {
        this.deliveryList = deliveryList;
        this.context = context;
        this.fragmentHome = fragmentHome;
        this.user = user;
        queue = Volley.newRequestQueue(fragmentHome.getContext());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterRecyclerViewBinding adapterRecyclerViewBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.adapter_recycler_view,parent,false);
        return new ViewHolder(adapterRecyclerViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Delivery currentDelivery = deliveryList.get(position);
        holder.binding.setDeliver(currentDelivery);
        holder.binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialAlertDialogBuilder materialAlertDialogBuilder =
                        new MaterialAlertDialogBuilder(context);
                materialAlertDialogBuilder.setTitle("Konfirmasi")
                        .setMessage("Apakah anda yakin ingin menghapus data mahasiswa ini?")
                        .setNegativeButton("Batal", null)
                        .setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteDelivery(currentDelivery.getIdDelivery(), currentDelivery.getIdUser());
                            }
                        })
                        .show();
            }
        });
        holder.binding.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent update = new Intent(context, DeliveryForm.class);
                update.putExtra("dataDelivery",currentDelivery.getIdDelivery());
                update.putExtra("dataUser", user);
                if(context instanceof MainActivity){
                    ((MainActivity) context).startActivityForResult(update,MainActivity.LAUNCH_ADD_ACTIVITY);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return deliveryList.size();
    }

    private void deleteDelivery(int id_deliver, int id_user){
        StringRequest stringRequest = new StringRequest(DELETE, DeliveryAPI.DELETE_URL + id_deliver, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                DeliveryResponseData deliveryResponse =
                        gson.fromJson(response, DeliveryResponseData.class);
                Toast.makeText(fragmentHome.getActivity(), 
                        deliveryResponse.getMessage(), Toast.LENGTH_SHORT).show();
                fragmentHome.getAllDelivery(id_user);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    String responseBody = new String(error.networkResponse.data,
                            StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);
                    Toast.makeText(fragmentHome.getActivity(), 
                            errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(fragmentHome.getActivity(), e.getMessage(),
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

}
