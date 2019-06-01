package ru.javaops.android.tamagotchi.ui.dialog;

import android.view.View;

import androidx.fragment.app.DialogFragment;

public class BaseDialogFragment extends DialogFragment {

    View.OnClickListener initCancelClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        };
    }
}
