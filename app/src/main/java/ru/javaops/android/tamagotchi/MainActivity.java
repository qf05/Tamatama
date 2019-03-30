package ru.javaops.android.tamagotchi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import ru.javaops.android.tamagotchi.db.DataBase;
import ru.javaops.android.tamagotchi.model.Pet;

import static ru.javaops.android.tamagotchi.WalkActivity.INTENT_PET_TO_WALK;

public class MainActivity extends AppCompatActivity {

    public static final String APP_PREFERENCES = "PREFERENCES";
    public static final String PREFERENCES_SELECTED_PET = "SELECTED_PET";
    private static final String PREFERENCES_SOUND_ON = "SOUND_ON";

    public static Pet selectedPet;
    private static boolean soundOn = true;
    private DataBase db;
    private SharedPreferences settings;
    private MenuItem soundCheckbox;
    private TextView petName;
    private ImageView petView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        db = DataBase.getAppDatabase(getApplicationContext());
        selectedPet = db.petDao().findAny();
        settings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        soundOn = settings.getBoolean(PREFERENCES_SOUND_ON, true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        long petId = settings.getLong(PREFERENCES_SELECTED_PET, -1);
        if (petId >= 0) {
            selectedPet = db.petDao().findById(petId);
            if (selectedPet == null) {
                selectedPet = db.petDao().findAny();
                if (selectedPet != null) {
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putLong(PREFERENCES_SELECTED_PET, selectedPet.getId());
                    editor.apply();
                }
            }
        }
        if (selectedPet != null) {
            petName.setText(selectedPet.getName());
            petView.setImageResource(selectedPet.getPetsType().getDrawableResource());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        soundCheckbox = menu.findItem(R.id.offSound);
        soundCheckbox.setChecked(soundOn);
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
                soundOn = !soundOn;
                soundCheckbox.setChecked(soundOn);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean(PREFERENCES_SOUND_ON, soundOn);
                editor.apply();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initViews() {
        petName = findViewById(R.id.pet_name);
        petView = findViewById(R.id.pet_view);
    }

    public void goWalk(View view) {
        if (selectedPet != null) {
            Intent intent = new Intent(MainActivity.this, WalkActivity.class);
            intent.putExtra(INTENT_PET_TO_WALK, selectedPet);
            startActivity(intent);
        }
    }

    public void onButtonClick(View view) {
        Intent intent = new Intent(MainActivity.this, OtherActivity.class);
        startActivity(intent);
    }

    public static boolean isSoundOn() {
        return soundOn;
    }
}
