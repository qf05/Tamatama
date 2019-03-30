package ru.javaops.android.tamagotchi;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import ru.javaops.android.tamagotchi.adapters.DeleteRVAdapter;
import ru.javaops.android.tamagotchi.db.DataBase;
import ru.javaops.android.tamagotchi.model.Pet;
import ru.javaops.android.tamagotchi.utils.Utils;

@SuppressLint("InflateParams")
public class DeletePetActivity extends AppCompatActivity implements DeleteRVAdapter.ItemClickListener {

    private DeleteRVAdapter adapter;
    private ArrayMap<Integer, View> deleteMap = new ArrayMap<>();
    private List<Pet> pets;
    private static DataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_pet);
        db = DataBase.getAppDatabase(DeletePetActivity.this);
        final RecyclerView rv = findViewById(R.id.delete_rv);
        Utils.setParametersRv(DeletePetActivity.this, rv);
        pets = DataBase.getAppDatabase(getApplicationContext()).petDao().getAll();
        Spinner sortSpinner = findViewById(R.id.deleteSort);
        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Utils.sort(pets, position);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Utils.sort(pets, 0);
            }
        });

        adapter = new DeleteRVAdapter(pets);
        adapter.setClickListener(DeletePetActivity.this);
        rv.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.delete, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                final View layout = getLayoutInflater().inflate(R.layout.dialog_delete, null);
                final Button ok = layout.findViewById(R.id.okDelete);
                final Button cancel = layout.findViewById(R.id.cancelDelete);
                final AlertDialog dialog = new AlertDialog.Builder(this)
                        .setView(layout)
                        .setCancelable(false)
                        .create();
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (deleteMap.size() > 0) {
                            for (int i : deleteMap.keySet()) {
                                db.petDao().delete(pets.get(i));
                                CheckBox checkBox = (CheckBox) deleteMap.get(i);
                                if (checkBox != null) {
                                    checkBox.setChecked(false);
                                }
                            }
                            pets.clear();
                            pets.addAll(db.petDao().getAll());
                            adapter.notifyDataSetChanged();
                        }
                        dialog.cancel();
                        Toast toast = Toast.makeText(DeletePetActivity.this,
                                getString(R.string.was_delete) + " "
                                        + deleteMap.size() + " "
                                        + getString(R.string.pets2),
                                Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        deleteMap.clear();
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        final CheckBox checkBox = view.findViewById(R.id.checkbox_delete);
        checkBox.setChecked(!checkBox.isChecked());
        if (checkBox.isChecked()) {
            deleteMap.put(position, checkBox);
            Log.i("CHECKBOX", "add " + position);
        } else {
            if (deleteMap.containsKey(position)) {
                deleteMap.remove(position);
                Log.i("CHECKBOX", "remove " + position);
            }
        }
    }

    public void goBack(View view) {
        finish();
    }
}
