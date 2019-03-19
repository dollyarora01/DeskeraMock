package com.deskera.mock.viewModels;

import com.deskera.mock.DeskeraMockApplication;
import com.deskera.mock.entities.Item;
import com.deskera.mock.entities.ItemDao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import rx.Observable;

public class ItemsViewModel extends ViewModel {
    private MutableLiveData<Item> items;

    public Observable<List<Item>> getItems(Long userId) {
        return Observable.just(
                DeskeraMockApplication.getDaoSession().queryBuilder(Item.class)
                        .where(ItemDao.Properties.UserId.eq(userId))
                        .list());
    }

    public Observable<List<Item>> getItemsByCategory(Long userId, Long categoryId) {
        return Observable.just(
                DeskeraMockApplication.getDaoSession().queryBuilder(Item.class)
                        .where(ItemDao.Properties.UserId.eq(userId))
                        .where(ItemDao.Properties.CategoryId.eq(categoryId))
                        .list());
    }

    public Observable<List<Item>> getFavouriteItems(Long userId) {
        return Observable.just(
                DeskeraMockApplication.getDaoSession().queryBuilder(Item.class)
                        .where(ItemDao.Properties.UserId.eq(userId))
                        .where(ItemDao.Properties.IsFavourite.eq(true))
                        .list());
    }

    public boolean updateItem(Item item) {
        if (item != null && item.getItemName() != null && !item.getItemName().isEmpty()
                && item.getCategoryId() != 0L && item.getUserId() != null && item.getIsFavourite() != null) {
            try {
                DeskeraMockApplication.getDaoSession().getItemDao().update(item);
            } catch (Exception e) {
                return false;
            }
            return true;
        } else
            return false;
    }
}
