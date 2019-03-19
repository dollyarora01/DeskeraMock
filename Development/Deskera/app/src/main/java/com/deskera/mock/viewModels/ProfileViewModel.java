package com.deskera.mock.viewModels;

import com.deskera.mock.DeskeraMockApplication;
import com.deskera.mock.entities.User;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProfileViewModel extends ViewModel {
    private MutableLiveData<User> user;

    public LiveData<User> getUserDetails(Long userId) {
        if (user == null)
            user = new MutableLiveData<>();
        user.postValue(DeskeraMockApplication.getDaoSession().getUserDao().load(userId));
        return user;
    }

    public void updateUser(User user) {
        if (user != null && user.getUserId() > 0L && user.getHobby() != null && user.getEmail() != null && user.getSettings() != null &&
                user.getSettings().getProbationDate() != null &&
                user.getSettings().getTemperature() > 0.0 &&
                user.getSettings().getTemperatureType() != null &&
                user.getSettings().getDoj() != null)
            DeskeraMockApplication.getDaoSession().getSettingsDao().update(user.getSettings());
        DeskeraMockApplication.getDaoSession().getUserDao().update(user);
    }
}
