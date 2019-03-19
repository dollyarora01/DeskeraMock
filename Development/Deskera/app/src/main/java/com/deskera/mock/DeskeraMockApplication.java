package com.deskera.mock;

import android.app.Application;
import android.content.Context;

import com.deskera.mock.entities.Category;
import com.deskera.mock.entities.DaoMaster;
import com.deskera.mock.entities.DaoSession;
import com.deskera.mock.entities.Item;
import com.deskera.mock.entities.Settings;
import com.deskera.mock.entities.Table;
import com.deskera.mock.entities.TemperatureType;
import com.deskera.mock.entities.User;

import java.util.Date;
import java.util.logging.SimpleFormatter;

public class DeskeraMockApplication extends Application {
    private static DaoSession mDaoSession;
    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DeskeraMockApplication.context = getApplicationContext();
        mDaoSession = new DaoMaster(
                new DaoMaster.DevOpenHelper(this, "deskera_mock.db").getWritableDb()).newSession();

//        mDaoSession.deleteAll(User.class);
//        mDaoSession.deleteAll(Settings.class);
//        mDaoSession.deleteAll(Item.class);
//        mDaoSession.deleteAll(Table.class);
        // USER CREATION FOR DEMO PURPOSE
        if (mDaoSession.getUserDao().loadAll().size() == 0) {
            User user = new User(1L, "dolly.arora", "dolly.arora01@gmail.com", "Exploration, Travelling");
            user.setSettings(new Settings(1L, true, TemperatureType.Celsius, 32, false, 1520275402000L, 1551811402000L));
            mDaoSession.getUserDao().insert(user);
        }
        if (mDaoSession.getSettingsDao().loadAll().size() == 0) {
            mDaoSession.getSettingsDao().insert(new Settings(1L, true, TemperatureType.Celsius, 32, false, 1520275402000L, 1551811402000L));
        }
        if (mDaoSession.getCategoryDao().loadAll().size() == 0) {
            mDaoSession.getCategoryDao().insert(new Category(1L, "Category A"));
            mDaoSession.getCategoryDao().insert(new Category(2L, "Category B"));
            mDaoSession.getCategoryDao().insert(new Category(3L, "Category C"));
        }
        if (mDaoSession.getItemDao().loadAll().size() == 0) {
            mDaoSession.getItemDao().insert(new Item(100L, 1L, "The best 32 inch 4K TV", true, "Samsung TV", 1L));
            mDaoSession.getItemDao().insert(new Item(200L, 1L, "42 inch 4K TV", false, "Micromax", 1L));
            mDaoSession.getItemDao().insert(new Item(300L, 1L, "The best Soundbar", true, "Philips", 1L));
            mDaoSession.getItemDao().insert(new Item(400L, 1L, "Fastest Fan in 900mm", false, "Orient", 2L));
            mDaoSession.getItemDao().insert(new Item(500L, 1L, "5Liters storage purifier ", false, "Aqua Guard", 2L));
        }
        if (mDaoSession.getTableDao().loadAll().size() == 0) {
            mDaoSession.getTableDao().insert(new Table(100L, 1L, "Grapes"));
            mDaoSession.getTableDao().insert(new Table(200L, 1L, "Banana"));
            mDaoSession.getTableDao().insert(new Table(300L, 1L, "Pineapple"));
            mDaoSession.getTableDao().insert(new Table(400L, 1L, "Coconut"));
            mDaoSession.getTableDao().insert(new Table(500L, 1L, "Cranberry"));
            mDaoSession.getTableDao().insert(new Table(600L, 1L, "Pear"));
            mDaoSession.getTableDao().insert(new Table(700L, 1L, "Plum"));
            mDaoSession.getTableDao().insert(new Table(800L, 1L, "Pomegranate"));
            mDaoSession.getTableDao().insert(new Table(900L, 1L, "Peach"));
            mDaoSession.getTableDao().insert(new Table(1000L, 1L, "Soursop"));
            mDaoSession.getTableDao().insert(new Table(1100L, 1L, "Passionfruit"));
            mDaoSession.getTableDao().insert(new Table(1200L, 1L, "Jackfruit"));
            mDaoSession.getTableDao().insert(new Table(1400L, 1L, "Cloudberry"));
        }
    }

    public static DaoSession getDaoSession() {
        return mDaoSession;
    }

}
