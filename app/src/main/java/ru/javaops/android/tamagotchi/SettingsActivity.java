package ru.javaops.android.tamagotchi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.concurrent.Callable;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import ru.javaops.android.tamagotchi.db.DataBase;
import ru.javaops.android.tamagotchi.enums.PetsType;
import ru.javaops.android.tamagotchi.model.Pet;
import ru.javaops.android.tamagotchi.utils.PetUtils;

import static ru.javaops.android.tamagotchi.MainActivity.APP_PREFERENCES;
import static ru.javaops.android.tamagotchi.MainActivity.PREFERENCES_SELECTED_PET;

public class SettingsActivity extends AppCompatActivity {
    private Spinner spinnerCreate;
    private EditText inputName;
    private AlertDialog dialog;
    private static DataBase db;
    private static SharedPreferences settings;
//    private static PetUtils petUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        db = DataBase.getAppDatabase(this);
        settings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

//        petUtils = ViewModelProviders.of(this).get(PetUtils.class);
//        // Create the observer which updates the UI.
//        final Observer<Pet> nameObserver = new Observer<Pet>() {
//            @Override
//            public void onChanged(@Nullable final Pet pet) {
//                // Update the UI, in this case, a TextView.
//                SELECTED_PET = pet;
//                updateView();
//            }
//        };
//        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
//        petUtils.getCurrentPet().observe(this, nameObserver);

    }

    public void createPet(View view) {
        View layout = getLayoutInflater().inflate(R.layout.dialog_create_pet, null);
        spinnerCreate = layout.findViewById(R.id.inputTypePet);
        inputName = layout.findViewById(R.id.inputName);
        Button ok = layout.findViewById(R.id.okCreate);
        ok.setOnClickListener(okCreateListener);
        Button cancel = layout.findViewById(R.id.cancelCreate);
        cancel.setOnClickListener(cancelListener);

        dialog = new AlertDialog.Builder(this)
                .setView(layout)
                .setCancelable(false)
                .create();
        dialog.show();
    }

    final View.OnClickListener okCreateListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final String name = inputName.getText().toString().trim();
            if (PetUtils.checkName(SettingsActivity.this, name)) {
                PetsType[] petsTypes = PetsType.values();
                final PetsType petsType = petsTypes[spinnerCreate.getSelectedItemPosition()];
                Log.i("SELECTED_PET", petsType.toString() + "   " + name);

//                new SaveNewPet().execute(name, petsType.toString());
                try {
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putLong(PREFERENCES_SELECTED_PET, new Callable<Long>() {
                        @Override
                        public Long call() {
                            try {
                                Thread.sleep(4000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            return db.petDao().insert(new Pet(name, petsType));
                        }
                    }.call());
                    editor.apply();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dialog.cancel();
                finish();
            }
        }
    };

//    private static class SaveNewPet extends AsyncTask<String, Void, Void> {
//
//        @Override
//        protected Void doInBackground(String... strings) {
//            long id = db.petDao().insert(new Pet(strings[0], PetsType.valueOf(strings[1])));
////            PETS = db.petDao().getAll();
////            SELECTED_PET = db.petDao().findById(id);
//            SharedPreferences.Editor editor = settings.edit();
//            editor.putLong(PREFERENCES_SELECTED_PET, id);
//            editor.apply();
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            new MainActivity.UpdateView().execute();
//        }
//    }

    View.OnClickListener cancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog.cancel();
        }
    };


    public void changePet(View view) {
        Intent intent = new Intent(SettingsActivity.this, ChangePetActivity.class);
        startActivity(intent);
        finish();
    }

    public void deletePet(View view) {

    }

    public void changeNamePet(View view) {

    }

    public void goBack(View view) {
        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
