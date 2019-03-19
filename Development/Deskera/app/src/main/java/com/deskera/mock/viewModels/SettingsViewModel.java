package com.deskera.mock.viewModels;

import com.deskera.mock.DeskeraMockApplication;
import com.deskera.mock.entities.Settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SettingsViewModel extends ViewModel {
    private MutableLiveData<Settings> settings;

    public LiveData<Settings> getSettings(Long userId) {
        if (settings == null)
            settings = new MutableLiveData<>();
        settings.postValue(DeskeraMockApplication.getDaoSession().getSettingsDao().load(userId));
        return settings;
    }

    public boolean updateSettings(Settings settings) {
        if (settings != null &&
                settings.getProbationDate() != null &&
                settings.getTemperature() > 0.0 &&
                settings.getTemperatureType() != null &&
                settings.getDoj() != null) {
            try {
                DeskeraMockApplication.getDaoSession().getSettingsDao().update(settings);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }
}
