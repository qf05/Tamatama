package ru.javaops.android.tamagotchi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ru.javaops.android.tamagotchi.adapters.ChangeRVAdapter;
import ru.javaops.android.tamagotchi.db.DataBase;
import ru.javaops.android.tamagotchi.model.Pet;
import ru.javaops.android.tamagotchi.utils.PetUtils;

import static ru.javaops.android.tamagotchi.MainActivity.APP_PREFERENCES;
import static ru.javaops.android.tamagotchi.MainActivity.PREFERENCES_SELECTED_PET;
import static ru.javaops.android.tamagotchi.MainActivity.SELECTED_PET;


public class ChangePetActivity extends AppCompatActivity implements ChangeRVAdapter.ItemClickListener {

    private ChangeRVAdapter adapter;
    private static DataBase db;
    private static PetUtils listProvider;

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
        db = DataBase.getAppDatabase(this);

        listProvider = ViewModelProviders.of(this).get(PetUtils.class);
        listProvider.getPets().observe(this, new Observer<List<Pet>>() {
            @Override
            public void onChanged(@Nullable final List<Pet> pets) {
                adapter.changeList(pets);
            }
        });

        adapter = new ChangeRVAdapter(ChangePetActivity.this, new ArrayList<Pet>());
        adapter.setClickListener(ChangePetActivity.this);
        rv.setAdapter(adapter);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                listProvider.getPets().postValue(db.petDao().getAll());
            }
        });
//        new LoadList().execute();
    }

    @Override
    public void onItemClick(View view, int position) {
        SELECTED_PET = adapter.getItem(position);
        Log.i("ChangePet", SELECTED_PET.getName());
        SharedPreferences settings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(PREFERENCES_SELECTED_PET, SELECTED_PET.getId());
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

//    private static class LoadList extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            listProvider.getPets().postValue(db.petDao().getAll());
//            return null;
//        }
//    }
}