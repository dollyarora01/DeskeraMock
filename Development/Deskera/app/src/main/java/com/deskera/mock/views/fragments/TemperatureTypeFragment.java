package com.deskera.mock.views.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.deskera.mock.R;
import com.deskera.mock.entities.TemperatureType;
import com.deskera.mock.interfaces.InteractionListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TemperatureTypeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class TemperatureTypeFragment extends Fragment {
    @BindView(R.id.llTemperature)
    LinearLayout llTemperature;

    private InteractionListener<String> mListener;

    public TemperatureTypeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_temperature_type, container, false);
        ButterKnife.bind(this, view);
        buildTextSettingLayout("Celsius");
        buildTextSettingLayout("Fahrenheit");
        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof InteractionListener) {
            mListener = (InteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String value);
    }

    private void buildTextSettingLayout(String key) {
        RelativeLayout relativeLayout = new RelativeLayout(new ContextThemeWrapper(getContext(), R.style.SettingRow));
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
        relativeLayout.setLayoutParams(rlp);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    Fragment fragment = getFragmentManager().findFragmentByTag(getContext().getResources().getString(R.string.title_settings));
                    ((SettingsFragment) fragment).onInteraction(key);
                    if(getFragmentManager().getBackStackEntryCount() > 0){
                        getFragmentManager().popBackStackImmediate();
                    }
                    else{
                        getActivity().onBackPressed();
                    }
                }
            }
        });
        //KEY TEXT VIEW SETTINGS START
        RelativeLayout.LayoutParams keyParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        keyParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

        TextView keyTextView = new TextView(new ContextThemeWrapper(getContext(), R.style.SettingKey));
        keyTextView.setText(key);
        relativeLayout.addView(keyTextView, keyParams);
        //KEY TEXT VIEW SETTINGS END

        if (llTemperature == null) {
            llTemperature = new LinearLayout(getContext());
            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            llTemperature.setLayoutParams(llp);
            llTemperature.setOrientation(LinearLayout.VERTICAL);
        }

        llTemperature.addView(relativeLayout, rlp);
    }
}
