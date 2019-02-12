package ru.javaops.android.tamagotchi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ru.javaops.android.tamagotchi.adapters.ChangeRVAdapter;
import ru.javaops.android.tamagotchi.db.DataBase;
import ru.javaops.android.tamagotchi.model.Pet;

import static ru.javaops.android.tamagotchi.MainActivity.APP_PREFERENCES;
import static ru.javaops.android.tamagotchi.MainActivity.PREFERENCES_SELECTED_PET;


public class ChangePetActivity extends AppCompatActivity implements ChangeRVAdapter.ItemClickListener {

    private ChangeRVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pet);

        final RecyclerView rv = findViewById(R.id.change_rv);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(ChangePetActivity.this);
        rv.setLayoutManager(llm);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv.getContext(),
                llm.getOrientation());
        rv.addItemDecoration(dividerItemDecoration);

        List<Pet> pets = DataBase.getAppDatabase(getApplicationContext()).petDao().getAll();
        adapter = new ChangeRVAdapter(ChangePetActivity.this, pets);
        adapter.setClickListener(ChangePetActivity.this);
        rv.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        SharedPreferences settings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(PREFERENCES_SELECTED_PET, adapter.getItem(position).getId());
        editor.apply();
        Intent intent = new Intent(ChangePetActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void goBack(View view) {
        Intent intent = new Intent(ChangePetActivity.this, SettingsActivity.class);
        startActivity(intent);
        finish();
    }
}
