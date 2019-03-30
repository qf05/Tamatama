package ru.javaops.android.tamagotchi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import ru.javaops.android.tamagotchi.db.DataBase;
import ru.javaops.android.tamagotchi.enums.PetsType;
import ru.javaops.android.tamagotchi.model.Pet;
import ru.javaops.android.tamagotchi.utils.PetUtils;

import static ru.javaops.android.tamagotchi.MainActivity.APP_PREFERENCES;
import static ru.javaops.android.tamagotchi.MainActivity.PREFERENCES_SELECTED_PET;

@SuppressLint("InflateParams")
public class SettingsActivity extends AppCompatActivity {

    private Spinner spinnerCreate;
    private EditText inputName;
    private AlertDialog dialog;
    private View.OnClickListener okCreateListener;
    private View.OnClickListener cancelListener;
    private DataBase db;
    private SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initListeners();
        db = DataBase.getAppDatabase(getApplicationContext());
        settings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    public void goBack(View view) {
        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void createPet(View view) {
        @SuppressLint("InflateParams") final View layout = getLayoutInflater().inflate(R.layout.dialog_create_pet, null);
        spinnerCreate = layout.findViewById(R.id.input_type_pet);
        inputName = layout.findViewById(R.id.input_name);
        layout.findViewById(R.id.ok_create).setOnClickListener(okCreateListener);
        layout.findViewById(R.id.cancel_create).setOnClickListener(cancelListener);

        dialog = new AlertDialog.Builder(this)
                .setView(layout)
                .setCancelable(false)
                .create();
        dialog.show();
    }

    public void changePet(View view) {
        Intent intent = new Intent(SettingsActivity.this, ChangePetActivity.class);
        startActivity(intent);
        finish();
    }

    public void deletePet(View view) {

    }

    public void changePetName(View view) {
        long petId = settings.getLong(PREFERENCES_SELECTED_PET, -1);
        if (petId >= 0) {
            final Pet pet = db.petDao().findById(petId);
            if (pet != null) {
                View layout = getLayoutInflater().inflate(R.layout.dialog_change_name, null);
                TextView selectedPetName = layout.findViewById(R.id.selectedPetName);
                selectedPetName.setText(String.format("%s %s", getString(R.string.this_name), pet.getName()));
                inputName = layout.findViewById(R.id.inputChangeName);
                Button cancel = layout.findViewById(R.id.cancelChangeName);
                cancel.setOnClickListener(cancelListener);
                Button ok = layout.findViewById(R.id.okChangeName);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = inputName.getText().toString().trim();
                        if (PetUtils.checkName(SettingsActivity.this, name)) {
                            pet.setName(name);
                            db.petDao().update(pet);
                            dialog.cancel();
                        }
                    }
                });
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setView(layout).setCancelable(false);
                dialog = builder.create();
                dialog.show();
            }
        }
    }

    private void initListeners() {
        okCreateListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = inputName.getText().toString().trim();
                if (PetUtils.checkName(SettingsActivity.this, name)) {
                    PetsType[] petsTypes = PetsType.values();
                    PetsType petsType = petsTypes[spinnerCreate.getSelectedItemPosition()];
                    Log.d("SELECTED_PET", petsType.toString() + "   " + name);
                    long id = db.petDao().insert(new Pet(name, petsType));
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putLong(PREFERENCES_SELECTED_PET, id);
                    editor.apply();
                    dialog.cancel();
                    finish();
                }
            }
        };

        cancelListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        };
    }
}
