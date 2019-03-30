package ru.javaops.android.tamagotchi.utils;

import android.content.Context;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ru.javaops.android.tamagotchi.model.Pet;

public class Utils {

    public static void setParametersRv(Context context, RecyclerView rv) {
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        rv.setLayoutManager(llm);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv.getContext(),
                llm.getOrientation());
        rv.addItemDecoration(dividerItemDecoration);
    }

    public static void sort(List<Pet> pets, int position) {
        switch (position) {
            case 1:
                Collections.sort(pets, new Comparator<Pet>() {
                    @Override
                    public int compare(Pet o1, Pet o2) {
                        if (o1.getLvl() != o2.getLvl()) {
                            return Integer.compare(o2.getLvl(), o1.getLvl());
                        } else {
                            return o1.getName().compareToIgnoreCase(o2.getName());
                        }
                    }
                });
                break;
            default: {
                Collections.sort(pets, new Comparator<Pet>() {
                    @Override
                    public int compare(Pet o1, Pet o2) {
                        if (o1.getName().compareToIgnoreCase(o2.getName()) != 0) {
                            return o1.getName().compareToIgnoreCase(o2.getName());
                        } else {
                            return Integer.compare(o2.getLvl(), o1.getLvl());
                        }
                    }
                });
            }
        }
    }
}
