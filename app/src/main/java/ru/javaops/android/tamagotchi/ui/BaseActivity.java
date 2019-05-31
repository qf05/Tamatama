package ru.javaops.android.tamagotchi.ui;

import android.annotation.SuppressLint;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    public void goBack(View view) {
        finish();
    }

    @SuppressLint("ShowToast")
    public void makeMessage(int messageId) {
        makeMessage(Toast.makeText(this, messageId, Toast.LENGTH_SHORT));
    }

    @SuppressLint("ShowToast")
    public void makeMessage(String message) {
        makeMessage(Toast.makeText(this, message, Toast.LENGTH_SHORT));
    }

    protected void initGoBackListener(ImageView view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void makeMessage(Toast toast) {
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
