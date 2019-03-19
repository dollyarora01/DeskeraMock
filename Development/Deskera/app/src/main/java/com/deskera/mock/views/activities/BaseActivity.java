package com.deskera.mock.views.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.deskera.mock.DeskeraMockApplication;
import com.deskera.mock.R;
import com.deskera.mock.views.fragments.ProfileFragment;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public abstract class BaseActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView navigation;
    public AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        if (appBarLayout != null)
            appBarLayout.addOnOffsetChangedListener(this::onOffsetChanged);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationBarState();
    }

    private void updateNavigationBarState() {
        int actionId = getNavigationMenuItemId();
        selectBottomNavigationBarItem(actionId);
    }

    void selectBottomNavigationBarItem(int itemId) {
        Menu menu = navigation.getMenu();
        for (int i = 0, size = menu.size(); i < size; i++) {
            MenuItem item = menu.getItem(i);
            boolean shouldBeChecked = item.getItemId() == itemId;
            if (shouldBeChecked) {
                item.setChecked(true);
                break;
            }
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Intent intent;
            switch (item.getItemId()) {
                case R.id.navigation_profile:
                    intent = new Intent(DeskeraMockApplication.getContext(), ProfileActivity.class);
                    startActivity(intent);

                    return true;
                case R.id.navigation_items:
                    intent = new Intent(DeskeraMockApplication.getContext(), ItemsActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_favourites:
                    intent = new Intent(DeskeraMockApplication.getContext(), FavouritesActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_table:
                    intent = new Intent(DeskeraMockApplication.getContext(), TableActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_settings:
                    intent = new Intent(DeskeraMockApplication.getContext(), SettingsActivity.class);
                    startActivity(intent);
                    return true;
            }
            return false;
        }
    };

    public abstract int getContentViewId();

    public abstract int getNavigationMenuItemId();

    public abstract void onOffsetChanged(AppBarLayout appBarLayout, int offset);
}