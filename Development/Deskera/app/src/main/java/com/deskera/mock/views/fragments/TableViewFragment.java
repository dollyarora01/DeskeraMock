package com.deskera.mock.views.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.deskera.mock.DeskeraMockApplication;
import com.deskera.mock.R;
import com.deskera.mock.entities.Table;
import com.deskera.mock.interfaces.InteractionListener;
import com.deskera.mock.viewModels.SettingsViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class TableViewFragment extends Fragment {

    //region Controls
    Toolbar profileToolbar;
    TextView tvBack;
    TextView tvTableSave;
    @BindView(R.id.etTableItem)
    EditText etTableItem;
    Table table;
    ImageView ivAddTable;
    TextView tvEditDone;
    private InteractionListener<Table> mListener;

    //endregion
    public TableViewFragment() {
        // Required empty public constructor
    }

    //region Events
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_table_view, container, false);
        ButterKnife.bind(this, view);
        if (getArguments() != null) {
            table = getArguments().getParcelable(getResources().getString(R.string.title_table));
            if (table != null)
                etTableItem.setText(table.getName());
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupToolBar();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof InteractionListener) {
            mListener = (InteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    //endregion

    private void setupToolBar() {
        profileToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        tvBack = (TextView) profileToolbar.findViewById(R.id.tvBack);
        tvTableSave = (TextView) profileToolbar.findViewById(R.id.tvTableSave);
        ivAddTable = (ImageView) profileToolbar.findViewById(R.id.ivAddTable);
        tvEditDone = (TextView) profileToolbar.findViewById(R.id.tvEditDone);

        tvBack.setVisibility(View.VISIBLE);
        tvEditDone.setVisibility(View.GONE);
        tvTableSave.setVisibility(View.VISIBLE);
        ivAddTable.setVisibility(View.GONE);

        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getFragmentManager().getBackStackEntryCount() > 0) {
                    getFragmentManager().popBackStackImmediate();
                } else {
                    getActivity().onBackPressed();
                }
            }
        });

        tvTableSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    Fragment fragment = getFragmentManager().findFragmentByTag(getContext().
                            getResources().getString(R.string.title_table));
                    if (etTableItem != null && !etTableItem.getText().toString().isEmpty()) {
                        if (table != null) {
                            table.setName(etTableItem.getText().toString());
                            ((TableFragment) fragment).onInteraction(table);
                            Toast.makeText(DeskeraMockApplication.getContext(), "Table updated", Toast.LENGTH_SHORT).show();
                            if (getFragmentManager().getBackStackEntryCount() > 0) {
                                getFragmentManager().popBackStackImmediate();
                            } else {
                                getActivity().onBackPressed();
                            }
                        }
                    } else {
                        etTableItem.setError("Please enter valid table item");
                        etTableItem.requestFocus();
                    }
                }
            }
        });
    }

}
