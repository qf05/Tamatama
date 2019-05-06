package ru.javaops.android.tamagotchi.ui;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import ru.javaops.android.tamagotchi.R;
import ru.javaops.android.tamagotchi.databinding.ActivityWalkBinding;
import ru.javaops.android.tamagotchi.viewmodel.WalkViewModel;

public class WalkActivity extends BasePetActivity {

    private WalkViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk);
        model = createModel(WalkViewModel.class);
        ActivityWalkBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_walk);
        binding.setLifecycleOwner(this);
        binding.setModel(model);
        initGoBackListener(binding.back);
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        model.destroyAnimation();
    }
}
