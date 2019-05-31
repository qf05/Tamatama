package ru.javaops.android.tamagotchi.ui;

import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import ru.javaops.android.tamagotchi.utils.ViewHelper;

public abstract class BaseActivity extends AppCompatActivity {

    public void goBack(View view) {
        finish();
    }

    public void makeMessage(int messageId) {
        ViewHelper.makeMessage(this, messageId);
    }

    public void makeMessage(String message) {
        ViewHelper.makeMessage(this, message);
    }

    protected void initGoBackListener(ImageView view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
