package ru.javaops.android.tamagotchi;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import ru.javaops.android.tamagotchi.utils.SoundHelper;

import static ru.javaops.android.tamagotchi.WalkActivity.INTENT_PET_TYPE;

public class MainActivity extends AppCompatActivity {

    private MenuItem soundCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
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

    public void onButtonClick(View view) {
        Intent intent = new Intent(MainActivity.this, OtherActivity.class);
        startActivity(intent);
    }

    private void initViews() {
        Button cat = findViewById(R.id.button_cat);
        Button dog = findViewById(R.id.button_dog);
        Button cthulhu = findViewById(R.id.button_cthulhu);

        final View.OnClickListener buttonListener = initButtonListener();
        cat.setOnClickListener(buttonListener);
        dog.setOnClickListener(buttonListener);
        cthulhu.setOnClickListener(buttonListener);
    }

    private View.OnClickListener initButtonListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                Intent intent = new Intent(MainActivity.this, WalkActivity.class);
                intent.putExtra(INTENT_PET_TYPE, button.getText().toString().toUpperCase());
                startActivity(intent);
            }
        };
    }
}
