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
import ru.javaops.android.tamagotchi.utils.ViewHelper;

public class WalkActivity extends AppCompatActivity {
    public static final String INTENT_PET_TYPE = "pet_type";

    private static final double DOG_VIEW_MAGNIFICATION = 1.8;
    private static final double MAX_DISTANCE_LIMIT_DIVIDER = 1.5;
    private static final double MIN_DISTANCE_LIMIT_DIVIDER = 10;
    private static final int RANDOM_TIME_DURATION_TRANSLATE = 1000;
    private static final int MIN_TIME_DURATION_TRANSLATE = 300;
    private static final int COEFFICIENT_TIME_DURATION_TRANSLATE = 3;
    private static final int RANDOM_TIME_DURATION_ROTATE = 500;
    private static final int COEFFICIENT_TIME_DURATION_ROTATE = 3;
    private static final int DEFAULT_ROTATION = -90;

    private Animation.AnimationListener animationListener;
    private ImageView petView;
    private int borderHeight;
    private int borderWidth;
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
        petView = findViewById(R.id.image_pet);
        initViews();
        initAnimatorListener();
        initAnimation();
    }

    public void goHome(View view) {
        Intent intent = new Intent(WalkActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void initViews() {
        petView = findViewById(R.id.image_pet);
        final PetsType petsType = PetsType.valueOf(getIntent().getStringExtra(INTENT_PET_TYPE));
        petView.setImageResource(petsType.getDrawableResource());
        if (PetsType.DOG == petsType) {
            ViewGroup.LayoutParams params = petView.getLayoutParams();
            params.height = (int) (petView.getLayoutParams().height * DOG_VIEW_MAGNIFICATION);
            petView.setLayoutParams(params);
        }
    }

    private void initAnimatorListener() {
        animationListener = new Animation.AnimationListener() {
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
    }

    private void initAnimation() {
        ViewHelper.executeAfterViewDrawing(petView, new Runnable() {
            @Override
            public void run() {
                final FrameLayout layout = findViewById(R.id.layoutWalk);
                int radius = (int) (Math.hypot(petView.getHeight(), petView.getWidth()) / 2);
                borderWidth = radius - petView.getWidth() / 2;
                borderHeight = radius - petView.getHeight() / 2;
                width = layout.getWidth() - radius * 2;
                height = layout.getHeight() - radius * 2;
                thisX = width / 2;
                thisY = height / 2;
                petView.setRotation(DEFAULT_ROTATION);
                startAnimation();
            }
        });
    }

    private void startAnimation() {
        int distance;
        do {
            nextX = (int) (Math.random() * width) + borderWidth;
            nextY = (int) (Math.random() * height) + borderHeight;
            distance = (int) Math.hypot(thisX - nextX, thisY - nextY);
        }
        while (isPetPositionCorrect(distance));

        nextAngle = (float) Math.toDegrees(Math.atan2(thisY - nextY, thisX - nextX));
        int rotationDuration = (int) (Math.random() * RANDOM_TIME_DURATION_ROTATE +
                Math.abs(nextAngle - angle) * COEFFICIENT_TIME_DURATION_ROTATE);
        int translateDuration = (int) (Math.random() * RANDOM_TIME_DURATION_TRANSLATE +
                MIN_TIME_DURATION_TRANSLATE +
                ViewHelper.pxToDp(distance) * COEFFICIENT_TIME_DURATION_TRANSLATE);

        final RotateAnimation rotateAnimation = new RotateAnimation(angle, nextAngle, 1, 0.5f, 1, 0.5f);
        rotateAnimation.setDuration(rotationDuration);

        final Animation translateAnimation = new TranslateAnimation(thisX, nextX, thisY, nextY);
        translateAnimation.setDuration(translateDuration);
        translateAnimation.setStartOffset((long) (rotationDuration - rotationDuration / COEFFICIENT_TIME_DURATION_ROTATE));

        final AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(translateAnimation);
        animationSet.setAnimationListener(animationListener);
        petView.startAnimation(animationSet);
    }

    private boolean isPetPositionCorrect(int distance) {
        return distance < Math.max(width, height) / MIN_DISTANCE_LIMIT_DIVIDER ||
                distance > Math.max(width, height) / MAX_DISTANCE_LIMIT_DIVIDER;
    }
}
