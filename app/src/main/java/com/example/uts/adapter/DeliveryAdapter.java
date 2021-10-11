package com.example.uts.adapter;

import android.content.ClipData;
import android.content.Context;
import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uts.R;
import com.example.uts.databinding.AdapterRecyclerViewBinding;
import com.example.uts.model.Delivery;

import java.util.List;

public class DeliveryAdapter extends RecyclerView.Adapter<DeliveryAdapter.ViewHolder> {
    private List<Delivery> deliveryList;
    private Context context;


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

    public DeliveryAdapter(List<Delivery> deliveryList, Context context) {
        this.deliveryList = deliveryList;
        this.context = context;
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
    }

    @Override
    public int getItemCount() {
        return deliveryList.size();
    }
}
