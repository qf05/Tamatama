package ru.javaops.android.tamagotchi.utils;

import android.content.Context;

import java.util.regex.Pattern;

import ru.javaops.android.tamagotchi.db.DataBase;
import ru.javaops.android.tamagotchi.enums.NameCheckStatus;
import ru.javaops.android.tamagotchi.enums.PetsType;
import ru.javaops.android.tamagotchi.model.Pet;

public class PetUtils {

    private static final int MAX_NAME_LENGTH = 15;
    private static final int MIN_NAME_LENGTH = 2;

    private PetUtils() {
    }

    public static NameCheckStatus checkName(String name) {
        if (name.isEmpty() || name.replace(" ", "").isEmpty()) {
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

    public static void createPet(Context context, String name, PetsType petsType) {
        final DataBase db = DataBase.getAppDatabase(context);
        Pet pet = new Pet(name, petsType);
        long id = db.petDao().insert(pet);
        pet.setId(id);
        PrefsUtils.saveSelectedPetId(context, id);
    }
}
