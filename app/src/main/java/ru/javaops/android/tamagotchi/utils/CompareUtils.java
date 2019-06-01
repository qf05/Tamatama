package ru.javaops.android.tamagotchi.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ru.javaops.android.tamagotchi.model.Pet;

public class CompareUtils {

    private static final Comparator<Pet> LVL_COMPARATOR = new Comparator<Pet>() {
        @Override
        public int compare(Pet o1, Pet o2) {
            int lvlCompare = Integer.compare(o2.getLvl(), o1.getLvl());
            if (lvlCompare != 0) return lvlCompare;
            return o1.getName().compareToIgnoreCase(o2.getName());
        }
    };

    private static final Comparator<Pet> NAME_COMPARATOR = new Comparator<Pet>() {
        @Override
        public int compare(Pet o1, Pet o2) {
            int nameCompare = o1.getName().compareToIgnoreCase(o2.getName());
            if (nameCompare != 0) return nameCompare;
            return Integer.compare(o2.getLvl(), o1.getLvl());
        }
    };

    private CompareUtils() {
    }

    public static void sort(List<Pet> pets, int position) {
        switch (position) {
            case 1:
                Collections.sort(pets, LVL_COMPARATOR);
                break;
            default:
                Collections.sort(pets, NAME_COMPARATOR);
        }
    }
}
