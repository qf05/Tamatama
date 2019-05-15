package ru.javaops.android.tamagotchi.utils;

import java.util.regex.Pattern;

import ru.javaops.android.tamagotchi.enums.NameCheckStatus;
import ru.javaops.android.tamagotchi.model.Pet;

public class PetUtils {

    private static final int MAX_NAME_LENGTH = 15;
    private static final int MIN_NAME_LENGTH = 2;

    private static Pet selectedPet;

    private PetUtils() {
    }

    public static NameCheckStatus checkName(String name) {
        if (name == null || name.isEmpty() || name.replace(" ", "").isEmpty()) {
            return NameCheckStatus.EMPTY;
        }
        if (name.trim().length() < MIN_NAME_LENGTH) {
            return NameCheckStatus.EXCEEDED_CHARACTERS_MIN;
        }
        if (name.trim().length() > MAX_NAME_LENGTH) {
            return NameCheckStatus.EXCEEDED_CHARACTERS_LIMIT;
        }
        if (!Pattern.matches("\\w+", name.replace(" ", ""))) {
            return NameCheckStatus.NOT_CORRECT;
        }
        return NameCheckStatus.CORRECT;
    }

    public static Pet getSelectedPet() {
        return selectedPet;
    }

    public static void setSelectedPet(Pet selectedPet) {
        PetUtils.selectedPet = selectedPet;
    }
}
