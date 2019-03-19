package com.deskera.mock.views.fragments;

import androidx.appcompat.widget.Toolbar;
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
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.deskera.mock.R;
import com.deskera.mock.entities.Settings;
import com.deskera.mock.entities.TemperatureType;
import com.deskera.mock.interfaces.InteractionListener;
import com.deskera.mock.viewModels.SettingsViewModel;

import java.text.SimpleDateFormat;

public class SettingsFragment extends Fragment implements InteractionListener<String> {

    private SettingsViewModel settingViewModel;
    com.deskera.mock.entities.Settings settings;
    @BindView(R.id.llSettings)
    LinearLayout llSettings;
    Toolbar profileToolbar;
    TextView tvBack;

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_fragment, container, false);
        ButterKnife.bind(this, view);
        profileToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        tvBack = (TextView) profileToolbar.findViewById(R.id.tvBack);
        tvBack.setVisibility(View.GONE);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        settingViewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);
        buildDisplay();
    }

    private void buildTextSettingLayout(View.OnClickListener clickListener, CompoundButton.OnCheckedChangeListener onCheckedChangeListener, boolean isSwitch, String key, String value, boolean switchValue) {
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
            switchSetting.setOnCheckedChangeListener(onCheckedChangeListener);
            relativeLayout.addView(switchSetting, valueParams);
        } else {
            TextView valueTextView = new TextView(new ContextThemeWrapper(getContext(), R.style.SettingValue));
            valueTextView.setText(value);
            relativeLayout.addView(valueTextView, valueParams);
        }
        //VALUE TEXT VIEW SETTINGS END
        if (llSettings == null) {
            llSettings = new LinearLayout(getContext());
            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            llSettings.setLayoutParams(llp);
            llSettings.setOrientation(LinearLayout.VERTICAL);
        }
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
            setSettings(s);
            View.OnClickListener temperatureOnClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment;
                    FragmentTransaction transaction;
                    fragment = new TemperatureTypeFragment();
                    transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.flMasterSetting, fragment, getResources().getString(R.string.fragment_temperature));
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            };
            View.OnClickListener viewDetailsOnClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment;
                    FragmentTransaction transaction;
                    fragment = new DetailsFragment();
                    transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.flMasterSetting, fragment, getResources().getString(R.string.fragment_view_details));
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            };
            CompoundButton.OnCheckedChangeListener soundOnCheckListener = new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    getSettings().setSound(isChecked);
                    settingViewModel.updateSettings(getSettings());
                }
            };
            CompoundButton.OnCheckedChangeListener notificationsOnCheckListener = new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    getSettings().setNotification(isChecked);
                    settingViewModel.updateSettings(getSettings());
                }
            };

            if (llSettings != null && llSettings.getChildCount() == 0) {
                buildTextSettingLayout(temperatureOnClickListener, null, false, "Temperature Display Units", s.getTemperatureType().name(), false);
                buildTextSettingLayout(null, soundOnCheckListener, true, "Sound", null, s.getSound());
                buildBlankSettingLayout();
                buildTextSettingLayout(null, notificationsOnCheckListener, true, "Notifications", null, s.getNotification());
                buildTextSettingLayout(null, null, false, "Date probation ends", s.getProbationDate() != null ? new SimpleDateFormat("dd/MM/yyyy").format(s.getProbationDate()) : "", false);
                buildBlankSettingLayout();
                buildTextSettingLayout(viewDetailsOnClickListener, null, false, "View Details", "", false);
            }
        });
    }

    public void updateTemperature(String temperatureType) {
        getSettings().setTemperatureType(TemperatureType.valueOf(temperatureType));
        settingViewModel.updateSettings(getSettings());
    }


    @Override
    public void onInteraction(String value) {
        updateTemperature(value);
    }

}
