package ru.javaops.android.tamagotchi;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import ru.javaops.android.tamagotchi.enums.PetsType;

import static ru.javaops.android.tamagotchi.WalkActivity.INTENT_PET_TYPE;

public class MainActivity extends AppCompatActivity {

    public static boolean SOUND_OFF = false;
    private MenuItem soundCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button cat = findViewById(R.id.buttonCat);
        Button dog = findViewById(R.id.buttonDog);
        Button cthulhu = findViewById(R.id.buttonCthulhu);
        cat.setOnClickListener(buttonListener);
        dog.setOnClickListener(buttonListener);
        cthulhu.setOnClickListener(buttonListener);
    }

    View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, WalkActivity.class);
            switch (v.getId()) {
                case R.id.buttonCat:
                    intent.putExtra(INTENT_PET_TYPE, PetsType.CAT.toString());
                    break;
                case R.id.buttonDog:
                    intent.putExtra(INTENT_PET_TYPE, PetsType.DOG.toString());
                    break;
                case R.id.buttonCthulhu:
                    intent.putExtra(INTENT_PET_TYPE, PetsType.CTHULHU.toString());
                    break;
            }
            startActivity(intent);
        }
    };

    public void onButtonClick(View view) {
        Intent intent = new Intent(MainActivity.this, OtherActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        soundCheckbox = menu.findItem(R.id.offSound);
        soundCheckbox.setChecked(!SOUND_OFF);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.petSettings:
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.alarmSettings:

                return true;
            case R.id.showHistory:

                return true;
            case R.id.offSound:
                SOUND_OFF = !SOUND_OFF;
                soundCheckbox.setChecked(!SOUND_OFF);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
