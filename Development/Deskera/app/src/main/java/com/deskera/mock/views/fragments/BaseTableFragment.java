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
public class BaseTableFragment extends Fragment {
    @BindView(R.id.flMasterTable)
    FrameLayout flMasterTable;

    public BaseTableFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_table_base, container, false);
        ButterKnife.bind(this, view);
        Fragment fragment;
        FragmentTransaction transaction;
        fragment = new TableFragment();
        transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.flMasterTable, fragment, getResources().getString(R.string.title_table));
        transaction.addToBackStack(null);
        transaction.commit();
        return view;
    }

}
