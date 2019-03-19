package com.deskera.mock.views.fragments;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.deskera.mock.R;
import com.deskera.mock.entities.Settings;
import com.deskera.mock.viewModels.SettingsViewModel;

import java.text.SimpleDateFormat;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {

    private SettingsViewModel settingViewModel;
    com.deskera.mock.entities.Settings settings;
    @BindView(R.id.llViewDetails)
    LinearLayout llViewDetails;
    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
    Toolbar profileToolbar;
    TextView tvBack;

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.bind(this, view);
        profileToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        tvBack = (TextView) profileToolbar.findViewById(R.id.tvBack);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        settingViewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);
        buildDisplay();
        setupToolBar();
    }

    private void buildTextSettingLayout(String key, String value) {
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

        TextView keyTextView = new TextView(new ContextThemeWrapper(getContext(), R.style.SettingKey));
        keyTextView.setText(key);
        relativeLayout.addView(keyTextView, keyParams);
        //KEY TEXT VIEW SETTINGS END

        //VALUE TEXT VIEW SETTINGS START
        RelativeLayout.LayoutParams valueParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        valueParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        TextView valueTextView = new TextView(new ContextThemeWrapper(getContext(), R.style.SettingValue));
        valueTextView.setText(value);
        relativeLayout.addView(valueTextView, valueParams);
        //VALUE TEXT VIEW SETTINGS END

        llViewDetails.addView(relativeLayout, rlp);
    }


    private void buildDisplay() {
        settingViewModel.getSettings(1L).observe(this, s -> {
            setSettings(s);

            if (llViewDetails != null && llViewDetails.getChildCount() == 0) {
                buildTextSettingLayout("Username", getSettings().getUser().getUsername());
                buildTextSettingLayout("Email", getSettings().getUser().getEmail());
                buildTextSettingLayout("Date of Joining", dateFormatter.format(getSettings().getDoj()));
                buildTextSettingLayout("Temperature Display Units", getSettings().getTemperatureType().name());
                buildTextSettingLayout("Sound", Boolean.toString(getSettings().getSound()));
                buildTextSettingLayout("Notifications", Boolean.toString(getSettings().getNotification()));
                buildTextSettingLayout("Date probation ends", getSettings().getProbationDate() != null ? dateFormatter.format(getSettings().getProbationDate()) : "");
                buildTextSettingLayout("Probation Duration", getSettings().getProbationDuration());
                buildTextSettingLayout("Date became permanent", getSettings().getPermanentDate() != null ? dateFormatter.format(getSettings().getPermanentDate()) : "");
                buildTextSettingLayout("Probation length", getSettings().getProbationLength());
            }
        });
    }

    private void setupToolBar()
    {
        tvBack.setVisibility(View.VISIBLE);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getFragmentManager().getBackStackEntryCount() > 0){
                    getFragmentManager().popBackStackImmediate();
                }
                else{
                    getActivity().onBackPressed();
                }
            }
        });
    }

}
