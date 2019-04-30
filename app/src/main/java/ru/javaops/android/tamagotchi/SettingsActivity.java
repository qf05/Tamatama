package ru.javaops.android.tamagotchi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import ru.javaops.android.tamagotchi.db.DataBase;
import ru.javaops.android.tamagotchi.enums.NameCheckStatus;
import ru.javaops.android.tamagotchi.enums.PetsType;
import ru.javaops.android.tamagotchi.model.Pet;
import ru.javaops.android.tamagotchi.utils.PetUtils;
import ru.javaops.android.tamagotchi.utils.PrefsUtils;

public class SettingsActivity extends AppCompatActivity {

    private Spinner spinnerCreate;
    private EditText inputName;
    private AlertDialog dialog;
    private View.OnClickListener okCreateListener;
    private View.OnClickListener cancelListener;
    private DataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initListeners();
        db = DataBase.getAppDatabase(getApplicationContext());
    }

    public void goBack(View view) {
        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void createPet(View view) {
        final View layout = getLayoutInflater().inflate(R.layout.dialog_create_pet, null);
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

    }

    public void deletePet(View view) {

    }

    public void changePetName(View view) {

    }

    private void initListeners() {
        okCreateListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = inputName.getText().toString().trim();
                NameCheckStatus nameCheckStatus = PetUtils.checkName(name);
                if (nameCheckStatus != NameCheckStatus.CORRECT) {
                    makeMessage(nameCheckStatus.getMessageId());
                } else {
                    PetsType[] petsTypes = PetsType.values();
                    PetsType petsType = petsTypes[spinnerCreate.getSelectedItemPosition()];
                    Log.d("SELECTED_PET", petsType.toString() + "   " + name);
                    Pet pet = new Pet(name, petsType);
                    long id = db.petDao().insert(pet);
                    PrefsUtils.saveSelectedPetId(SettingsActivity.this, id);
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

    private void makeMessage(int messageId) {
        Toast toast = Toast.makeText(this, messageId, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
