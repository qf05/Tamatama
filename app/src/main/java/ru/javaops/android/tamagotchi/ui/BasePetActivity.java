package ru.javaops.android.tamagotchi.ui;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import ru.javaops.android.tamagotchi.model.Pet;
import ru.javaops.android.tamagotchi.utils.ViewHelper;
import ru.javaops.android.tamagotchi.viewmodel.BasePetViewModel;

public abstract class BasePetActivity extends BaseActivity {

    public Pet getPet() {
        return null;
    }

    protected <T extends BasePetViewModel> T createModel(Class<T> modelClass) {
        T model = ViewModelProviders.of(this).get(modelClass);
        model.resetOpenCreatePetDialogData();
        model.getOpenCreatePetDialogData().observe(this, createOpenCreatePetDialogObserver());
        model.getPetDataMediator().observe(this, model.getPetObserver());
        return model;
    }

    private Observer<Boolean> createOpenCreatePetDialogObserver() {
        return new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                ViewHelper.showCreatePetDialog(BasePetActivity.this, aBoolean);
            }
        };
    }
}
