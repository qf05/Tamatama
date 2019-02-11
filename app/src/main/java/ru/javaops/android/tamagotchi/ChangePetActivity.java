package ru.javaops.android.tamagotchi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ru.javaops.android.tamagotchi.adapters.ChangeRVAdapter;
import ru.javaops.android.tamagotchi.adapters.PetDataBaseAdapter;
import ru.javaops.android.tamagotchi.model.Pet;

import static ru.javaops.android.tamagotchi.MainActivity.APP_PREFERENCES;
import static ru.javaops.android.tamagotchi.MainActivity.PREFERENCES_SELECTED_PET;
import static ru.javaops.android.tamagotchi.MainActivity.SELECTED_PET;


public class ChangePetActivity extends AppCompatActivity implements ChangeRVAdapter.ItemClickListener {

    private ChangeRVAdapter adapter;
    private static List<Pet> pets = new ArrayList<>();
    private static PetDataBaseAdapter db;
    private static Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pet);
        handler = new ChangePetHandler(this);
        final RecyclerView rv = findViewById(R.id.change_rv);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(ChangePetActivity.this);
        rv.setLayoutManager(llm);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv.getContext(),
                llm.getOrientation());
        rv.addItemDecoration(dividerItemDecoration);
        db = PetDataBaseAdapter.getInstance(this);
        adapter = new ChangeRVAdapter(ChangePetActivity.this, pets);
        adapter.setClickListener(ChangePetActivity.this);
        rv.setAdapter(adapter);
        new LoadList().execute();
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

    private static class LoadList extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            List<Pet> petList = db.getAll();
            pets.clear();
            pets.addAll(petList);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (handler != null) {
                handler.sendEmptyMessage(0);
            }
        }
    }

    private void updateList() {
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler = null;
    }

    private static class ChangePetHandler extends Handler {
        WeakReference<ChangePetActivity> wrActivity;

        private ChangePetHandler(ChangePetActivity activity) {
            wrActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ChangePetActivity activity = wrActivity.get();
            if (activity != null) {
                activity.updateList();
            }
        }
    }
}
