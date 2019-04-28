package ru.javaops.android.tamagotchi;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import ru.javaops.android.tamagotchi.model.Pet;
import ru.javaops.android.tamagotchi.utils.PetUtils;
import ru.javaops.android.tamagotchi.utils.SoundHelper;

public class MainActivity extends AppCompatActivity {

    private Pet selectedPet;
    private MenuItem soundCheckbox;
    private TextView petName;
    private ImageView petView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        selectedPet = PetUtils.getSelectedPet();
        if (selectedPet != null) {
            petName.setText(selectedPet.getName());
            int imageResource = 0;
            switch (selectedPet.getPetsType()) {
                case CAT:
                    imageResource = R.drawable.cat_small;
                    break;
                case DOG:
                    imageResource = R.drawable.dog_small;
                    break;
                case CTHULHU:
                    imageResource = R.drawable.cthulhu_small;
                    break;
            }
            petView.setImageResource(imageResource);
        }
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
                SoundHelper.switchSoundState();
                soundCheckbox.setChecked(SoundHelper.isSoundOn());
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void initViews() {
        petName = findViewById(R.id.pet_name);
        petView = findViewById(R.id.pet_view);
    }

    public void goWalk(View view) {
        if (selectedPet != null) {
            Intent intent = new Intent(MainActivity.this, WalkActivity.class);
            startActivity(intent);
        }
    }

    public void onButtonClick(View view) {
        Intent intent = new Intent(MainActivity.this, OtherActivity.class);
        startActivity(intent);
    }
}
