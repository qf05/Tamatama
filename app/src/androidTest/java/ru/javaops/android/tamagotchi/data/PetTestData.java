package ru.javaops.android.tamagotchi.data;

import java.util.ArrayList;
import java.util.List;

import ru.javaops.android.tamagotchi.enums.PetsType;
import ru.javaops.android.tamagotchi.model.Pet;

public class PetTestData {

    private static final Pet cat = new Pet("Cat", PetsType.CAT);
    private static final Pet dog = new Pet("Dog", PetsType.DOG);
    private static final Pet cthulhu = new Pet("Cthulhu", PetsType.CTHULHU);

    public static List<Pet> getPets() {
        List<Pet> pets = new ArrayList<>();
        pets.add(cat);
        pets.add(dog);
        pets.add(cthulhu);
        return pets;
    }
}
