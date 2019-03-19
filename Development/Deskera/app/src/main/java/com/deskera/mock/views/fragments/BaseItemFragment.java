package com.deskera.mock.views.fragments;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deskera.mock.DeskeraMockApplication;
import com.deskera.mock.R;
import com.deskera.mock.adapters.ItemsAdapter;
import com.deskera.mock.entities.Item;
import com.deskera.mock.interfaces.OnItemClickListener;
import com.deskera.mock.viewModels.ItemsViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseItemFragment extends Fragment {
    //region Controls
    @BindView(R.id.rvItems)
    RecyclerView rvItems;
    ItemsAdapter itemsAdapter;
    protected Toolbar profileToolbar;
    protected ItemsViewModel itemsViewModel;

    //end region
    public BaseItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_category, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        itemsViewModel = ViewModelProviders.of(this).get(ItemsViewModel.class);
        profileToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        getItemDetails(1L);

    }

    //region Functions
    public abstract void getItemDetails(Long userId);

    public void setDisplay(List<Item> items) {
        rvItems.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(DeskeraMockApplication.getContext());
        rvItems.setLayoutManager(layoutManager);
        itemsAdapter = new ItemsAdapter(items, new OnItemClickListener<Item>() {
            @Override
            public void onItemClick(Item item) {
                item.setIsFavourite(!item.getIsFavourite());
                itemsViewModel.updateItem(item);
            }
        });
        rvItems.setAdapter(itemsAdapter);
    }

    //end region
}
