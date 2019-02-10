package ru.javaops.android.tamagotchi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OtherActivity extends AppCompatActivity {

    private int countAnimationRepeat = 1;
    private Animation translateAnimation;
    private TextView textView;
    private float height;
    private int width;
    private float yDelta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        ImageButton home = findViewById(R.id.toHome);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtherActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        textView = findViewById(R.id.hello);
        final Animation helloAnimation = AnimationUtils.loadAnimation(this, R.anim.hello);
        final AnimationSet animationSet = new AnimationSet(true);
        final FrameLayout frameLayout = findViewById(R.id.layout);
        // http://poetofcode.ru/programming/2017/06/12/kak-opredelit-nachalnyue-razmeryu-view-v-android.html
        ViewHelper.executeAfterViewHasDrawn(textView, new Runnable() {
            @Override
            public void run() {
                // Получаем размеры
                height = frameLayout.getHeight() - textView.getHeight();
                // -(int)textView.getX() требуется, потому что у textView начальное положение не равно 0 по оси X (указано свойство android:layout_gravity="center_horizontal")
                // в этом случае width будет равен 0
                width = frameLayout.getWidth() / 2 - textView.getWidth() / 2 - (int) textView.getX();

                //создаем анимацию
                translateAnimation = new TranslateAnimation(width, width, textView.getY(), height);
                translateAnimation.setDuration(2000);
                translateAnimation.setStartOffset(4000);
                translateAnimation.setAnimationListener(animationListener);

                animationSet.addAnimation(translateAnimation);
                animationSet.addAnimation(helloAnimation);
                textView.startAnimation(animationSet);
            }
        });
    }

    Animation.AnimationListener animationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            countAnimationRepeat++;
            if (countAnimationRepeat < 10) {
                if (countAnimationRepeat % 2 == 0) {
                    yDelta = height - (height / countAnimationRepeat);
                    translateAnimation = new TranslateAnimation(width, width, height, yDelta);
                    startAnimation();
                } else {
                    translateAnimation = new TranslateAnimation(width, width, yDelta, height);
                    startAnimation();
                }
            } else {
                textView.setX(width + (int) textView.getX());
                textView.setY(height);
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    private void startAnimation() {
        translateAnimation.setDuration(2000 / countAnimationRepeat);
        translateAnimation.setAnimationListener(animationListener);
        textView.startAnimation(translateAnimation);
    }
}