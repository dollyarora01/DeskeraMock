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
public class AllCategoryFragment extends Fragment {

    @BindView(R.id.rvItems)
    RecyclerView rvItems;
    ItemsAdapter itemsAdapter;
    private ItemsViewModel itemsViewModel;
    Toolbar profileToolbar;

    public AllCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_category, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        itemsViewModel = ViewModelProviders.of(this).get(ItemsViewModel.class);
        getItemDetails(1L);
        profileToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
    }

    private void getItemDetails(Long userId) {
        itemsViewModel.getItems(userId).subscribe(new Subscriber<List<Item>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Item> itemList) {
                setDisplay(itemList);
            }
        });
    }

    private void setDisplay(List<Item> items) {
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
}
