package com.deskera.mock.views.fragments;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.deskera.mock.R;
import com.deskera.mock.viewModels.ProfileViewModel;

import butterknife.BindView;

public class Profile extends Fragment {

    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etDoj)
    EditText etDoj;
    @BindView(R.id.etHobby)
    EditText etHobby;

    private ProfileViewModel mViewModel;

    public static Profile newInstance() {
        return new Profile();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        // TODO: Use the ViewModel
    }

}
