package ru.javaops.android.tamagotchi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ru.javaops.android.tamagotchi.R;
import ru.javaops.android.tamagotchi.db.DataBase;
import ru.javaops.android.tamagotchi.model.Pet;
import ru.javaops.android.tamagotchi.utils.SoundHelper;

public class MainActivity extends BasePetActivity {

    private DataBase db;
    private MenuItem soundCheckbox;
    private TextView petName;
    private ImageView petView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = DataBase.getAppDatabase(this);
        initViews();
    }

    @Override
    protected void updateView(Pet pet) {
        super.updateView(pet);
        petName.setText(getPet().getName());
        petView.setImageResource(getPet().getPetsType().getPetDrawableResource());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        soundCheckbox = menu.findItem(R.id.off_sound);
        soundCheckbox.setChecked(SoundHelper.isSoundOn());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.pet_settings:
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.alarm_settings:
                break;
            case R.id.show_history:
                break;
            case R.id.off_sound:
                SoundHelper.switchSoundState(this);
                soundCheckbox.setChecked(SoundHelper.isSoundOn());
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void goBack(View view) {
        onResume();
    }

    public void goWalk(View view) {
        if (getPet() != null) {
            Intent intent = new Intent(MainActivity.this, WalkActivity.class);
            startActivity(intent);
        }
    }

    public void onButtonClick(View view) {
        Intent intent = new Intent(MainActivity.this, OtherActivity.class);
        startActivity(intent);
    }

    public void lvlUp(View view) {
        getPet().incLvl();
        db.petDao().update(getPet());
    }

    private void initViews() {
        petName = findViewById(R.id.pet_name);
        petView = findViewById(R.id.pet_view);
    }
}
