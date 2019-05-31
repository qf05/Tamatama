package ru.javaops.android.tamagotchi.viewmodel;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Application;
import android.content.Intent;
import android.transition.TransitionManager;
import android.transition.TransitionValues;
import android.transition.Visibility;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import ru.javaops.android.tamagotchi.R;
import ru.javaops.android.tamagotchi.adapters.PetAdapter;
import ru.javaops.android.tamagotchi.model.Pet;
import ru.javaops.android.tamagotchi.ui.WalkActivity;

public class MainViewModel extends BasePetViewModel {

    private boolean viewYourPets = false;
    private LiveData<List<Pet>> petsData;

    public MainViewModel(@NonNull Application application) {
        super(application);
        petsData = getDb().petDao().getAll();
    }

    @BindingAdapter("app:pets")
    public static void setPets(RecyclerView recyclerView, List<Pet> pets) {
        ((PetAdapter) Objects.requireNonNull(recyclerView.getAdapter())).updateData(pets, 0);
    }

    public void viewPets(final View rv) {
        viewYourPets = !viewYourPets;
        MyRvAnimation transition = new MyRvAnimation();
        transition.setDuration(500);
        transition.addTarget(rv);
        ViewGroup viewGroup = (ViewGroup) rv.getRootView();
        TransitionManager.beginDelayedTransition(viewGroup, transition);
        notifyChange();
    }

    public String getLvl() {
        if (getPetData().getValue() != null) {
            return String.format(Locale.getDefault(),
                    getApplication().getString(R.string.level_number),
                    getPetData().getValue().getLvl());
        }
        return "";
    }

    public void lvlUp() {
        if (getPetData().getValue() != null) {
            getPetData().getValue().incLvl();
            getDb().petDao().update(getPetData().getValue());
        }
    }

    public void toWalk() {
        if (getPetData().getValue() != null) {
            Intent intent = new Intent(getApplication(), WalkActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplication().startActivity(intent);
        }
    }

    public boolean isViewYourPets() {
        return viewYourPets;
    }

    public LiveData<List<Pet>> getPetsData() {
        return petsData;
    }

    private class MyRvAnimation extends Visibility {

        @Override
        public Animator onAppear(ViewGroup sceneRoot, View view, TransitionValues startValues, TransitionValues endValues) {
            return createScaleAnimator(startValues, endValues);
        }

        @Override
        public Animator onDisappear(ViewGroup sceneRoot, View view, TransitionValues startValues, TransitionValues endValues) {
            return createScaleAnimator(startValues, endValues);
        }

        private Animator createScaleAnimator(TransitionValues startValues, TransitionValues endValues) {
            float start = viewYourPets ? 0 : 1f;
            float end = viewYourPets ? 1f : 0;
            View view = startValues != null ? startValues.view : endValues != null ? endValues.view : null;
            if (view != null) {
                view.setPivotY(view.getHeight());
            }
            return ObjectAnimator.ofFloat(view, "scaleY", start, end);
        }
    }
}
