package com.deskera.mock.views.activities;

import androidx.annotation.NonNull;

import android.view.MenuItem;
import com.deskera.mock.R;
import com.google.android.material.appbar.AppBarLayout;

public class ItemsActivity extends BaseActivity {

    @Override
    public int getContentViewId() {
        return R.layout.activity_items;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.navigation_items;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}
