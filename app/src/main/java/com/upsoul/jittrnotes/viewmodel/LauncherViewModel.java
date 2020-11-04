package com.upsoul.jittrnotes.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class LauncherViewModel extends AndroidViewModel {

    public LauncherViewModel(@NonNull Application application) {
        super(application);
    }


    public boolean authenticate(String pin) {
        SharedPreferences preferences = getApplication().getSharedPreferences("", Context.MODE_PRIVATE);
        String key = preferences.getString("PIN", null);
        if (key == null) {
            return true;
        } else {
            return pin.equals(key);
        }
    }

    public boolean authenticate() {
        SharedPreferences preferences = getApplication().getSharedPreferences("", Context.MODE_PRIVATE);
        String key = preferences.getString("PIN", null);
        return key == null;
    }
}
