package ru.javaops.android.tamagotchi.viewmodel;

import android.app.Application;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.Locale;

import ru.javaops.android.tamagotchi.R;
import ru.javaops.android.tamagotchi.ui.WalkActivity;
import ru.javaops.android.tamagotchi.utils.SaveUtils;

public class MainViewModel extends BasePetViewModel {

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public String getLvl() {
        if (getPetData().getValue() != null) {
            return String.format(Locale.getDefault(),
                    getApplication().getString(R.string.level_number),
                    getPetData().getValue().getLvl());
        }
        return "";
    }

    public void lvlUp(View view) {
        if (getPetData().getValue() != null) {
            getPetData().getValue().incLvl();
            SaveUtils.getExecutor().submit(new Runnable() {
                @Override
                public void run() {
                    getDb().petDao().update(getPetData().getValue());
                }
            });
        }
    }

    public void goWalk(View view) {
        if (getPetData().getValue() != null) {
            Intent intent = new Intent(getApplication(), WalkActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplication().startActivity(intent);
        }
    }
}
