package com.deskera.mock.views.fragments;

import com.deskera.mock.entities.Item;

import java.util.List;

import rx.Subscriber;

public class CategoryAllFragment extends BaseItemFragment {

    public CategoryAllFragment() {
    }

    @Override
    public void getItemDetails(Long userId) {
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
}
