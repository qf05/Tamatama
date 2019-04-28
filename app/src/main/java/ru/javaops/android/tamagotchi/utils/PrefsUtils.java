package ru.javaops.android.tamagotchi.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefsUtils {

    private static final String APP_PREFERENCES = "PREFERENCES";
    private static final String PREFERENCES_SELECTED_PET = "SELECTED_PET";

    private PrefsUtils() {
    }

    public static synchronized long getSelectedPetId(Context context) {
        return getPreferences(context).getLong(PREFERENCES_SELECTED_PET, -1);
    }

    public static synchronized void saveSelectedPetId(Context context, long petId) {
        getPreferences(context)
                .edit()
                .putLong(PREFERENCES_SELECTED_PET, petId)
                .apply();
    }

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }
}
