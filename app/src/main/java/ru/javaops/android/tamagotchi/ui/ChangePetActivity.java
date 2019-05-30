package ru.javaops.android.tamagotchi.ui;

import android.os.Bundle;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.javaops.android.tamagotchi.R;
import ru.javaops.android.tamagotchi.adapters.MySpinnerAdapter;
import ru.javaops.android.tamagotchi.adapters.PetAdapter;
import ru.javaops.android.tamagotchi.db.DataBase;
import ru.javaops.android.tamagotchi.model.Pet;
import ru.javaops.android.tamagotchi.utils.CompareUtils;
import ru.javaops.android.tamagotchi.utils.PrefsUtils;
import ru.javaops.android.tamagotchi.utils.ViewHelper;

public class ChangePetActivity extends BaseActivity implements PetAdapter.ItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);
        initViews();
        initSpinner();
    }

    @Override
    public void onItemClick(long itemId) {
        PrefsUtils.saveSelectedPetId(this, itemId);
        goBack(null);
    }

    private void initViews() {
        final RecyclerView rv = findViewById(R.id.pets_rv);
        ViewHelper.setParametersRv(ChangePetActivity.this, rv);

        final List<Pet> pets = DataBase.getAppDatabase(getApplicationContext()).petDao().getAll();
        final PetAdapter adapter = new PetAdapter(pets, this);
        rv.setAdapter(adapter);

        Spinner sortSpinner = findViewById(R.id.spinner_sort);
        sortSpinner.setOnItemSelectedListener(CompareUtils.getSpinnerClickListener(adapter, pets));
    }

    private void initSpinner() {
        Spinner spinner = findViewById(R.id.spinner_sort);
        String[] stringArray = getResources().getStringArray(R.array.sort);
        SpinnerAdapter spinnerAdapter =
                new MySpinnerAdapter(this, stringArray, false);
        spinner.setAdapter(spinnerAdapter);
    }
}
