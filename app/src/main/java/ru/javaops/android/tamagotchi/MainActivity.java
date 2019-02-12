package ru.javaops.android.tamagotchi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import ru.javaops.android.tamagotchi.enums.PetsType;

import static ru.javaops.android.tamagotchi.WalkActivity.INTENT_PET_TYPE;

public class MainActivity extends AppCompatActivity {

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
}
