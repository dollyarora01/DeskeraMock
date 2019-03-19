package com.deskera.mock.views.activities;

import android.os.Bundle;

import com.deskera.mock.interfaces.InteractionListener;
import com.deskera.mock.viewModels.SettingsViewModel;
import com.deskera.mock.views.fragments.SettingsFragment;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.deskera.mock.R;

public class SettingsActivity extends BaseActivity implements InteractionListener<String> {
    @Override
    public int getContentViewId() {
        return R.layout.activity_settings;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.navigation_settings;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    @Override
    public void onInteraction(String value) {

    }
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStackImmediate();
        } else {
            super.onBackPressed();
        }
    }
}
