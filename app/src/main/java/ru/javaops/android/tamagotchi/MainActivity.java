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

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import ru.javaops.android.tamagotchi.db.DataBase;
import ru.javaops.android.tamagotchi.enums.PetsType;
import ru.javaops.android.tamagotchi.model.Pet;
import ru.javaops.android.tamagotchi.utils.PetUtils;

public class MainActivity extends AppCompatActivity {

    public static final String APP_PREFERENCES = "PREFERENCES";
    public static final String PREFERENCES_SELECTED_PET = "SELECTED_PET";

    public static Pet SELECTED_PET;
    public static boolean SOUND_OFF = false;
    private MenuItem soundCheckbox;
    private TextView petName;
    private ImageView petView;
    public static List<Pet> PETS = new ArrayList<>();
    public static DataBase db;
    private static SharedPreferences settings;
    private static long selectedPetId;
    private static PetUtils mainPetView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        petName = findViewById(R.id.petName);
        petView = findViewById(R.id.petView);

        settings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        db = DataBase.getAppDatabase(this);

        mainPetView = ViewModelProviders.of(this).get(PetUtils.class);
        mainPetView.getCurrentPet().observe(this, new Observer<Pet>() {
            @Override
            public void onChanged(@Nullable final Pet pet) {
                SELECTED_PET = pet;
                updateView();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Runnable() {
            @Override
            public void run() {
                if (settings.contains(PREFERENCES_SELECTED_PET)) {
                    selectedPetId = settings.getLong(PREFERENCES_SELECTED_PET, -1);
                }
                Pet pet = null;
                if (selectedPetId >= 0) {
                    pet = db.petDao().findById(selectedPetId);
                    if (pet == null) {
                        pet = db.petDao().findAny();
                        if (pet != null) {
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putLong(PREFERENCES_SELECTED_PET, pet.getId());
                            editor.apply();
                        }
                    }
                }
                mainPetView.getCurrentPet().postValue(pet);
            }
        }.run();
//        new UpdateView().execute();
    }

    private void updateView() {
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

//    public static class UpdateView extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//
//        }
//    }

    @Override
    protected void onStop() {
        super.onStop();
    }

//    private static class MyHandler extends Handler {
//
//        WeakReference<MainActivity> wrActivity;
//
//        private MyHandler(MainActivity activity) {
//            wrActivity = new WeakReference<>(activity);
//        }
//
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            MainActivity activity = wrActivity.get();
//            if (activity != null) {
//                activity.updateView();
//            }
//        }
//    }
}
