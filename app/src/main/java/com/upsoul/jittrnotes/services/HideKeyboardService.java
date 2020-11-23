package com.upsoul.jittrnotes.services;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public interface HideKeyboardService {
    default void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        //noinspection ConstantConditions
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
