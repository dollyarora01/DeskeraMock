package com.deskera.mock.views.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.deskera.mock.R;
import com.deskera.mock.entities.Table;
import com.deskera.mock.interfaces.InteractionListener;
import com.google.android.material.appbar.AppBarLayout;

public class TableActivity extends BaseActivity implements InteractionListener<Table> {

    @Override
    public int getContentViewId() {
        return R.layout.tables_toolbar;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.navigation_table;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    @Override
    public void onInteraction(Table value) {

    }
}
