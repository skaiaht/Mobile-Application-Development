package com.example.lab10;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class EquipmentFragment extends Fragment implements EquipmentAdapter.OnProductClickListener {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_equipment, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Create sample data
        Product[] products = {
                new Product("Alpine Skis", "High-performance all-mountain skis", 599.99, R.drawable.ic_launcher_background, "Equipment"),
                new Product("Ski Boots", "Comfortable and precise fit boots", 299.99, R.drawable.ic_launcher_background, "Equipment"),
                new Product("Ski Poles", "Lightweight aluminum poles", 49.99, R.drawable.ic_launcher_background, "Equipment"),
                new Product("Bindings", "Safety-first ski bindings", 199.99, R.drawable.ic_launcher_background, "Equipment"),
                new Product("Helmet", "Advanced protection helmet", 129.99, R.drawable.ic_launcher_background, "Equipment"),
                new Product("Goggles", "Anti-fog ski goggles", 89.99, R.drawable.ic_launcher_background, "Equipment"),
                new Product("Ski Bag", "Padded ski travel bag", 79.99, R.drawable.ic_launcher_background, "Equipment"),
                new Product("Boot Bag", "Ventilated boot bag", 39.99, R.drawable.ic_launcher_background, "Equipment"),
                new Product("Wax Kit", "Complete ski wax kit", 29.99, R.drawable.ic_launcher_background, "Equipment"),
                new Product("Edge Tool", "Professional edge tuning tool", 24.99, R.drawable.ic_launcher_background, "Equipment")
        };

        EquipmentAdapter adapter = new EquipmentAdapter(products, this);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onProductClick(Product product) {
        try {
            // Handle click - for now just show a toast
            if (getContext() != null) {
                Toast.makeText(getContext(), "Selected: " + product.getName(), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}