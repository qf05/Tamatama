package ru.javaops.android.tamagotchi.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefsUtils {

    private static final String APP_PREFERENCES = "PREFERENCES";
    private static final String PREFERENCES_SELECTED_PET = "SELECTED_PET";
    private static final String PREFERENCES_SOUND_ON = "SOUND_ON";

    private PrefsUtils() {
    }

    public static synchronized boolean getSoundState(Context context) {
        return getPreferences(context).getBoolean(PREFERENCES_SOUND_ON, true);
    }

    public static synchronized long getSelectedPetId(Context context) {
        return getPreferences(context).getLong(PREFERENCES_SELECTED_PET, -1);
    }

    public static synchronized void saveSoundState(Context context, boolean soundOn) {
        getPreferences(context)
                .edit()
                .putBoolean(PREFERENCES_SOUND_ON, soundOn)
                .apply();
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
