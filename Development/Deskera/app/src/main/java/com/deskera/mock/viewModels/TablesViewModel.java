package com.deskera.mock.viewModels;

import com.deskera.mock.DeskeraMockApplication;
import com.deskera.mock.entities.Table;
import com.deskera.mock.entities.TableDao;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import rx.Observable;

public class TablesViewModel extends ViewModel {
    private MutableLiveData<Table> items;

    public Observable<List<Table>> getTables(Long userId) {
        return Observable.just(
                DeskeraMockApplication.getDaoSession().queryBuilder(Table.class)
                        .where(TableDao.Properties.UserId.eq(userId))
                        .list());
    }

    public Long insertTable(Table table) {
        if (table != null && table.getName() != null && !table.getName().isEmpty() && table.getUserId() != null) {
            try {
                TableDao tableDao = DeskeraMockApplication.getDaoSession().getTableDao();
                return tableDao.insert(table);
            } catch (Exception e) {
                return 0L;
            }
        }
        return 0L;
    }

    public boolean deleteTable(Table table) {
        if (table != null && table.getTableId() != null && table.getTableId() > 0L) {
            try {
                DeskeraMockApplication.getDaoSession().queryBuilder(Table.class).
                        where(TableDao.Properties.TableId.eq(table.getTableId())).buildDelete().executeDeleteWithoutDetachingEntities();
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    public boolean updateTable(Table table) {
        if (table != null && table.getTableId() != null && table.getUserId() != null && table.getName() != null &&
                !table.getName().isEmpty()) {
            try {
                DeskeraMockApplication.getDaoSession().getTableDao().update(table);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }
}
