package ru.javaops.android.tamagotchi.ui;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import ru.javaops.android.tamagotchi.R;
import ru.javaops.android.tamagotchi.databinding.ActivitySettingsBinding;
import ru.javaops.android.tamagotchi.viewmodel.SettingsViewModel;

public class SettingsActivity extends BasePetActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        SettingsViewModel model = createModel(SettingsViewModel.class);
        ActivitySettingsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_settings);
        binding.setLifecycleOwner(this);
        binding.setModel(model);
        initGoBackListener(binding.back);
    }
}
