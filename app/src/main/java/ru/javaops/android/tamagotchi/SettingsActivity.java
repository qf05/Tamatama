package ru.javaops.android.tamagotchi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import ru.javaops.android.tamagotchi.adapters.PetDataBaseAdapter;
import ru.javaops.android.tamagotchi.enums.PetsType;
import ru.javaops.android.tamagotchi.model.Pet;
import ru.javaops.android.tamagotchi.utils.PetUtils;

import static ru.javaops.android.tamagotchi.MainActivity.APP_PREFERENCES;
import static ru.javaops.android.tamagotchi.MainActivity.PETS;
import static ru.javaops.android.tamagotchi.MainActivity.PREFERENCES_SELECTED_PET;
import static ru.javaops.android.tamagotchi.MainActivity.SELECTED_PET;

public class SettingsActivity extends AppCompatActivity {
    private Spinner spinnerCreate;
    private EditText inputName;
    private AlertDialog dialog;
    private static PetDataBaseAdapter db;
    private SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        db = PetDataBaseAdapter.getInstance(this);
        settings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
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
                savePet(name, petsType, settings);
                dialog.cancel();
                finish();

            }
        }
    };

    private static void savePet(final String name, final PetsType petsType, final SharedPreferences settings) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                long id = db.insert(new Pet(name, petsType));
                PETS = db.getAll();
                SELECTED_PET = db.findById(id);
                SharedPreferences.Editor editor = settings.edit();
                editor.putLong(PREFERENCES_SELECTED_PET, SELECTED_PET.getId());
                editor.apply();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (MainActivity.handler != null) {
                    MainActivity.handler.sendEmptyMessage(0);
                }
            }
        };
    }

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
