package ru.javaops.android.tamagotchi.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import ru.javaops.android.tamagotchi.R;
import ru.javaops.android.tamagotchi.adapters.PetAdapter;
import ru.javaops.android.tamagotchi.model.Pet;

public class DeletePetViewModel extends BaseViewModel {

    private LiveData<List<Pet>> petsData;
    private PetAdapter adapter;

    public DeletePetViewModel(@NonNull Application application) {
        super(application);
        adapter = new PetAdapter(new ArrayList<Pet>(), null, R.layout.item_pet);
        petsData = getDb().petDao().getAll();
    }

    public LiveData<List<Pet>> getPetsData() {
        return petsData;
    }

    public PetAdapter getAdapter() {
        return adapter;
    }

    public void sortPets(int pos) {
        adapter.sortData(pos);
    }
}
