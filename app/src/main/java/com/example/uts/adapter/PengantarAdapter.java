package com.example.uts.adapter;

import static com.android.volley.Request.Method.DELETE;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.uts.DeliveryForm;
import com.example.uts.FragmentHome;
import com.example.uts.FragmentPengantar;
import com.example.uts.MainActivity;
import com.example.uts.PengantarForm;
import com.example.uts.R;
import com.example.uts.api.PengantarAPI;
import com.example.uts.databinding.AdapterRecyclerViewBinding;
import com.example.uts.model.Delivery;
import com.example.uts.model.Pengantar;
import com.example.uts.model.PengantarResponse;
import com.example.uts.model.PengantarResponseData;
import com.example.uts.model.User;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PengantarAdapter extends RecyclerView.Adapter<PengantarAdapter.ViewHolder> {

    private List<Pengantar> pengantarList;
    private Context context;
    private FragmentPengantar fragmentPengantar;
    private RequestQueue queue;
    User user;

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvNoTelp;
        ImageButton btnDelete;
        CardView cvPengantar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.isiNama);
            tvNoTelp = itemView.findViewById(R.id.isiNoTelp);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            cvPengantar = itemView.findViewById(R.id.cv_pengantar);
        }
    }

    public PengantarAdapter(List<Pengantar> pengantarList, Context context, FragmentPengantar fragmentPengantar, User user) {
        this.pengantarList = pengantarList;
        this.context = context;
        this.fragmentPengantar = fragmentPengantar;
        this.user = user;
        queue = Volley.newRequestQueue(fragmentPengantar.getContext());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PengantarAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.adapter_pengantar, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PengantarAdapter.ViewHolder holder, int position) {
        Pengantar currentPengantar = pengantarList.get(position);
        holder.tvName.setText(currentPengantar.getNama());
        holder.tvNoTelp.setText(currentPengantar.getNoTelp());

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletePengantar(currentPengantar.getId());
            }
        });

        holder.cvPengantar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent update = new Intent(context, PengantarForm.class);
                update.putExtra("dataPengantar",currentPengantar.getId());
                update.putExtra("dataUser",user);
                if(context instanceof MainActivity){
                    ((MainActivity) context).startActivityForResult(update,MainActivity.LAUNCH_ADD_ACTIVITY);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return pengantarList.size();
    }

    // untuk delete
    public void deletePengantar(long id) {

        StringRequest stringRequest = new StringRequest(DELETE,
                PengantarAPI.DELETE_URL + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                PengantarResponseData pengantarResponse =
                        gson.fromJson(response, PengantarResponseData.class);

                Toast.makeText(fragmentPengantar.getActivity(), 
                        pengantarResponse.getMessage(), Toast.LENGTH_SHORT).show();

                fragmentPengantar.getAllPengantar();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                try {
                    String responseBody = new String(error.networkResponse.data,
                            StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);
                    Toast.makeText(fragmentPengantar.getActivity(), 
                            errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(fragmentPengantar.getActivity(), e.getMessage(),
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
