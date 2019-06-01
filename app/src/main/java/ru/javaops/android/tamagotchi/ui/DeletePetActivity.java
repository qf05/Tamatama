package ru.javaops.android.tamagotchi.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.javaops.android.tamagotchi.R;
import ru.javaops.android.tamagotchi.adapters.MySpinnerAdapter;
import ru.javaops.android.tamagotchi.adapters.PetAdapter;
import ru.javaops.android.tamagotchi.databinding.ActivityPetListBinding;
import ru.javaops.android.tamagotchi.db.DataBase;
import ru.javaops.android.tamagotchi.model.Pet;
import ru.javaops.android.tamagotchi.ui.dialog.DeletePetDialogFragment;
import ru.javaops.android.tamagotchi.utils.ExecutorUtils;
import ru.javaops.android.tamagotchi.utils.ViewHelper;
import ru.javaops.android.tamagotchi.viewmodel.DeletePetViewModel;

public class DeletePetActivity extends BaseActivity implements DeletePetDialogFragment.DeletePets {

    private Spinner sortSpinner;
    private PetAdapter adapter;
    private DataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);
        db = DataBase.getAppDatabase(DeletePetActivity.this);
        DeletePetViewModel model = ViewModelProviders.of(this).get(DeletePetViewModel.class);
        adapter = model.getAdapter();
        model.getPetsData().observe(this, getPetsObserver());

        ActivityPetListBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_pet_list);
        binding.setLifecycleOwner(this);
        binding.setModel(model);
        sortSpinner = binding.spinnerSort;
        final RecyclerView rv = binding.petsRv;
        ViewHelper.setParametersRv(DeletePetActivity.this, rv, false);
        rv.setAdapter(adapter);
        binding.delete.setOnClickListener(getDeleteListener());
        initSpinner();
    }

    @Override
    public void deletePets() {
        final List<Pet> deleteList = adapter.getDeleteList();
        ExecutorUtils.getExecutor().submit(new Runnable() {
            @Override
            public void run() {
                adapter.clearDeleteMap();
                db.petDao().delete(deleteList);
                DeletePetActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        makeMessage(getResources().getQuantityString(R.plurals.was_delete_plurals,
                                deleteList.size(), deleteList.size()));
                    }
                });
            }
        });
    }

    private Observer<List<Pet>> getPetsObserver() {
        return new Observer<List<Pet>>() {
            @Override
            public void onChanged(List<Pet> pets) {
                adapter.updateData(pets, sortSpinner.getSelectedItemPosition());
            }
        };
    }

    private View.OnClickListener getDeleteListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeletePetDialogFragment dialog = new DeletePetDialogFragment();
                dialog.setCancelable(false);
                dialog.show(getSupportFragmentManager(), "dialogDeletePet");
            }
        };
    }

    private void initSpinner() {
        String[] stringArray = getResources().getStringArray(R.array.sort);
        SpinnerAdapter spinnerAdapter =
                new MySpinnerAdapter(this, stringArray, false);
        sortSpinner.setAdapter(spinnerAdapter);
    }
}
