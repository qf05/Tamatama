package ru.javaops.android.tamagotchi;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import ru.javaops.android.tamagotchi.enums.PetsType;

import static android.view.View.TRANSLATION_X;
import static android.view.View.TRANSLATION_Y;

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
        SoundHelper.initialSoundPool(getApplicationContext());
        petView = findViewById(R.id.petWalk);
        final PetsType petsType = PetsType.valueOf(getIntent().getStringExtra(INTENT_PET_TYPE));
        switch (petsType) {
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
        petView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("WALK", "Touch on pet");
                SoundHelper.play(petsType);
            }
        });

        ViewHelper.executeAfterViewHasDrawn(petView, new Runnable() {
            @Override
            public void run() {
                FrameLayout layout = findViewById(R.id.layoutWalk);
                height = layout.getHeight() - Math.max(petView.getHeight(), petView.getWidth());
                width = layout.getWidth() - Math.max(petView.getHeight(), petView.getWidth());
                nextX = width / 2;
                nextY = height / 2;
                petView.setX(nextX);
                petView.setY(nextY);
                ObjectAnimator rotate = ObjectAnimator.ofFloat(petView, View.ROTATION, -90);
                rotate.setDuration(1);
                rotate.addListener(aListener);
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
        } while (distance < Math.max(width, height) / 10 ||
                distance > Math.max(width, height) / 1.5 ||
                nextX < petView.getWidth() / 2 ||
                nextY < petView.getHeight() / 2);

        nextAngle = (float) Math.toDegrees(Math.atan2(thisY - nextY, thisX - nextX));
        int rotationDuration = (int) (Math.random() * 500 + Math.abs(nextAngle - angle) * 3);
        int time = (int) (Math.random() * 1000 + 300 + ViewHelper.pxToDp(distance) * 3);

        ObjectAnimator rotate = ObjectAnimator.ofFloat(petView, View.ROTATION, nextAngle - 90);
        rotate.setDuration(rotationDuration);

        // https://stackoverflow.com/questions/28352352/change-multiple-properties-with-single-objectanimator
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat(TRANSLATION_X, nextX);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat(TRANSLATION_Y, nextY);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(petView, pvhX, pvhY);
        animator.setDuration(time);
        animator.setStartDelay(rotationDuration - rotationDuration / 3);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(rotate, animator);
        animatorSet.addListener(aListener);
        animatorSet.start();
    }

    Animator.AnimatorListener aListener = new Animator.AnimatorListener() {
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

    public void goHome(View view) {
        Intent intent = new Intent(WalkActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
