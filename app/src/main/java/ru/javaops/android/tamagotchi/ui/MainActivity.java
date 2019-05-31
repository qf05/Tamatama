package ru.javaops.android.tamagotchi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import ru.javaops.android.tamagotchi.R;
import ru.javaops.android.tamagotchi.databinding.ActivityMainBinding;
import ru.javaops.android.tamagotchi.utils.SoundHelper;
import ru.javaops.android.tamagotchi.viewmodel.MainViewModel;

public class MainActivity extends BasePetActivity {

    private MainViewModel model;
    private MenuItem soundCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SoundHelper.initialSoundPool(getApplicationContext());
        model = createModel(MainViewModel.class);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);
        binding.setModel(model);
    }

    @Override
    protected void onResume() {
        super.onResume();
        model.notifyChange();
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
        model.notifyChange();
    }

    public void onButtonClick(View view) {
        Intent intent = new Intent(MainActivity.this, OtherActivity.class);
        startActivity(intent);
    }
}
