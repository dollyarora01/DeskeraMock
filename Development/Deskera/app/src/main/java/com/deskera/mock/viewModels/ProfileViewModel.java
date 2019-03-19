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
        if (user != null)
            DeskeraMockApplication.getDaoSession().getUserDao().update(user);
    }
}
