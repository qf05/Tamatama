package ru.javaops.android.tamagotchi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import ru.javaops.android.tamagotchi.R;
import ru.javaops.android.tamagotchi.db.DataBase;
import ru.javaops.android.tamagotchi.enums.NameCheckStatus;
import ru.javaops.android.tamagotchi.model.Pet;
import ru.javaops.android.tamagotchi.utils.PetUtils;
import ru.javaops.android.tamagotchi.utils.ViewHelper;

public class SettingsActivity extends BaseActivity {

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

    public void createPet(View view) {
        ViewHelper.showCreatePetDialog(this, true);
    }

    public void changePet(View view) {
        Intent intent = new Intent(SettingsActivity.this, ChangePetActivity.class);
        startActivity(intent);
    }

    public void deletePet(View view) {
        Intent intent = new Intent(SettingsActivity.this, DeletePetActivity.class);
        startActivity(intent);
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
}
