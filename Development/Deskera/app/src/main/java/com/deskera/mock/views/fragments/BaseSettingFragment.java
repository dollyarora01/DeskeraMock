package com.deskera.mock.views.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.deskera.mock.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseSettingFragment extends Fragment {

    @BindView(R.id.flMasterSetting)
    FrameLayout flMasterSetting;

    public BaseSettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_base_setting_fragement, container, false);
        ButterKnife.bind(this, view);
        Fragment fragment;
        FragmentTransaction transaction;
        fragment = new SettingsFragment();
        transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.flMasterSetting, fragment, getResources().getString(R.string.title_settings));
        transaction.addToBackStack(null);
        transaction.commit();
        return view;
    }

}
