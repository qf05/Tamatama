package ru.javaops.android.tamagotchi.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SpinnerAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import java.util.Objects;

import ru.javaops.android.tamagotchi.R;
import ru.javaops.android.tamagotchi.adapters.MySpinnerAdapter;
import ru.javaops.android.tamagotchi.databinding.DialogCreatePetBinding;
import ru.javaops.android.tamagotchi.enums.NameCheckStatus;
import ru.javaops.android.tamagotchi.ui.SettingsActivity;
import ru.javaops.android.tamagotchi.utils.PetUtils;
import ru.javaops.android.tamagotchi.utils.ViewHelper;
import ru.javaops.android.tamagotchi.viewmodel.CreatePetViewModel;

public class CreatePetDialogFragment extends BaseDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final CreatePetViewModel model = ViewModelProviders.of(this).get(CreatePetViewModel.class);
        model.setShowCancelButton(getActivity() instanceof SettingsActivity);
        final DialogCreatePetBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.dialog_create_pet, null, false);
        binding.setLifecycleOwner(this);
        binding.setModel(model);
        binding.cancelCreate.setOnClickListener(initCancelClickListener());
        binding.okCreate.setOnClickListener(initOkClickListener(model));

        String[] stringArray = getResources().getStringArray(R.array.pets);
        SpinnerAdapter spinnerAdapter =
                new MySpinnerAdapter(getContext(), stringArray, true);
        binding.inputTypePet.setAdapter(spinnerAdapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
        builder.setView(binding.getRoot());
        return builder.create();
    }

    private View.OnClickListener initOkClickListener(final CreatePetViewModel model) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NameCheckStatus nameCheckStatus = PetUtils.checkName(model.getPetName());
                if (nameCheckStatus != NameCheckStatus.CORRECT) {
                    ViewHelper.makeMessage(getContext(), nameCheckStatus.getMessageId());
                } else {
                    PetUtils.createPet(getContext(), model.getPetName(), model.getPetsType());
                    if (getActivity() instanceof SettingsActivity) {
                        getActivity().finish();
                    }
                    dismiss();
                }
            }
        };
    }
}
