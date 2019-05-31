package ru.javaops.android.tamagotchi.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import ru.javaops.android.tamagotchi.enums.PetsType;

public class CreatePetViewModel extends BaseViewModel {

    private String petName;
    private boolean showCancelButton;
    private PetsType petsType;

    public CreatePetViewModel(@NonNull Application application) {
        super(application);
        petsType = PetsType.CAT;
    }

    public void setName(CharSequence s) {
        petName = s.toString();
    }

    public void petTypeSelected(int pos) {
        PetsType[] petsTypes = PetsType.values();
        petsType = petsTypes[pos];
    }

    public boolean isShowCancelButton() {
        return showCancelButton;
    }

    public void setShowCancelButton(boolean showCancelButton) {
        this.showCancelButton = showCancelButton;
    }

    public String getPetName() {
        return petName;
    }

    public PetsType getPetsType() {
        return petsType;
    }
}
