package com.deskera.mock.views.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.deskera.mock.R;
import com.google.android.material.appbar.AppBarLayout;

public class FavouritesActivity  extends BaseActivity  {

    @Override
    public int getContentViewId() {
        return R.layout.activity_favourites;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.navigation_favourites;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}
