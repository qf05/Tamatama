package ru.javaops.android.tamagotchi.ui;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import ru.javaops.android.tamagotchi.utils.ViewHelper;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        BaseActivity.this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

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
