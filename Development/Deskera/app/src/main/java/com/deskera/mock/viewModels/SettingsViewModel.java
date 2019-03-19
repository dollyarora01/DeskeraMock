package com.deskera.mock.views.fragments;

import com.deskera.mock.DeskeraMockApplication;
import com.deskera.mock.entities.Settings;

import androidx.lifecycle.ViewModel;

public class SettingsViewModel extends ViewModel {
    private Settings getSettings(Long userId) {
        return DeskeraMockApplication.getDaoSession().getSettingsDao().load(userId);
    }
}
