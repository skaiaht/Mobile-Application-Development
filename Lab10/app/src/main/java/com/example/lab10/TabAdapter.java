package com.example.lab10;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TabAdapter extends FragmentStateAdapter {
    private static final int TAB_COUNT = 3;

    public TabAdapter(FragmentActivity activity) {
        super(activity);
    }

    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new EquipmentFragment();
            case 1:
                return new ClothingFragment();
            case 2:
                return new StoresFragment();
            default:
                return new EquipmentFragment();
        }
    }

    @Override
    public int getItemCount() {
        return TAB_COUNT;
    }
}
