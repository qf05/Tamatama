package ru.javaops.android.tamagotchi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.javaops.android.tamagotchi.adapters.PetAdapter;
import ru.javaops.android.tamagotchi.db.DataBase;
import ru.javaops.android.tamagotchi.model.Pet;
import ru.javaops.android.tamagotchi.utils.PrefsUtils;

public class ChangePetActivity extends AppCompatActivity implements PetAdapter.ItemClickListener {

    private PetAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);
        initRecycler();
    }

    @Override
    public void onItemClick(View view, int position) {
        PrefsUtils.saveSelectedPetId(this, adapter.getItem(position).getId());
        goBack(null);
    }

    public void goBack(View view) {
        Intent intent = new Intent(ChangePetActivity.this, SettingsActivity.class);
        startActivity(intent);
        finish();
    }

    private void initRecycler() {
        final RecyclerView rv = findViewById(R.id.pets_rv);
        rv.setHasFixedSize(true);
        final LinearLayoutManager llm = new LinearLayoutManager(ChangePetActivity.this);
        rv.setLayoutManager(llm);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv.getContext(),
                llm.getOrientation());
        rv.addItemDecoration(dividerItemDecoration);

        final List<Pet> pets = DataBase.getAppDatabase(getApplicationContext()).petDao().getAll();
        adapter = new PetAdapter(pets, this);
        rv.setAdapter(adapter);
    }
}
