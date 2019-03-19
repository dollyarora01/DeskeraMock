package com.deskera.mock.views.fragments;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import rx.Subscriber;

import com.deskera.mock.R;
import com.deskera.mock.adapters.ItemsAdapter;
import com.deskera.mock.entities.Item;
import com.deskera.mock.viewModels.ItemsViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryBFragment extends BaseItemFragment {

    public CategoryBFragment() {
    }

    @Override
    public void getItemDetails(Long userId) {
        itemsViewModel.getItemsByCategory(userId, 2L).subscribe(new Subscriber<List<Item>>() {
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
