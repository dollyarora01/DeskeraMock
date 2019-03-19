package com.deskera.mock.adapters;

import com.deskera.mock.views.fragments.CategoryAllFragment;
import com.deskera.mock.views.fragments.CategoryAFragment;
import com.deskera.mock.views.fragments.CategoryBFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class CategoryPagerAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 3;

    public CategoryPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new CategoryAllFragment();
            case 1:
                return new CategoryAFragment();
            case 2:
                return new CategoryBFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "All Categories";
            case 1:
                return "Category A";
            case 2:
                return "Category B";
        }
        return null;
    }
}
