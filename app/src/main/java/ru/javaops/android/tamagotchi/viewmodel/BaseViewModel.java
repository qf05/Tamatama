package ru.javaops.android.tamagotchi.viewmodel;

import android.app.Application;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.Observable;
import androidx.databinding.PropertyChangeRegistry;
import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

import ru.javaops.android.tamagotchi.adapters.PetAdapter;
import ru.javaops.android.tamagotchi.db.DataBase;
import ru.javaops.android.tamagotchi.model.Pet;

public abstract class BaseViewModel extends AndroidViewModel implements Observable {

    private PropertyChangeRegistry callbacks = new PropertyChangeRegistry();
    private DataBase db;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        this.db = DataBase.getAppDatabase(getApplication());
    }

    @BindingAdapter(value = {"app:pets", "app:spinner"}, requireAll = false)
    public static void setPets(RecyclerView recyclerView, List<Pet> pets, Spinner spinner) {
        int pos = spinner == null ? 0 : spinner.getSelectedItemPosition();
        ((PetAdapter) Objects.requireNonNull(recyclerView.getAdapter())).updateData(pets, pos);
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
