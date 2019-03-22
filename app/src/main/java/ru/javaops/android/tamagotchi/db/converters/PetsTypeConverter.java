package ru.javaops.android.tamagotchi.db.converters;

import androidx.room.TypeConverter;
import ru.javaops.android.tamagotchi.enums.PetsType;

public class PetsTypeConverter {

    @TypeConverter
    public String fromPetType(PetsType petsType) {
        return petsType.toString();
    }

    @TypeConverter
    public PetsType toPetType(String petsType) {
        return PetsType.valueOf(petsType);
    }
}
