package com.example.lab8;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import androidx.fragment.app.Fragment;

public class ProductListFragment extends Fragment {
    private String category;
    private Product[] products;

    public static ProductListFragment newInstance(String category) {
        ProductListFragment fragment = new ProductListFragment();
        Bundle args = new Bundle();
        args.putString("category", category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        category = getArguments().getString("category");
        products = DataProvider.getProductsByCategory(category);

        ListView listView = view.findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_list_item_1,
                getProductNames()
        );
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
            intent.putExtra("category", category);
            intent.putExtra("position", position);
            startActivity(intent);
        });

        return view;
    }

    private String[] getProductNames() {
        String[] names = new String[products.length];
        for (int i = 0; i < products.length; i++) {
            names[i] = products[i].getName();
        }
        return names;
    }
}
