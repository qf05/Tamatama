package ru.javaops.android.tamagotchi.utils;

import android.content.Context;

import java.util.regex.Pattern;

import ru.javaops.android.tamagotchi.db.DataBase;
import ru.javaops.android.tamagotchi.enums.NameCheckStatus;
import ru.javaops.android.tamagotchi.model.Pet;

public class PetUtils {

    private static final int MAX_NAME_LENGTH = 15;
    private static final int MIN_NAME_LENGTH = 2;

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

    public static Pet getSelectedPet(Context context) {
        Pet pet = null;
        long petId = PrefsUtils.getSelectedPetId(context);

        DataBase db = DataBase.getAppDatabase(context);
        if (petId >= 0) {
            pet = db.petDao().findById(petId);
            if (pet == null) {
                pet = db.petDao().findAny();
                if (pet != null) {
                    PrefsUtils.saveSelectedPetId(context, pet.getId());
                }
            }
        }
        return pet;
    }
}
