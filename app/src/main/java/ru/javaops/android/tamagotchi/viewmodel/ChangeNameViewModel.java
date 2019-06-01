package ru.javaops.android.tamagotchi.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import java.util.Locale;

import ru.javaops.android.tamagotchi.R;

public class ChangeNameViewModel extends BasePetViewModel {

    private String newName;

    public ChangeNameViewModel(@NonNull Application application) {
        super(application);
    }

    public void setNewName(CharSequence s) {
        newName = s.toString();
    }

    public String getThisName() {
        if (getPetData().getValue() != null) {
            return String.format(Locale.getDefault(),
                    getApplication().getString(R.string.this_name), getPetData().getValue().getName());
        }
        return "";
    }

    public String getNewName() {
        return newName;
    }
}
