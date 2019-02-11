package ru.javaops.android.tamagotchi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import ru.javaops.android.tamagotchi.adapters.PetDataBaseAdapter;
import ru.javaops.android.tamagotchi.enums.PetsType;
import ru.javaops.android.tamagotchi.model.Pet;

public class MainActivity extends AppCompatActivity {

    public static final String APP_PREFERENCES = "PREFERENCES";
    public static final String PREFERENCES_SELECTED_PET = "SELECTED_PET";

    public static Pet SELECTED_PET;
    public static boolean SOUND_OFF = false;
    private MenuItem soundCheckbox;
    private TextView petName;
    private ImageView petView;
    public static List<Pet> PETS = new ArrayList<>();
    public static PetDataBaseAdapter db;
    private SharedPreferences settings;
    public static Handler handler;
    private static long selectedPetId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        petName = findViewById(R.id.petName);
        petView = findViewById(R.id.petView);

        settings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        db = PetDataBaseAdapter.getInstance(this);
        if (settings.contains(PREFERENCES_SELECTED_PET)) {
            selectedPetId = settings.getLong(PREFERENCES_SELECTED_PET, -1);
        }
        new UpdateView().execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler = new MyHandler(this);
        handler.sendEmptyMessage(0);
    }

    private void updateView() {
        if (PETS.size() > 0 && (SELECTED_PET == null || !PETS.contains(SELECTED_PET))) {
            SELECTED_PET = PETS.get(0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putLong(PREFERENCES_SELECTED_PET, SELECTED_PET.getId());
            editor.apply();
        }

        if (SELECTED_PET != null) {
            petName.setText(SELECTED_PET.getName());
            switch (PetsType.valueOf(SELECTED_PET.getType())) {
                case CAT:
                    petView.setImageResource(R.drawable.cat_small);
                    break;
                case DOG:
                    petView.setImageResource(R.drawable.dog_small);
                    break;
                case CTHULHU:
                    petView.setImageResource(R.drawable.cthulhu_small);
                    break;
            }
        }
    }

    public void goWalk(View view) {
        if (SELECTED_PET != null) {
            Intent intent = new Intent(MainActivity.this, WalkActivity.class);
            startActivity(intent);
        }
    }

    public void onButtonClick(View view) {
        Intent intent = new Intent(MainActivity.this, OtherActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        soundCheckbox = menu.findItem(R.id.offSound);
        soundCheckbox.setChecked(!SOUND_OFF);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.petSettings:
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.alarmSettings:

                return true;
            case R.id.showHistory:

                return true;
            case R.id.offSound:
                SOUND_OFF = !SOUND_OFF;
                soundCheckbox.setChecked(!SOUND_OFF);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private static class UpdateView extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            PETS = db.getAll();
            if (selectedPetId >= 0) {
                SELECTED_PET = db.findById(selectedPetId);
            }
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

    @Override
    protected void onStop() {
        super.onStop();
        handler = null;
    }

    private static class MyHandler extends Handler {

        WeakReference<MainActivity> wrActivity;

        private MyHandler(MainActivity activity) {
            wrActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MainActivity activity = wrActivity.get();
            if (activity != null) {
                activity.updateView();
            }
        }
    }
}
