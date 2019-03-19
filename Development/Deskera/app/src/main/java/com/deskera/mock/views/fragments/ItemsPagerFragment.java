package com.deskera.mock.views.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.deskera.mock.R;
import com.deskera.mock.adapters.CategoryPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemsPagerFragment extends Fragment {
    FragmentPagerAdapter adapter;
    @BindView(R.id.vpPager)
    ViewPager vpPager;

    public ItemsPagerFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_items, container, false);
        ButterKnife.bind(this, view);
        adapter = new CategoryPagerAdapter(getFragmentManager());
        vpPager.setAdapter(adapter);
        return view;
    }
}
