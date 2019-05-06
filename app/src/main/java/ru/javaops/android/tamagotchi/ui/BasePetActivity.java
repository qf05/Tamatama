package ru.javaops.android.tamagotchi.ui;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import ru.javaops.android.tamagotchi.viewmodel.BasePetViewModel;

public abstract class BasePetActivity extends BaseActivity {

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
                if (!aBoolean && BasePetActivity.this instanceof SettingsActivity) {
                    finish();
                } else {
                    FragmentManager manager = getSupportFragmentManager();
                    if (manager.findFragmentByTag("dialogCreatePet") == null) {
                        CreatePetDialogFragment dialog = new CreatePetDialogFragment();
                        dialog.setCancelable(false);
                        dialog.show(manager, "dialogCreatePet");
                    }
                }
            }
        };
    }
}
