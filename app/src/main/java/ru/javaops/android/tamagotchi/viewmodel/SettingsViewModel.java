package ru.javaops.android.tamagotchi.viewmodel;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;

import ru.javaops.android.tamagotchi.ui.DeletePetActivity;
import ru.javaops.android.tamagotchi.utils.SoundHelper;

public class SettingsViewModel extends BasePetViewModel {

    public SettingsViewModel(@NonNull Application application) {
        super(application);
    }

    public void createPet() {
        getOpenCreatePetDialogData().postValue(true);
    }

    public void deletePet() {
        Intent intent = new Intent(getApplication(), DeletePetActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplication().startActivity(intent);
    }

    public void history() {

    }

    public void notification() {

    }

    public void sound() {
        SoundHelper.switchSoundState(getApplication());
        notifyChange();
    }

    public boolean isSound() {
        return SoundHelper.isSoundOn();
    }
}
