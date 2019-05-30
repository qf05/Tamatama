package ru.javaops.android.tamagotchi.db.converters;

import androidx.room.TypeConverter;

import ru.javaops.android.tamagotchi.enums.ActionType;
import ru.javaops.android.tamagotchi.enums.PetsType;

public class Converter {

    @TypeConverter
    public String fromPetType(PetsType petsType) {
        return petsType.toString();
    }

    @TypeConverter
    public PetsType toPetType(String petsType) {
        return PetsType.valueOf(petsType);
    }

    @TypeConverter
    public String fromActionType(ActionType actionType) {
        return actionType.toString();
    }

    @TypeConverter
    public ActionType toActionType(String actionType) {
        return ActionType.valueOf(actionType);
    }
}
