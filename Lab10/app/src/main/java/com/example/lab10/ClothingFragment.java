package com.example.lab10;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ClothingFragment extends Fragment implements ClothingAdapter.OnProductClickListener {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clothing, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Create sample data
        Product[] products = {
                new Product("Ski Jacket", "Waterproof insulated jacket", 249.99, R.drawable.ic_launcher_background, "Clothing"),
                new Product("Ski Pants", "Breathable snow pants", 179.99, R.drawable.ic_launcher_background, "Clothing"),
                new Product("Base Layer Top", "Thermal compression top", 49.99, R.drawable.ic_launcher_background, "Clothing"),
                new Product("Base Layer Bottom", "Moisture-wicking thermal bottoms", 44.99, R.drawable.ic_launcher_background, "Clothing"),
                new Product("Ski Socks", "Merino wool blend socks", 24.99, R.drawable.ic_launcher_background, "Clothing"),
                new Product("Gloves", "Waterproof insulated gloves", 69.99, R.drawable.ic_launcher_background, "Clothing"),
                new Product("Neck Gaiter", "Fleece neck warmer", 19.99, R.drawable.ic_launcher_background, "Clothing"),
                new Product("Beanie", "Warm winter hat", 29.99, R.drawable.ic_launcher_background, "Clothing"),
                new Product("Mid Layer", "Fleece jacket", 89.99, R.drawable.ic_launcher_background, "Clothing"),
                new Product("Snow Boots", "Après-ski boots", 149.99, R.drawable.ic_launcher_background, "Clothing")
        };

        ClothingAdapter adapter = new ClothingAdapter(products, this);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onProductClick(Product product) {
        try {
            if (getContext() != null) {
                Toast.makeText(getContext(), "Selected: " + product.getName(), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}