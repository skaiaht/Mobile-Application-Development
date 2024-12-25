// ClothingAdapter.java
package com.example.lab9;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ClothingAdapter extends RecyclerView.Adapter<ClothingAdapter.ViewHolder> {
    private final Product[] products;
    private final OnProductClickListener listener;

    public interface OnProductClickListener {
        void onProductClick(Product product);
    }

    public ClothingAdapter(Product[] products, OnProductClickListener listener) {
        this.products = products;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_clothing_linear, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(products[position]);
    }

    @Override
    public int getItemCount() {
        return products.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView productImage;
        private final TextView productName;
        private final TextView productPrice;
        private final TextView productDescription;

        ViewHolder(View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productDescription = itemView.findViewById(R.id.productDescription);

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
            productDescription.setText(product.getDescription());
        }
    }
}