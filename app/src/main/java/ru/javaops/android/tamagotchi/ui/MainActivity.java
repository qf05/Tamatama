package ru.javaops.android.tamagotchi.ui;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ru.javaops.android.tamagotchi.R;
import ru.javaops.android.tamagotchi.adapters.PetAdapter;
import ru.javaops.android.tamagotchi.databinding.ActivityMainBinding;
import ru.javaops.android.tamagotchi.model.Pet;
import ru.javaops.android.tamagotchi.utils.PrefsUtils;
import ru.javaops.android.tamagotchi.utils.SoundHelper;
import ru.javaops.android.tamagotchi.utils.ViewHelper;
import ru.javaops.android.tamagotchi.viewmodel.MainViewModel;

public class MainActivity extends BasePetActivity implements PetAdapter.ItemClickListener {

    private MainViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SoundHelper.initialSoundPool(getApplicationContext());
        model = createModel(MainViewModel.class);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);
        binding.setModel(model);
        final RecyclerView rv = binding.changeRv;
        ViewHelper.setParametersRv(this, rv, true);
        rv.setAdapter(new PetAdapter(new ArrayList<Pet>(), this, R.layout.item_change_pet));
    }

    @Override
    protected void onResume() {
        super.onResume();
        model.notifyChange();
    }

    @Override
    public void goBack(View view) {
        model.notifyChange();
    }

    @Override
    public void onItemClick(long itemId) {
        PrefsUtils.saveSelectedPetId(this, itemId);
        model.notifyChange();
    }
}
