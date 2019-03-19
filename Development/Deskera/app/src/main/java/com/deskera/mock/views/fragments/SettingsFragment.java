package com.deskera.mock.views.fragments;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.deskera.mock.R;
import com.deskera.mock.interfaces.InteractionListener;
import com.deskera.mock.viewModels.SettingsViewModel;

public class Settings extends Fragment implements InteractionListener<String> {

    @BindView(R.id.flSettings)
    FrameLayout flSettings;
    @BindView(R.id.llSettings)
    LinearLayout llSettings;
    private SettingsViewModel settingViewModel;
    com.deskera.mock.entities.Settings settings;

    public static Settings newInstance() {
        return new Settings();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        settingViewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);
        buildDisplay();
    }

    private void buildTextSettingLayout(View.OnClickListener clickListener, boolean isSwitch, String key, String value, boolean switchValue) {
        RelativeLayout relativeLayout = new RelativeLayout(new ContextThemeWrapper(getContext(), R.style.SettingRow));
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
        relativeLayout.setLayoutParams(rlp);

        //KEY TEXT VIEW SETTINGS START
        RelativeLayout.LayoutParams keyParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        keyParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        if (clickListener != null) {
            relativeLayout.setOnClickListener(clickListener);
        }
        TextView keyTextView = new TextView(new ContextThemeWrapper(getContext(), R.style.SettingKey));
        keyTextView.setText(key);
        relativeLayout.addView(keyTextView, keyParams);
        //KEY TEXT VIEW SETTINGS END

        //VALUE TEXT VIEW SETTINGS START
        RelativeLayout.LayoutParams valueParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        valueParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        if (isSwitch) {
            Switch switchSetting = new Switch(getContext());
            switchSetting.setChecked(switchValue);
            relativeLayout.addView(switchSetting, valueParams);
        } else {
            TextView valueTextView = new TextView(new ContextThemeWrapper(getContext(), R.style.SettingValue));
            valueTextView.setText(value);
            relativeLayout.addView(valueTextView, valueParams);
        }
        //VALUE TEXT VIEW SETTINGS END

        llSettings.addView(relativeLayout, rlp);
    }

    private void buildBlankSettingLayout() {
        RelativeLayout relativeLayout = new RelativeLayout(new ContextThemeWrapper(getContext(), R.style.BlankSettingRow));
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
        relativeLayout.setLayoutParams(rlp);


        llSettings.addView(relativeLayout, rlp);
    }

    private void buildDisplay() {
        settingViewModel.getSettings(1L).observe(this, s -> {
            settings=s;
            View.OnClickListener temperatureOnClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment;
                    FragmentTransaction transaction;
                    fragment = new TemperatureType();
                    transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.flSettings, fragment, getResources().getString(R.string.fragment_temperature));
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            };
            View.OnClickListener viewDetailsOnClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment;
                    FragmentTransaction transaction;
                    fragment = new TemperatureType();
                    transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.flSettings, fragment, getResources().getString(R.string.fragment_view_details));
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            };
            buildTextSettingLayout(temperatureOnClickListener, false, "Temperature Display Units", s.getTemperatureType().name(), false);
            buildTextSettingLayout(null, true, "Sound", null, s.getSound());
            buildBlankSettingLayout();
            buildTextSettingLayout(null, true, "Notifications", null, s.getNotification());
            buildTextSettingLayout(null, false, "Date probation ends", s.getProbationDate() != null ? s.getProbationDate().toString() : "", false);
            buildBlankSettingLayout();
            buildTextSettingLayout(viewDetailsOnClickListener, false, "View Details", "", false);
        });
    }

    @Override
    public void onInteraction(String value) {
        settings.setTemperatureType(com.deskera.mock.entities.TemperatureType.valueOf(value));
        settingViewModel.updateSettings(settings);
    }
}
