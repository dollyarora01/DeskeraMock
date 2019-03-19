package com.deskera.mock.views.fragments;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.deskera.mock.DeskeraMockApplication;
import com.deskera.mock.R;
import com.deskera.mock.entities.User;
import com.deskera.mock.viewModels.ProfileViewModel;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileFragment extends Fragment {

    //region Controls
    @BindView(R.id.etEmail)
    TextInputEditText etEmail;
    @BindView(R.id.etDoj)
    TextInputEditText etDoj;
    @BindView(R.id.etHobby)
    TextInputEditText etHobby;
    @BindView(R.id.btnSave)
    Button btnSave;
    TextView tvTitle;
    Toolbar profileToolbar;
    CollapsingToolbarLayout ctlProfile;
    //endregion

    //region Variables
    private ProfileViewModel profileViewModel;
    User user;
    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
    private final String EMAIL_REG_EX = "^([a-zA-Z0-9]+[a-zA-Z0-9._\\%\\\\+]*@(?:[a-zA-Z0-9]+\\.)+[a-zA-Z]{2,})$";

    //endregion
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    //region Functions
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        getUserDetails(1L);
    }

    private void setupToolbar() {
        profileToolbar = (Toolbar) getActivity().findViewById(R.id.profile_toolbar);
        ctlProfile = getActivity().findViewById(R.id.ctlProfile);
        tvTitle = (TextView) ctlProfile.findViewById(R.id.tvTitle);
        ctlProfile.setTitle(getUser().getUsername());
        ctlProfile.setExpandedTitleTextAppearance(R.style.profile_toolbar_title_expanded);
        ctlProfile.setCollapsedTitleTextAppearance(R.style.profile_toolbar_title_collapsed);
        ctlProfile.setExpandedTitleGravity(Gravity.CENTER);
        ctlProfile.setCollapsedTitleGravity(Gravity.CENTER);


    }

    private void getUserDetails(Long userId) {
        profileViewModel.getUserDetails(userId).observe(this, user -> setUserDisplay(user));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setUserDisplay(User user) {
        setUser(user);
        etEmail.setText(getUser().getEmail());
        etHobby.setText(getUser().getHobby());
        etDoj.setText(dateFormatter.format(getUser().getSettings().getDoj()));
        etDoj.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Calendar newCalendar = Calendar.getInstance();
                    DatePickerDialog startDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            newCalendar.set(year, monthOfYear, dayOfMonth);
                            etDoj.setText(dateFormatter.format(newCalendar.getTime()));
                        }
                    }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                    startDatePicker.show();
                }
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etEmail.getText() != null && !etEmail.getText().toString().isEmpty() && Pattern.matches(EMAIL_REG_EX, etEmail.getText().toString()))
                    getUser().setEmail(etEmail.getText().toString());
                else
                    displayError(etEmail, "Please enter valid email");
                if (etDoj.getText() != null && !etDoj.getText().toString().isEmpty()) {
                    try {
                        getUser().getSettings().setDoj(dateFormatter.parse(etDoj.getText().toString()).getTime());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else
                    displayError(etDoj, "Please enter valid date of joining");
                if (etHobby.getText() != null && !etHobby.getText().toString().isEmpty())
                    getUser().setHobby(etHobby.getText().toString());
                else
                    displayError(etHobby, "Please enter valid hobby");
                profileViewModel.updateUser(getUser());
                Toast.makeText(DeskeraMockApplication.getContext(), "User updated", Toast.LENGTH_SHORT).show();
            }
        });
        setupToolbar();
    }

    private void displayError(EditText editText, String message) {
        editText.setError(message);
        editText.requestFocus();
    }
//endregion
}
