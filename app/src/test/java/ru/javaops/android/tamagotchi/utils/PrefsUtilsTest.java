package ru.javaops.android.tamagotchi.utils;

import android.content.Context;
import android.content.SharedPreferences;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PrefsUtilsTest {

    private static final String APP_PREFERENCES = "PREFERENCES";
    private static final String PREFERENCES_SELECTED_PET = "SELECTED_PET";
    private static final String PREFERENCES_SOUND_ON = "SOUND_ON";

    private Context context;
    private SharedPreferences preferences;

    @Before
    public void setUp() {
        context = mock(Context.class);
        preferences = mock(SharedPreferences.class);
        when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(preferences);
    }

    @After
    public void clear() {
        context = null;
        preferences = null;
    }

    @Test
    public void getSoundState() {
        PrefsUtils.getSoundState(context);
        verify(context).getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        verify(preferences).getBoolean(PREFERENCES_SOUND_ON, true);
    }

    @Test
    public void getSelectedPetId() {
        PrefsUtils.getSelectedPetId(context);
        verify(context).getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        verify(preferences).getLong(PREFERENCES_SELECTED_PET, -1);
    }

    @Test
    public void saveSoundState() {
        SharedPreferences.Editor edit = mock(SharedPreferences.Editor.class);
        when(preferences.edit()).thenReturn(edit);
        when(edit.putBoolean(anyString(), anyBoolean())).thenReturn(edit);
        PrefsUtils.saveSoundState(context, true);
        verify(context).getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        verify(preferences).edit();
        verify(edit).putBoolean(PREFERENCES_SOUND_ON, true);
        verify(edit).apply();
    }

    @Test
    public void saveSelectedPetId() {
        SharedPreferences.Editor edit = mock(SharedPreferences.Editor.class);
        when(preferences.edit()).thenReturn(edit);
        when(edit.putLong(anyString(), anyLong())).thenReturn(edit);
        PrefsUtils.saveSelectedPetId(context, 1);
        verify(context).getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        verify(preferences).edit();
        verify(edit).putLong(PREFERENCES_SELECTED_PET, 1);
        verify(edit).apply();
    }

    @Test
    public void getPreferences() {
        Context context = mock(Context.class);
        PrefsUtils.getPreferences(context);
        verify(context).getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }
}