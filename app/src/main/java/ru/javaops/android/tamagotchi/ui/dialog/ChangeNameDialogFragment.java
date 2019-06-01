package ru.javaops.android.tamagotchi.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import ru.javaops.android.tamagotchi.R;
import ru.javaops.android.tamagotchi.databinding.DialogChangeNameBinding;
import ru.javaops.android.tamagotchi.enums.NameCheckStatus;
import ru.javaops.android.tamagotchi.model.Pet;
import ru.javaops.android.tamagotchi.utils.ExecutorUtils;
import ru.javaops.android.tamagotchi.utils.PetUtils;
import ru.javaops.android.tamagotchi.utils.ViewHelper;
import ru.javaops.android.tamagotchi.viewmodel.ChangeNameViewModel;

public class ChangeNameDialogFragment extends BaseDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        ChangeNameViewModel model = ViewModelProviders.of(this).get(ChangeNameViewModel.class);
        model.getPetDataMediator().observe(this, model.getPetObserver());
        DialogChangeNameBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.dialog_change_name, null, false);
        binding.setLifecycleOwner(this);
        binding.setModel(model);
        binding.cancelChangeName.setOnClickListener(initCancelClickListener());
        binding.okChangeName.setOnClickListener(initOkClickListener(model));

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(binding.getRoot());
        return builder.create();
    }

    private View.OnClickListener initOkClickListener(final ChangeNameViewModel model) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NameCheckStatus nameCheckStatus = PetUtils.checkName(model.getNewName());
                if (nameCheckStatus != NameCheckStatus.CORRECT) {
                    ViewHelper.makeMessage(getContext(), nameCheckStatus.getMessageId());
                } else {
                    final Pet pet = model.getPetData().getValue();
                    if (pet != null) {
                        pet.setName(model.getNewName());
                        ExecutorUtils.getExecutor().submit(new Runnable() {
                            @Override
                            public void run() {
                                model.getDb().petDao().update(pet);
                            }
                        });
                    }
                    dismiss();
                }
            }
        };
    }
}
