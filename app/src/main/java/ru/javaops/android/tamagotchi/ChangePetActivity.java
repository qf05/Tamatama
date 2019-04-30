package ru.javaops.android.tamagotchi;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.javaops.android.tamagotchi.adapters.PetAdapter;
import ru.javaops.android.tamagotchi.db.DataBase;
import ru.javaops.android.tamagotchi.model.Pet;
import ru.javaops.android.tamagotchi.utils.PrefsUtils;
import ru.javaops.android.tamagotchi.utils.ViewHelper;

public class ChangePetActivity extends AppCompatActivity implements PetAdapter.ItemClickListener {

    private PetAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pet);

        initRecycler();
    }

    @Override
    public void onItemClick(long itemId) {
        PrefsUtils.saveSelectedPetId(this, itemId);
        goBack(null);
    }

    public void goBack(View view) {
        finish();
    }

    private void initRecycler() {
        final RecyclerView rv = findViewById(R.id.change_rv);
        ViewHelper.setParametersRv(ChangePetActivity.this, rv);

        final List<Pet> pets = DataBase.getAppDatabase(getApplicationContext()).petDao().getAll();
        adapter = new PetAdapter(pets, this);
        rv.setAdapter(adapter);
    }
}
