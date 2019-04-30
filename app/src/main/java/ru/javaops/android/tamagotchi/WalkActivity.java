package ru.javaops.android.tamagotchi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
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

    private Animation.AnimationListener animationListener;
    private ImageView petView;
    private int height;
    private int width;
    private int thisX;
    private int thisY;
    private int nextX;
    private int nextY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk);
        petView = findViewById(R.id.image_pet);
        initViews();
        initAnimatorListener();
        initAnimation();
        startAnimation();
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
                height = layout.getHeight() - petView.getHeight();
                width = layout.getWidth() - petView.getWidth();
                thisX = width / 2;
                thisY = height / 2;
            }
        });
    }

    private void startAnimation() {
        int distance;
        do {
            nextX = (int) (Math.random() * width);
            nextY = (int) (Math.random() * height);
            distance = (int) Math.hypot(thisX - nextX, thisY - nextY);
        }
        while (distance < Math.max(width, height) / MIN_DISTANCE_LIMIT_DIVIDER
                || distance > Math.max(width, height) / MAX_DISTANCE_LIMIT_DIVIDER);

        final Animation translateAnimation = new TranslateAnimation(thisX, nextX, thisY, nextY);
        translateAnimation.setDuration((long) (Math.random() * RANDOM_TIME_DURATION_TRANSLATE
                + MIN_TIME_DURATION_TRANSLATE
                + ViewHelper.pxToDp(distance) * COEFFICIENT_TIME_DURATION_TRANSLATE));
        translateAnimation.setAnimationListener(animationListener);
        petView.startAnimation(translateAnimation);
    }
}
