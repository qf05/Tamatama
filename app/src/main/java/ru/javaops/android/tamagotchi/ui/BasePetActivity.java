package ru.javaops.android.tamagotchi.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import ru.javaops.android.tamagotchi.db.DataBase;
import ru.javaops.android.tamagotchi.model.Pet;
import ru.javaops.android.tamagotchi.utils.PrefsUtils;
import ru.javaops.android.tamagotchi.utils.ViewHelper;

public abstract class BasePetActivity extends BaseActivity {

    private Pet selectedPet;
    private LiveData<Pet> petData;
    private Observer<Pet> observer;
    private boolean findAny;

    @Override
    protected void onResume() {
        super.onResume();
        loadSelectedPet();
    }

    public Pet getPet() {
        return selectedPet;
    }

    protected void updateView(Pet pet) {
        if (pet == null) {
            findAny = !findAny;
            if (findAny) {
                loadSelectedPet();
            } else {
                ViewHelper.showCreatePetDialog(this, false);
            }
        } else {
            if (pet.getId() == PrefsUtils.getSelectedPetId(this)) {
                selectedPet = pet;
            } else {
                loadSelectedPet();
            }
        }
    }

    private void loadSelectedPet() {
        final long petId = PrefsUtils.getSelectedPetId(this);
        final DataBase db = DataBase.getAppDatabase(this);
        if (petData != null) {
            petData.removeObserver(observer);
        }
        if (findAny) {
            petData = db.petDao().findAny();
        } else {
            petData = db.petDao().findById(petId);
        }
        if (observer == null) {
            observer = getPetObserver();
        }
        petData.observe(this, observer);
    }

    private Observer<Pet> getPetObserver() {
        return new Observer<Pet>() {
            @Override
            public void onChanged(Pet pet) {
                if (pet != null && findAny) {
                    PrefsUtils.saveSelectedPetId(BasePetActivity.this, pet.getId());
                }
                updateView(pet);
            }
        };
    }
}
