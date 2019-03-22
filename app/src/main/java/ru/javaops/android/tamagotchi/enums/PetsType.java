package ru.javaops.android.tamagotchi.enums;

import androidx.annotation.DrawableRes;
import ru.javaops.android.tamagotchi.R;

public enum PetsType {
    CAT(R.drawable.cat),
    DOG(R.drawable.dog),
    CTHULHU(R.drawable.cthulhu);

    private final int drawableResource;

    PetsType(@DrawableRes int drawableResource) {
        this.drawableResource = drawableResource;
    }

    public int getDrawableResource() {
        return drawableResource;
    }
}
