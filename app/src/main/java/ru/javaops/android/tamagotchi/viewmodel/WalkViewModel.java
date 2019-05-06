package ru.javaops.android.tamagotchi.viewmodel;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.LiveData;

import ru.javaops.android.tamagotchi.R;
import ru.javaops.android.tamagotchi.enums.PetsType;
import ru.javaops.android.tamagotchi.model.Pet;
import ru.javaops.android.tamagotchi.utils.SoundHelper;
import ru.javaops.android.tamagotchi.utils.ViewHelper;

import static android.view.View.TRANSLATION_X;
import static android.view.View.TRANSLATION_Y;

public class WalkViewModel extends BasePetViewModel {

    private static final double DOG_VIEW_MAGNIFICATION = 1.8;
    private static final double MAX_DISTANCE_LIMIT_DIVIDER = 1.5;
    private static final double MIN_DISTANCE_LIMIT_DIVIDER = 8;
    private static final int RANDOM_TIME_DURATION_TRANSLATE = 1000;
    private static final int MIN_TIME_DURATION_TRANSLATE = 300;
    private static final int COEFFICIENT_TIME_DURATION_TRANSLATE = 3;
    private static final int RANDOM_TIME_DURATION_ROTATE = 500;
    private static final int COEFFICIENT_TIME_DURATION_ROTATE = 3;
    private static final int DEFAULT_ROTATION = 90;

    private static int borderHeight;
    private static int borderWidth;
    private static int height;
    private static int width;

    private static int petViewHeight;

    private static ObjectAnimator rotate;
    private static ObjectAnimator animator;
    private static AnimatorSet animatorSet;

    private int counter = 0;
    private View.OnTouchListener onTouchListener;

    public WalkViewModel(@NonNull Application application) {
        super(application);
        SoundHelper.initialSoundPool(application);
        onTouchListener = initTouchListener();
        petViewHeight = (int) application.getResources().getDimension(R.dimen.pet_size);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        SoundHelper.destroySoundPool();
        destroyAnimation();
    }

    public void destroyAnimation() {
        if (rotate != null) {
            rotate.end();
            rotate.cancel();
            rotate = null;
        }
        if (animator != null) {
            animator.end();
            animator.cancel();
            animator = null;
        }
        if (animatorSet != null) {
            animatorSet.removeAllListeners();
            animatorSet.end();
            animatorSet.cancel();
            animatorSet = null;
        }
    }

    @BindingAdapter("onTouchListener")
    public static void setOnTouchListener(View view, View.OnTouchListener onTouchListener) {
        if (onTouchListener != null) {
            view.setOnTouchListener(onTouchListener);
        }
    }

    @BindingAdapter({"animation", "otherView"})
    public static void createAnimation(final View view, final LiveData<Pet> petData, final View otherView) {
        if (petData.getValue() != null) {
            if (PetsType.DOG == petData.getValue().getPetsType()) {
                ViewGroup.LayoutParams params = view.getLayoutParams();
                params.height = (int) (petViewHeight * DOG_VIEW_MAGNIFICATION);
                view.setLayoutParams(params);
            }
            ViewHelper.executeAfterViewDrawing(otherView, new Runnable() {
                @Override
                public void run() {
                    if (rotate == null || animator == null) {
                        rotate = ObjectAnimator.ofFloat(view, View.ROTATION, 0);
                        animator = ObjectAnimator.ofPropertyValuesHolder(view);
                        initAnimationParameters(view, otherView);
                    }
                    if (!animator.isRunning() && !rotate.isRunning()) {
                        startAnimation((int) view.getX(), (int) view.getY(), view.getRotation());
                    }
                }
            });
        }
    }

    public String getCount() {
        return String.valueOf(counter);
    }

    public View.OnTouchListener getOnTouchListener() {
        return onTouchListener;
    }

    private static void initAnimationParameters(final View petView, final View otherView) {
        int displayHeight = otherView.getHeight();
        int displayWidth = otherView.getWidth();
        int radius = (int) (Math.hypot(petView.getHeight(), petView.getWidth()) / 2);
        borderWidth = radius - petView.getWidth() / 2;
        borderHeight = radius - petView.getHeight() / 2;
        width = displayWidth - radius * 2;
        height = displayHeight - radius * 2;
        petView.setX(width >> 1);
        petView.setY(height >> 1);
    }

    private static void startAnimation(int nextX, int nextY, float nextAngle) {
        int distance;
        int thisX = nextX;
        int thisY = nextY;
        float angle = nextAngle;
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

        rotate.setFloatValues(nextAngle - DEFAULT_ROTATION);
        rotate.setDuration(rotationDuration);

        final PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat(TRANSLATION_X, nextX);
        final PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat(TRANSLATION_Y, nextY);
        animator.setValues(pvhX, pvhY);
        animator.setDuration(translateDuration);
        animator.setStartDelay(rotationDuration - rotationDuration / COEFFICIENT_TIME_DURATION_ROTATE);

        animatorSet = new AnimatorSet();
        animatorSet.playTogether(rotate, animator);
        animatorSet.addListener(initAnimatorListener(nextX, nextY, nextAngle));
        animatorSet.start();
    }

    private static boolean isPetPositionCorrect(int distance) {
        return distance < Math.max(width, height) / MIN_DISTANCE_LIMIT_DIVIDER ||
                distance > Math.max(width, height) / MAX_DISTANCE_LIMIT_DIVIDER;
    }

    private static Animator.AnimatorListener initAnimatorListener(final int nextX, final int nextY, final float nextAngle) {
        return new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (animator != null && rotate != null && animatorSet != null) {
                    startAnimation(nextX, nextY, nextAngle);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        };
    }

    private View.OnTouchListener initTouchListener() {
        return new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN && getPetData().getValue() != null) {
                    Log.d("WALK", "Touch on pet");
                    SoundHelper.play(getPetData().getValue().getPetsType());
                    counter++;
                    notifyChange();
                    return true;
                }
                return false;
            }
        };
    }
}
