package ru.javaops.android.tamagotchi.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import ru.javaops.android.tamagotchi.R;
import ru.javaops.android.tamagotchi.databinding.DialogCreatePetBinding;
import ru.javaops.android.tamagotchi.enums.NameCheckStatus;
import ru.javaops.android.tamagotchi.utils.PetUtils;
import ru.javaops.android.tamagotchi.utils.ViewHelper;
import ru.javaops.android.tamagotchi.viewmodel.CreatePetViewModel;

public class CreatePetDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final CreatePetViewModel model = ViewModelProviders.of(this).get(CreatePetViewModel.class);
        model.setShowCancelButton(getActivity() instanceof SettingsActivity);
        final DialogCreatePetBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.dialog_create_pet, null, false);
        binding.setModel(model);
        binding.cancelCreate.setOnClickListener(initCancelClickListener());
        binding.okCreate.setOnClickListener(initOkClickListener(model));
//        binding.setLifecycleOwner(getViewLifecycleOwner()); //WTF???  because DataBindingUtil.inflate ?

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(binding.getRoot());
//        builder.setCancelable(false);   WTF???? not work!!!! Oo
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

    private View.OnClickListener initCancelClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        };
    }
}
