package ru.javaops.android.tamagotchi;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import ru.javaops.android.tamagotchi.enums.PetsType;
import ru.javaops.android.tamagotchi.utils.MyImageView;
import ru.javaops.android.tamagotchi.utils.ViewHelper;

import static android.view.View.TRANSLATION_X;
import static android.view.View.TRANSLATION_Y;

public class WalkActivity extends AppCompatActivity {
    public static final String INTENT_PET_TYPE = "pet_type";

    private int height;
    private int width;
    private int thisX;
    private int thisY;
    private int nextX;
    private int nextY;
    private float nextAngle;
    private float angle;
    private MyImageView petView;
    private int correctorX;
    private int correctorY;
    private AnimatorSet animatorSet;
    private SoundPool soundPool;
    private int loadedSampleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk);

        loadedSampleId = -1;
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_UNKNOWN)
                .build();
        soundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                Log.i("WALK", "Load complete");
                loadedSampleId = sampleId;
            }
        });

        petView = findViewById(R.id.petWalk);
        PetsType pet = PetsType.valueOf(getIntent().getStringExtra(INTENT_PET_TYPE));
        switch (pet) {
            case CAT:
                soundPool.load(WalkActivity.this, R.raw.cat, 2);
                petView.setImageResource(R.drawable.cat);
                break;
            case DOG:
                soundPool.load(WalkActivity.this, R.raw.dog, 2);
                petView.setImageResource(R.drawable.dog);
                // http://www.cyberforum.ru/android-dev/thread1648514.html
                ViewGroup.LayoutParams params = petView.getLayoutParams();
                params.height = (int) (petView.getLayoutParams().height * 1.8);
                petView.setLayoutParams(params);
                break;
            case CTHULHU:
                soundPool.load(WalkActivity.this, R.raw.cthulhu, 2);
                petView.setImageResource(R.drawable.cthulhu);
                break;
        }

        petView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Log.i("WALK", "Touch on pet");
                    if (loadedSampleId > 0) {
                        soundPool.play(loadedSampleId, 1f, 1f, 2, 0, 1f);

                    }
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.performClick();
                    return false;
                } else {
                    return false;
                }
            }
        });

        ViewHelper.executeAfterViewHasDrawn(petView, new Runnable() {
            @Override
            public void run() {
                FrameLayout layout = findViewById(R.id.layoutWalk);
                height = layout.getHeight() - petView.getHeight();
                width = layout.getWidth() - petView.getWidth();
                if (petView.getHeight() > petView.getWidth()) {
                    correctorX = (petView.getHeight() - petView.getWidth()) / 2;
                    correctorY = 0;
                    width -= correctorX;

                } else {
                    correctorX = 0;
                    correctorY = (petView.getWidth() - petView.getHeight()) / 2;
                    height -= correctorY;
                }
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
        do {
            nextX = thisX + (int) (Math.random() * width - width / 2);
        } while (nextX < correctorX || nextX > width);

        do {
            nextY = thisY + (int) (Math.random() * height - height / 2);
        } while (nextY < correctorY || nextY > height);


        nextAngle = (float) Math.toDegrees(Math.atan2(thisY - nextY, thisX - nextX));
        int rotationDuration = (int) Math.abs(nextAngle - angle) * 4;
        int time = (int) (Math.random() * 2000 + Math.hypot(thisX - nextX, thisY - nextY));

        // https://stackoverflow.com/questions/28352352/change-multiple-properties-with-single-objectanimator
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat(TRANSLATION_X, nextX);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat(TRANSLATION_Y, nextY);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(petView, pvhX, pvhY);
        animator.setDuration(time);
        animator.setStartDelay(rotationDuration - rotationDuration / 3);

        ObjectAnimator rotate = ObjectAnimator.ofFloat(petView, View.ROTATION, nextAngle - 90);
        rotate.setDuration(rotationDuration);

        animatorSet = new AnimatorSet();
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
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (animatorSet != null) {
            animatorSet.removeAllListeners();
            animatorSet.cancel();
            animatorSet.end();
        }
    }
}
