package ru.javaops.android.tamagotchi.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import ru.javaops.android.tamagotchi.R;
import ru.javaops.android.tamagotchi.enums.PetsType;

public class MySpinnerAdapter extends ArrayAdapter<String> {

    private String[] items;
    private boolean isCreate;
    private int margin;

    public MySpinnerAdapter(Context context, String[] items, boolean isCreate) {
        super(context, R.layout.item_spinner, items);
        this.isCreate = isCreate;
        this.items = items;
        margin = (int) context.getResources().getDimension(R.dimen.min_margin);
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        return getPetView(position, parent, true);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        return getPetView(position, parent, false);
    }

    private View getPetView(int position, ViewGroup parent, boolean isDrop) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_spinner, null, false);

        TextView textView = view.findViewById(R.id.spinner_text);
        textView.setText(items[position]);
        if (isCreate) {
            setImage(textView, position);
        }
        if (isDrop) {
            setLayoutParams(textView);
        }
        return view;
    }

    private void setLayoutParams(View textView) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(margin * 2, margin, margin * 2, margin);
        textView.setLayoutParams(params);
    }

    private void setImage(TextView textView, int position) {
        Drawable image = getContext().getResources()
                .getDrawable(PetsType.values()[position].getIconDrawableResource());
        int size = (int) getContext().getResources().getDimension(R.dimen.pet_small_icon);
        image.setBounds(0, -margin, size, size - margin);
        textView.setCompoundDrawables(image, null, null, null);
        textView.setPadding(0, margin, 0, 0);
    }
}
