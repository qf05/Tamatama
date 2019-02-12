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
import ru.javaops.android.tamagotchi.enums.PetsType;
import ru.javaops.android.tamagotchi.model.Pet;

import static ru.javaops.android.tamagotchi.WalkActivity.INTENT_PET_TO_WALK;

public class MainActivity extends AppCompatActivity {

    public static final String APP_PREFERENCES = "PREFERENCES";
    public static final String PREFERENCES_SELECTED_PET = "SELECTED_PET";

    private static Pet selectedPet;
    public static boolean SOUND_OFF = false;
    private MenuItem soundCheckbox;
    private TextView petName;
    private ImageView petView;
    private static DataBase db;
    private SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        petName = findViewById(R.id.petName);
        petView = findViewById(R.id.petView);
        settings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        db = DataBase.getAppDatabase(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (settings.contains(PREFERENCES_SELECTED_PET)) {
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
        }

        if (selectedPet != null) {
            petName.setText(selectedPet.getName());
            switch (PetsType.valueOf(selectedPet.getType())) {
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
}
