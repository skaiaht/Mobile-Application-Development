// EquipmentAdapter.java
package com.example.lab10;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EquipmentAdapter extends RecyclerView.Adapter<EquipmentAdapter.ViewHolder> {
    private final Product[] products;
    private final OnProductClickListener listener;

    public EquipmentAdapter(Product[] products, OnProductClickListener listener) {
        this.products = products;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_equipment_grid, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
        holder.bind(products[position]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return products.length;
    }

    public interface OnProductClickListener {
        void onProductClick(Product product);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView productImage;
        private final TextView productName;
        private final TextView productPrice;

        ViewHolder(View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onProductClick(products[position]);
                }
            });
        }

        void bind(Product product) {
            productImage.setImageResource(product.getImageResourceId());
            productName.setText(product.getName());
            productPrice.setText(String.format("$%.2f", product.getPrice()));
        }
    }
}
