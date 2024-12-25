package com.example.lab10;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;

public class StoresFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stores, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        // Using GravitySnapHelper for carousel effect
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        GravitySnapHelper snapHelper = new GravitySnapHelper(Gravity.START);
        snapHelper.attachToRecyclerView(recyclerView);

        recyclerView.setAdapter(new StoresAdapter(DataProvider.getStores()));

        return view;
    }
}