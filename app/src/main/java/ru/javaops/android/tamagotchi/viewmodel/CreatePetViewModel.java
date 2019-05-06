package ru.javaops.android.tamagotchi.viewmodel;

import android.app.Application;
import android.view.View;
import android.widget.AdapterView;

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

    public void setName(CharSequence s, int start, int before, int count) {
        petName = s.toString();
    }

    public void petTypeSelected(AdapterView<?> parent, View view, int pos, long id) {
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
