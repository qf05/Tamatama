package ru.javaops.android.tamagotchi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import ru.javaops.android.tamagotchi.db.DataBase;
import ru.javaops.android.tamagotchi.enums.PetsType;
import ru.javaops.android.tamagotchi.model.Pet;
import ru.javaops.android.tamagotchi.utils.PetUtils;

import static ru.javaops.android.tamagotchi.MainActivity.selectedPet;

public class SettingsActivity extends AppCompatActivity {
    private Spinner spinnerCreate;
    private EditText inputName;
    private AlertDialog dialog;
    private DataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        db = DataBase.getAppDatabase(getApplicationContext());
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

    View.OnClickListener okCreateListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String name = inputName.getText().toString().trim();
            if (PetUtils.checkName(SettingsActivity.this, name)) {
                PetsType[] petsTypes = PetsType.values();
                PetsType petsType = petsTypes[spinnerCreate.getSelectedItemPosition()];
                Log.i("SELECTED_PET", petsType.toString() + "   " + name);
                long id = db.petDao().insert(new Pet(name, petsType));
                selectedPet = db.petDao().findById(id);
                dialog.cancel();
                finish();
            }
        }
    };

    View.OnClickListener cancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog.cancel();
        }
    };


    public void changePet(View view) {

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
