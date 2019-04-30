package ru.javaops.android.tamagotchi.ui;

import android.os.Bundle;
import android.widget.Spinner;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.javaops.android.tamagotchi.R;
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
        setContentView(R.layout.activity_change_pet);
        initViews();
    }

    @Override
    public void onItemClick(long itemId) {
        PrefsUtils.saveSelectedPetId(this, itemId);
        goBack(null);
    }

    private void initViews() {
        final RecyclerView rv = findViewById(R.id.change_rv);
        ViewHelper.setParametersRv(ChangePetActivity.this, rv);

        final List<Pet> pets = DataBase.getAppDatabase(getApplicationContext()).petDao().getAll();
        final PetAdapter adapter = new PetAdapter(pets, this);
        rv.setAdapter(adapter);

        Spinner sortSpinner = findViewById(R.id.change_sort);
        sortSpinner.setOnItemSelectedListener(CompareUtils.getSpinnerClickListener(adapter, pets));
    }
}
