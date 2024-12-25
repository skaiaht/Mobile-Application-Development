package com.example.lab9;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        recyclerView.setAdapter(new ClothingAdapter(DataProvider.getProductsByCategory("Clothing"), this));

        return view;
    }

    @Override
    public void onProductClick(Product product) {
        Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
        intent.putExtra("product", product);
        startActivity(intent);
    }
}