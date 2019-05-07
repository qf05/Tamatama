package ru.javaops.android.tamagotchi.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.javaops.android.tamagotchi.R;
import ru.javaops.android.tamagotchi.adapters.PetAdapter;
import ru.javaops.android.tamagotchi.db.DataBase;
import ru.javaops.android.tamagotchi.model.Pet;
import ru.javaops.android.tamagotchi.utils.CompareUtils;
import ru.javaops.android.tamagotchi.utils.ViewHelper;

public class DeletePetActivity extends BaseActivity {

    private Spinner sortSpinner;
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
                    List<Pet> petList = db.petDao().getAll();
                    adapter.updateData(petList, sortSpinner.getSelectedItemPosition());
                    makeMessage(getResources().getQuantityString(R.plurals.was_delete_plurals,
                            deleteList.size(), deleteList.size()));
                    dialog.cancel();
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
        TextView header = findViewById(R.id.change_header);
        header.setText(R.string.delete_pet);

        final RecyclerView rv = findViewById(R.id.change_rv);
        ViewHelper.setParametersRv(DeletePetActivity.this, rv);
        adapter = new PetAdapter(pets, null);
        rv.setAdapter(adapter);

        sortSpinner = findViewById(R.id.change_sort);
        sortSpinner.setOnItemSelectedListener(CompareUtils.getSpinnerClickListener(adapter, pets));
    }
}