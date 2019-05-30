package ru.javaops.android.tamagotchi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import ru.javaops.android.tamagotchi.adapters.MySpinnerAdapter;
import ru.javaops.android.tamagotchi.db.DataBase;
import ru.javaops.android.tamagotchi.enums.NameCheckStatus;
import ru.javaops.android.tamagotchi.enums.PetsType;
import ru.javaops.android.tamagotchi.model.Pet;
import ru.javaops.android.tamagotchi.utils.PetUtils;
import ru.javaops.android.tamagotchi.utils.PrefsUtils;

public class SettingsActivity extends AppCompatActivity {

    private Spinner spinnerCreate;
    private TextView selectedPetName;
    private EditText inputName;
    private Button cancelButton;
    private Button okButton;
    private AlertDialog dialog;
    private View.OnClickListener okCreateListener;
    private View.OnClickListener cancelListener;
    private OkChangeClickListener okChangeClickListener;
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
        initSpinner(layout);
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
        Intent intent = new Intent(SettingsActivity.this, DeletePetActivity.class);
        startActivity(intent);
        finish();
    }

    public void changePetName(View view) {
        final Pet pet = PetUtils.getSelectedPet(this);
        if (pet != null) {
            View layout = getLayoutInflater().inflate(R.layout.dialog_change_name, null);
            initViews(layout);
            selectedPetName.setText(getString(R.string.this_name, pet.getName()));
            cancelButton.setOnClickListener(cancelListener);
            okButton.setOnClickListener(okChangeClickListener.setPet(pet));
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(layout).setCancelable(false);
            dialog = builder.create();
            dialog.show();
        }
    }

    private void initViews(View layout) {
        selectedPetName = layout.findViewById(R.id.selected_pet_name);
        inputName = layout.findViewById(R.id.input_name);
        cancelButton = layout.findViewById(R.id.cancel_change_name);
        okButton = layout.findViewById(R.id.ok_change_name);
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

        okChangeClickListener = new OkChangeClickListener();
    }

    private class OkChangeClickListener implements View.OnClickListener {
        private Pet pet;

        public OkChangeClickListener setPet(Pet pet) {
            this.pet = pet;
            return this;
        }

        @Override
        public void onClick(View v) {
            String name = inputName.getText().toString().trim();
            NameCheckStatus nameCheckStatus = PetUtils.checkName(name);
            if (nameCheckStatus != NameCheckStatus.CORRECT) {
                makeMessage(nameCheckStatus.getMessageId());
            } else {
                pet.setName(name);
                db.petDao().update(pet);
                dialog.cancel();
            }
        }
    }

    private void makeMessage(int messageId) {
        Toast toast = Toast.makeText(this, messageId, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void initSpinner(View layout) {
        Spinner spinner = layout.findViewById(R.id.input_type_pet);
        String[] stringArray = getResources().getStringArray(R.array.pets);
        SpinnerAdapter spinnerAdapter =
                new MySpinnerAdapter(this, stringArray);
        spinner.setAdapter(spinnerAdapter);
    }
}
