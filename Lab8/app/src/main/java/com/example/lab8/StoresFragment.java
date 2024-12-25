package com.example.lab8;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import androidx.fragment.app.Fragment;

public class StoresFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        ListView listView = view.findViewById(R.id.listView);
        ArrayAdapter<Store> adapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_list_item_1,
                DataProvider.getStores()
        );
        listView.setAdapter(adapter);

        return view;
    }
}
