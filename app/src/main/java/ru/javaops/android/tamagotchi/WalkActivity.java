package ru.javaops.android.tamagotchi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import ru.javaops.android.tamagotchi.enums.PetsType;

public class WalkActivity extends AppCompatActivity {
    public static final String INTENT_PET_TYPE = "pet_type";

    private ImageView petView;
    private int height;
    private int width;
    private int thisX;
    private int thisY;
    private int nextX;
    private int nextY;
    private float nextAngle;
    private float angle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk);
        petView = findViewById(R.id.petWalk);
        PetsType pet = PetsType.valueOf(getIntent().getStringExtra(INTENT_PET_TYPE));
        switch (pet) {
            case CAT:
                petView.setImageResource(R.drawable.cat);
                break;
            case DOG:
                petView.setImageResource(R.drawable.dog);
                // http://www.cyberforum.ru/android-dev/thread1648514.html
                ViewGroup.LayoutParams params = petView.getLayoutParams();
                params.height = (int) (petView.getLayoutParams().height * 1.8);
                petView.setLayoutParams(params);
                break;
            case CTHULHU:
                petView.setImageResource(R.drawable.cthulhu);
                break;
        }

        ViewHelper.executeAfterViewHasDrawn(petView, new Runnable() {
            @Override
            public void run() {
                FrameLayout layout = findViewById(R.id.layoutWalk);
                height = layout.getHeight() - Math.max(petView.getHeight(), petView.getWidth());
                width = layout.getWidth() - Math.max(petView.getHeight(), petView.getWidth());
                thisX = height / 2;
                thisY = width / 2;
                petView.setRotation(-90);
                startAnimation();
            }
        });
    }

    private void startAnimation() {
        int distance;
        do {
            nextX = (int) (Math.random() * width);
            nextY = (int) (Math.random() * height);
            distance = (int) Math.hypot(thisX - nextX, thisY - nextY);
        } while (distance < Math.max(width, height) / 10 ||
                distance > Math.max(width, height) / 1.5 ||
                nextX < petView.getWidth() / 2 ||
                nextY < petView.getHeight() / 2);

        nextAngle = (float) Math.toDegrees(Math.atan2(thisY - nextY, thisX - nextX));
        int rotationDuration = (int) (Math.random() * 500 + Math.abs(nextAngle - angle) * 3);

        final AnimationSet animationSet = new AnimationSet(true);
        RotateAnimation rotateAnimation = new RotateAnimation(angle, nextAngle, 1, 0.5f, 1, 0.5f);
        rotateAnimation.setDuration(rotationDuration);

        Animation translateAnimation = new TranslateAnimation(thisX, nextX, thisY, nextY);
        translateAnimation.setDuration((long) (Math.random() * 1000 + 300 + ViewHelper.pxToDp(distance) * 3));
        translateAnimation.setStartOffset((long) (rotationDuration - rotationDuration / 3));

        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(translateAnimation);
        animationSet.setAnimationListener(animationListener);
        petView.startAnimation(animationSet);
    }

    Animation.AnimationListener animationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            thisX = nextX;
            thisY = nextY;
            angle = nextAngle;
            startAnimation();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    public void goHome(View view) {
        Intent intent = new Intent(WalkActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
