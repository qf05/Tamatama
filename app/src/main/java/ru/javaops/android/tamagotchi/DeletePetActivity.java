package ru.javaops.android.tamagotchi;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import ru.javaops.android.tamagotchi.adapters.PetAdapter;
import ru.javaops.android.tamagotchi.db.DataBase;
import ru.javaops.android.tamagotchi.model.Pet;
import ru.javaops.android.tamagotchi.utils.ViewHelper;

public class DeletePetActivity extends AppCompatActivity {

    private PetAdapter adapter;
    private List<Pet> pets;
    private static DataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pet);
        db = DataBase.getAppDatabase(DeletePetActivity.this);
        pets = db.petDao().getAll();
        initViews();
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
                        adapter.updateData(db.petDao().getAll());
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initViews() {
        TextView header = findViewById(R.id.change_header);
        header.setText(R.string.delete_pet);

        final RecyclerView rv = findViewById(R.id.change_rv);
        ViewHelper.setParametersRv(DeletePetActivity.this, rv);
        adapter = new PetAdapter(pets, null);
        rv.setAdapter(adapter);
    }

    public void goBack(View view) {
        Intent intent = new Intent(DeletePetActivity.this, SettingsActivity.class);
        startActivity(intent);
        finish();
    }
}
