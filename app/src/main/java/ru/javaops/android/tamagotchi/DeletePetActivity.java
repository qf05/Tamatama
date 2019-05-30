package ru.javaops.android.tamagotchi;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.javaops.android.tamagotchi.adapters.MySpinnerAdapter;
import ru.javaops.android.tamagotchi.adapters.PetAdapter;
import ru.javaops.android.tamagotchi.db.DataBase;
import ru.javaops.android.tamagotchi.model.Pet;
import ru.javaops.android.tamagotchi.utils.CompareUtils;
import ru.javaops.android.tamagotchi.utils.ViewHelper;

public class DeletePetActivity extends AppCompatActivity {

    private Spinner sortSpinner;
    private PetAdapter adapter;
    private List<Pet> pets;
    private static DataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);
        db = DataBase.getAppDatabase(DeletePetActivity.this);
        pets = db.petDao().getAll();
        initViews();
        initSpinner();
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.delete, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete) {
            final View layout = getLayoutInflater().inflate(R.layout.dialog_delete, null);
            final Button ok = layout.findViewById(R.id.ok_delete);
            final Button cancel = layout.findViewById(R.id.cancel_delete);
            final AlertDialog dialog = new AlertDialog.Builder(this)
                    .setView(layout)
                    .setCancelable(false)
                    .create();
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final List<Pet> deleteList = adapter.getDeleteList();
                    db.petDao().delete(deleteList);
                    adapter.clearDeleteMap();
                    List<Pet> petList = db.petDao().getAll();
                    adapter.updateData(petList, sortSpinner.getSelectedItemPosition());
                    dialog.cancel();
                    Toast toast = Toast.makeText(DeletePetActivity.this,
                            getResources().getQuantityString(R.plurals.was_delete_plurals,
                                    deleteList.size(), deleteList.size()),
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });
            dialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        TextView header = findViewById(R.id.list_header);
        header.setText(R.string.delete_pet);

        final RecyclerView rv = findViewById(R.id.pets_rv);
        ViewHelper.setParametersRv(DeletePetActivity.this, rv);
        adapter = new PetAdapter(pets, null);
        rv.setAdapter(adapter);

        sortSpinner = findViewById(R.id.spinner_sort);
        sortSpinner.setOnItemSelectedListener(CompareUtils.getSpinnerClickListener(adapter, pets));
    }

    public void goBack(View view) {
        Intent intent = new Intent(DeletePetActivity.this, SettingsActivity.class);
        startActivity(intent);
        finish();
    }

    private void initSpinner() {
        Spinner spinner = findViewById(R.id.spinner_sort);
        String[] stringArray = getResources().getStringArray(R.array.sort);
        SpinnerAdapter spinnerAdapter =
                new MySpinnerAdapter(this, stringArray, false);
        spinner.setAdapter(spinnerAdapter);
    }
}
