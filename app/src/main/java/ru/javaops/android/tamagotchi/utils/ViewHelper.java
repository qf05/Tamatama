package ru.javaops.android.tamagotchi.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.javaops.android.tamagotchi.R;
import ru.javaops.android.tamagotchi.adapters.MySpinnerAdapter;
import ru.javaops.android.tamagotchi.enums.NameCheckStatus;
import ru.javaops.android.tamagotchi.enums.PetsType;
import ru.javaops.android.tamagotchi.ui.BaseActivity;

public class ViewHelper {

    private ViewHelper() {
    }

    public static void executeAfterViewDrawing(final View v, final Runnable cb) {
        ViewTreeObserver vto = v.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                cb.run();
                ViewTreeObserver obs = v.getViewTreeObserver();
                obs.removeOnGlobalLayoutListener(this);
            }
        });
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static void setParametersRv(Context context, RecyclerView rv) {
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        rv.setLayoutManager(llm);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv.getContext(),
                llm.getOrientation());
        rv.addItemDecoration(dividerItemDecoration);
    }

    public static void showCreatePetDialog(final BaseActivity activity,
                                           final boolean viewCancelButton) {
        final LayoutInflater inflater = activity.getLayoutInflater();
        final View layout = inflater.inflate(R.layout.dialog_create_pet, null);
        final EditText inputName = layout.findViewById(R.id.input_name);
        final Spinner spinnerCreate = layout.findViewById(R.id.input_type_pet);
        final Button cancelButton = layout.findViewById(R.id.cancel_create);
        final AlertDialog dialog = new AlertDialog.Builder(activity)
                .setView(layout)
                .setCancelable(false)
                .create();

        if (viewCancelButton) {
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });
        } else {
            cancelButton.setVisibility(View.INVISIBLE);
            cancelButton.setClickable(false);
        }
        layout.findViewById(R.id.ok_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = inputName.getText().toString().trim();
                NameCheckStatus nameCheckStatus = PetUtils.checkName(name);
                if (nameCheckStatus != NameCheckStatus.CORRECT) {
                    activity.makeMessage(nameCheckStatus.getMessageId());
                } else {
                    PetsType[] petsTypes = PetsType.values();
                    PetsType petsType = petsTypes[spinnerCreate.getSelectedItemPosition()];
                    Log.d("SELECTED_PET", petsType.toString() + "   " + name);
                    PetUtils.createPet(activity, name, petsType);
                    dialog.cancel();
                    activity.goBack(null);
                }
            }
        });
        initSpinner(layout);
        dialog.show();
    }

    private static void initSpinner(View layout) {
        Spinner spinner = layout.findViewById(R.id.input_type_pet);
        String[] stringArray = layout.getContext().getResources().getStringArray(R.array.pets);
        SpinnerAdapter spinnerAdapter =
                new MySpinnerAdapter(layout.getContext(), stringArray, true);
        spinner.setAdapter(spinnerAdapter);
    }
}
