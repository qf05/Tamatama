package ru.javaops.android.tamagotchi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.javaops.android.tamagotchi.adapters.MySpinnerAdapter;
import ru.javaops.android.tamagotchi.adapters.PetAdapter;
import ru.javaops.android.tamagotchi.db.DataBase;
import ru.javaops.android.tamagotchi.model.Pet;
import ru.javaops.android.tamagotchi.utils.PrefsUtils;
import ru.javaops.android.tamagotchi.utils.ViewHelper;

public class ChangePetActivity extends AppCompatActivity implements PetAdapter.ItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);
        initRecycler();
        initSpinner();
    }

    @Override
    public void onItemClick(long itemId) {
        PrefsUtils.saveSelectedPetId(this, itemId);
        goBack(null);
    }

    public void goBack(View view) {
        Intent intent = new Intent(ChangePetActivity.this, SettingsActivity.class);
        startActivity(intent);
        finish();
    }

    private void initRecycler() {
        final RecyclerView rv = findViewById(R.id.pets_rv);
        ViewHelper.setParametersRv(ChangePetActivity.this, rv);

        final List<Pet> pets = DataBase.getAppDatabase(getApplicationContext()).petDao().getAll();
        PetAdapter adapter = new PetAdapter(pets, this);
        rv.setAdapter(adapter);
    }

    private void initSpinner() {
        Spinner spinner = findViewById(R.id.spinner_sort);
        String[] stringArray = getResources().getStringArray(R.array.sort);
        SpinnerAdapter spinnerAdapter =
                new MySpinnerAdapter(this, stringArray, false);
        spinner.setAdapter(spinnerAdapter);
    }
}
