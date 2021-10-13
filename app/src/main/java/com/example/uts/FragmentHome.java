package com.example.uts;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uts.adapter.DeliveryAdapter;
import com.example.uts.database.DatabaseDelivery;
import com.example.uts.databinding.ActivityLoginBinding;
import com.example.uts.databinding.FragmentHomeBinding;
import com.example.uts.model.Delivery;
import com.example.uts.model.User;

import java.util.ArrayList;
import java.util.List;

public class FragmentHome extends Fragment {

    private RecyclerView rv_delivery;
    private List<Delivery> deliveryList;
    private DeliveryAdapter deliveryAdapter;
    User user;
    FragmentHomeBinding binding;

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
        binding = DataBindingUtil.setContentView(getActivity(), R.layout.fragment_home);
        binding.setUser(user);
        binding.setFragment(this);
        return inflater.inflate(R.layout.fragment_home,container,false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rv_delivery = view.findViewById(R.id.rv_deliveryList);
        rv_delivery.setLayoutManager(new LinearLayoutManager(getActivity()));

        getDelivery();

        deliveryList = new ArrayList<>();
    }

    public View.OnClickListener btnCreate = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent movePage = new Intent(getActivity(), DeliveryForm.class);
            movePage.putExtra("user2",user);
            movePage.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(movePage);
        }
    };

    private void getDelivery(){
        class GetDelivery extends AsyncTask<Void, Void, List<Delivery>> {

            @Override
            protected List<Delivery> doInBackground(Void... voids) {
                List<Delivery> deliveryList = DatabaseDelivery.getInstance(getActivity().getApplicationContext())
                        .getDatabase()
                        .deliveryDao()
                        .getAll(user.id);
                return deliveryList;
            }

            @Override
            protected void onPostExecute(List<Delivery> deliveries) {
                super.onPostExecute(deliveries);
                if(!(deliveries.size() ==0)){
                    deliveryAdapter = new DeliveryAdapter(deliveries, getActivity());
                    rv_delivery.setAdapter(deliveryAdapter);
                }
            }
        }
        GetDelivery getDelivery = new GetDelivery();
        getDelivery.execute();
    }


}
