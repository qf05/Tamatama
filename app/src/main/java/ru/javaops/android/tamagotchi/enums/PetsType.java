package ru.javaops.android.tamagotchi.enums;

import androidx.annotation.DrawableRes;

import ru.javaops.android.tamagotchi.R;

public enum PetsType {
    CAT(R.drawable.cat_icon, R.drawable.cat_small, R.drawable.cat),
    DOG(R.drawable.dog_icon, R.drawable.dog_small, R.drawable.dog),
    CTHULHU(R.drawable.cthulhu_icon, R.drawable.cthulhu_small, R.drawable.cthulhu);

    @DrawableRes
    private final int iconDrawableResource;
    @DrawableRes
    private final int petDrawableResource;
    @DrawableRes
    private final int walkDrawableResource;

    PetsType(@DrawableRes int iconDrawableResource,
             @DrawableRes int petDrawableResource,
             @DrawableRes int walkDrawableResource) {
        this.iconDrawableResource = iconDrawableResource;
        this.petDrawableResource = petDrawableResource;
        this.walkDrawableResource = walkDrawableResource;
    }

    @DrawableRes
    public int getIconDrawableResource() {
        return iconDrawableResource;
    }

    @DrawableRes
    public int getPetDrawableResource() {
        return petDrawableResource;
    }

    @DrawableRes
    public int getWalkDrawableResource() {
        return walkDrawableResource;
    }
}
