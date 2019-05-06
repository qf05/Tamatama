package ru.javaops.android.tamagotchi.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import ru.javaops.android.tamagotchi.model.Pet;
import ru.javaops.android.tamagotchi.utils.PrefsUtils;

public abstract class BasePetViewModel extends BaseViewModel {

    private MediatorLiveData<Pet> petDataMediator = new MediatorLiveData<>();
    private MutableLiveData<Boolean> openCreatePetDialogData = new MutableLiveData<>();
    private LiveData<Pet> petData;
    private Observer<Pet> observer;
    private boolean findAny = false;

    public BasePetViewModel(@NonNull Application application) {
        super(application);
        loadSelectedPet();
    }

    @Override
    public void notifyChange() {
        if (petData.getValue() == null || petData.getValue().getId() != PrefsUtils.getSelectedPetId(getApplication())) {
            loadSelectedPet();
        }
        super.notifyChange();
    }

    public Observer<Pet> getPetObserver() {
        if (observer == null) {
            observer = new Observer<Pet>() {
                @Override
                public void onChanged(Pet pet) {
                    if (pet != null && findAny) {
                        PrefsUtils.saveSelectedPetId(getApplication(), pet.getId());
                    }
                    updateView(pet);
                }
            };
        }
        return observer;
    }

    public MediatorLiveData<Pet> getPetDataMediator() {
        return petDataMediator;
    }

    public LiveData<Pet> getPetData() {
        return petData;
    }

    public MutableLiveData<Boolean> getOpenCreatePetDialogData() {
        return openCreatePetDialogData;
    }

    public void resetOpenCreatePetDialogData() {
        openCreatePetDialogData = new MutableLiveData<>();
    }

    public void resetOpenPetDialogData() {
        this.openCreatePetDialogData = new MutableLiveData<>();
    }

    private void loadSelectedPet() {
        final long petId = PrefsUtils.getSelectedPetId(getApplication());
        if (petData != null) {
            petDataMediator.removeSource(petData);
        }
        if (findAny) {
            petData = getDb().petDao().findAny();
            petDataMediator.addSource(petData, getPetObserver());
        } else {
            petData = getDb().petDao().findById(petId);
            petDataMediator.addSource(petData, getPetObserver());
        }
    }

    private void updateView(Pet pet) {
        if (pet == null) {
            findAny = !findAny;
            if (findAny) {
                loadSelectedPet();
            } else {
                openCreatePetDialogData.postValue(false);
            }
            return;
        }
        notifyChange();
    }
}
