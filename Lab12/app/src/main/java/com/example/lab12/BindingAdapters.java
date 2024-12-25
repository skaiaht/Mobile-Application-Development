package com.example.lab12;

import android.widget.ImageView;
import androidx.databinding.BindingAdapter;

public class BindingAdapters {
    @BindingAdapter("imageResource")
    public static void setImageResource(ImageView imageView, int resource) {
        if (resource != 0) {
            imageView.setImageResource(resource);
        }
    }
}
