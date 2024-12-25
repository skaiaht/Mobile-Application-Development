package com.example.lab9;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

public class Product implements Parcelable {
    private String name;
    private String description;
    private double price;
    private int imageResourceId;
    private String category;

    public Product(String name, String description, double price, int imageResourceId, String category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageResourceId = imageResourceId;
        this.category = category;
    }

    // Parcelable implementation
    protected Product(Parcel in) {
        name = in.readString();
        description = in.readString();
        price = in.readDouble();
        imageResourceId = in.readInt();
        category = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeDouble(price);
        dest.writeInt(imageResourceId);
        dest.writeString(category);
    }

    // Getters
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public int getImageResourceId() { return imageResourceId; }
    public String getCategory() { return category; }
}
