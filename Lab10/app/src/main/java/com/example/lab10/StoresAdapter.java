// StoresAdapter.java
package com.example.lab10;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StoresAdapter extends RecyclerView.Adapter<StoresAdapter.ViewHolder> {
    private final Store[] stores;

    public StoresAdapter(Store[] stores) {
        this.stores = stores;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_store_carousel, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
        holder.bind(stores[position]);
    } catch (Exception e) {
        e.printStackTrace();
    }
    }

    @Override
    public int getItemCount() {
        return stores.length;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView storeName;
        private final TextView storeAddress;
        private final TextView storePhone;
        private final TextView storeHours;

        ViewHolder(View itemView) {
            super(itemView);
            storeName = itemView.findViewById(R.id.storeName);
            storeAddress = itemView.findViewById(R.id.storeAddress);
            storePhone = itemView.findViewById(R.id.storePhone);
            storeHours = itemView.findViewById(R.id.storeHours);
        }

        void bind(Store store) {
            storeName.setText(store.getName());
            storeAddress.setText(store.getAddress());
            storePhone.setText(store.getPhone());
            storeHours.setText(store.getWorkingHours());
        }
    }
}