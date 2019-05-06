package ru.javaops.android.tamagotchi.viewmodel;

import android.app.Application;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;

import ru.javaops.android.tamagotchi.ui.ChangePetActivity;
import ru.javaops.android.tamagotchi.ui.DeletePetActivity;

public class SettingsViewModel extends BasePetViewModel {

    public SettingsViewModel(@NonNull Application application) {
        super(application);
    }

    public void createPet(View view) {
        getOpenCreatePetDialogData().postValue(true);
    }

    public void changePet(View view) {
        Intent intent = new Intent(getApplication(), ChangePetActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplication().startActivity(intent);
    }

    public void deletePet(View view) {
        Intent intent = new Intent(getApplication(), DeletePetActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplication().startActivity(intent);
    }

    public void changePetName(View view) {

    }
}
