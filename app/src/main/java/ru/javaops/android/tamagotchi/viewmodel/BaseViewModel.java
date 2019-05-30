package ru.javaops.android.tamagotchi.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;
import androidx.databinding.PropertyChangeRegistry;
import androidx.lifecycle.AndroidViewModel;

import ru.javaops.android.tamagotchi.db.DataBase;

public abstract class BaseViewModel extends AndroidViewModel implements Observable {

    private PropertyChangeRegistry callbacks = new PropertyChangeRegistry();
    private DataBase db;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        this.db = DataBase.getAppDatabase(getApplication());
    }

    @Override
    public void addOnPropertyChangedCallback(
            Observable.OnPropertyChangedCallback callback) {
        callbacks.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(
            Observable.OnPropertyChangedCallback callback) {
        callbacks.remove(callback);
    }

    public void notifyChange() {
        callbacks.notifyCallbacks(this, 0, null);
    }

    public DataBase getDb() {
        return db;
    }
}
