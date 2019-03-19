package ru.javaops.android.tamagotchi;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import ru.javaops.android.tamagotchi.enums.PetsType;
import ru.javaops.android.tamagotchi.utils.SoundHelper;
import ru.javaops.android.tamagotchi.utils.ViewHelper;

import static android.view.View.TRANSLATION_X;
import static android.view.View.TRANSLATION_Y;

@SuppressLint("ClickableViewAccessibility")
public class WalkActivity extends AppCompatActivity {

    public static final String INTENT_PET_TYPE = "pet_type";

    private static final double MAG_VIEW_FACTOR = 1.8;
    private static final int DEFAULT_ROTATION = 90;

    private ImageView petView;
    private Animator.AnimatorListener animatorListener;

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

        SoundHelper.initialSoundPool(getApplicationContext());

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
            params.height = (int) (petView.getLayoutParams().height * MAG_VIEW_FACTOR);
            petView.setLayoutParams(params);
        }

        petView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Log.d("WALK", "Touch on pet");
                    SoundHelper.play(petsType);
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.performClick();
                }
                return false;
            }
        });
    }

    private void initAnimatorListener() {
        animatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                thisX = nextX;
                thisY = nextY;
                angle = nextAngle;
                startAnimation();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        };
    }

    private void initAnimation() {
        ViewHelper.executeAfterViewDrawing(petView, new Runnable() {
            @Override
            public void run() {
                FrameLayout layout = findViewById(R.id.layoutWalk);
                height = layout.getHeight() - Math.max(petView.getHeight(), petView.getWidth());
                width = layout.getWidth() - Math.max(petView.getHeight(), petView.getWidth());
                nextX = width / 2;
                nextY = height / 2;
                petView.setX(nextX);
                petView.setY(nextY);
                ObjectAnimator rotate = ObjectAnimator
                        .ofFloat(petView, View.ROTATION, -DEFAULT_ROTATION);
                rotate.setDuration(1);
                rotate.addListener(animatorListener);
                rotate.start();
            }
        });
    }

    private void startAnimation() {
        int distance;
        do {
            nextX = (int) (Math.random() * width);
            nextY = (int) (Math.random() * height);
            distance = (int) Math.hypot(thisX - nextX, thisY - nextY);
        } while (isPetPositionCorrect(distance));

        nextAngle = (float) Math.toDegrees(Math.atan2(thisY - nextY, thisX - nextX));
        int rotationDuration = (int) (Math.random() * 500 + Math.abs(nextAngle - angle) * 3);
        int time = (int) (Math.random() * 1000 + 300 + ViewHelper.pxToDp(distance) * 3);

        ObjectAnimator rotate = ObjectAnimator
                .ofFloat(petView, View.ROTATION, nextAngle - DEFAULT_ROTATION);
        rotate.setDuration(rotationDuration);

        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat(TRANSLATION_X, nextX);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat(TRANSLATION_Y, nextY);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(petView, pvhX, pvhY);
        animator.setDuration(time);
        animator.setStartDelay(rotationDuration - rotationDuration / 3);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(rotate, animator);
        animatorSet.addListener(animatorListener);
        animatorSet.start();
    }

    private boolean isPetPositionCorrect(int distance) {
        return distance < Math.max(width, height) / 10 ||
                distance > Math.max(width, height) / 1.5 ||
                nextX < petView.getWidth() / 2 ||
                nextY < petView.getHeight() / 2;
    }
}
