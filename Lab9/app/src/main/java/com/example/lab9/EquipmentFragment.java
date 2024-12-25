package com.example.lab9;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        recyclerView.setAdapter(new EquipmentAdapter(DataProvider.getProductsByCategory("Equipment"), this));

        return view;
    }

    @Override
    public void onProductClick(Product product) {
        Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
        intent.putExtra("product", product);
        startActivity(intent);
    }
}