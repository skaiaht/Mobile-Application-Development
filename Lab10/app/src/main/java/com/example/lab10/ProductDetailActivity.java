package com.example.lab10;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProductDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        String category = getIntent().getStringExtra("category");
        int position = getIntent().getIntExtra("position", 0);
        Product product = DataProvider.getProductsByCategory(category)[position];

        ImageView productImage = findViewById(R.id.productImage);
        TextView productName = findViewById(R.id.productName);
        TextView productDescription = findViewById(R.id.productDescription);
        TextView productPrice = findViewById(R.id.productPrice);

        productImage.setImageResource(product.getImageResourceId());
        productName.setText(product.getName());
        productDescription.setText(product.getDescription());
        productPrice.setText(String.format("$%.2f", product.getPrice()));
    }
}
