// ClothingAdapter.java
package com.example.lab10;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClothingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_PRODUCT = 0;
    private static final int TYPE_AD = 1;
    private static final int AD_FREQUENCY = 4; // Show ad after every 3 items

    private final List<Product> products;
    private final OnProductClickListener listener;

    public ClothingAdapter(Product[] products, OnProductClickListener listener) {
        this.products = new ArrayList<>(Arrays.asList(products));
        this.listener = listener;
        insertAds();
    }

    private void insertAds() {
        for (int i = AD_FREQUENCY - 1; i < products.size(); i += AD_FREQUENCY) {
            products.add(i, null); // null represents ad position
        }
    }

    @Override
    public int getItemViewType(int position) {
        return products.get(position) == null ? TYPE_AD : TYPE_PRODUCT;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_AD) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_ad, parent, false);
            return new AdViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_clothing_linear, parent, false);
            return new ProductViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
        if (getItemViewType(position) == TYPE_PRODUCT) {
            ((ProductViewHolder) holder).bind(products.get(position));
        } else {
            ((AdViewHolder) holder).bind();
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public interface OnProductClickListener {
        void onProductClick(Product product);
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        private final ImageView productImage;
        private final TextView productName;
        private final TextView productPrice;
        private final TextView productDescription;

        ProductViewHolder(View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productDescription = itemView.findViewById(R.id.productDescription);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && products.get(position) != null) {
                    listener.onProductClick(products.get(position));
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

    class AdViewHolder extends RecyclerView.ViewHolder {
        private final ImageView adImage;
        private final TextView adText;

        AdViewHolder(View itemView) {
            super(itemView);
            adImage = itemView.findViewById(R.id.adImage);
            adText = itemView.findViewById(R.id.adText);
        }

        void bind() {
            adImage.setImageResource(R.drawable.ad_placeholder);
            adText.setText("Special Offer!");
        }
    }
}