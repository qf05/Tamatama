package ru.javaops.android.tamagotchi.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;

import java.util.Objects;

import ru.javaops.android.tamagotchi.R;
import ru.javaops.android.tamagotchi.databinding.DialogDeleteBinding;

public class DeletePetDialogFragment extends BaseDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final DialogDeleteBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.dialog_delete, null, false);
        binding.setLifecycleOwner(this);
        binding.cancelDelete.setOnClickListener(initCancelClickListener());
        binding.okDelete.setOnClickListener(initOkClickListener());
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
        builder.setView(binding.getRoot());
        return builder.create();
    }

    private View.OnClickListener initOkClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DeletePets) Objects.requireNonNull(getActivity())).deletePets();
                dismiss();
            }
        };
    }

    public interface DeletePets {
        void deletePets();
    }
}
