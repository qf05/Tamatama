package ru.javaops.android.tamagotchi.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import java.util.List;
import java.util.regex.Pattern;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import ru.javaops.android.tamagotchi.model.Pet;

public class PetUtils extends ViewModel {


    public static boolean checkName(Context context, String name) {
        if (name.isEmpty() || name.replace(" ", "").isEmpty()) {
            Toast toast = Toast.makeText(context,
                    "Имя не должно быть пустым!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        } else {
            if (name.trim().length() > 15) {
                Toast toast = Toast.makeText(context,
                        "Имя не должно быть длиннее 15 символов", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return false;
            } else {
                if (!Pattern.matches("\\w+", name.replace(" ", ""))) {
                    Toast toast = Toast.makeText(context,
                            "Некорректное имя!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return false;
                } else {
                    return true;
                }
            }
        }
    }


    private MutableLiveData<Pet> currentPet;

    public MutableLiveData<Pet> getCurrentPet() {
        if (currentPet == null) {
            currentPet = new MutableLiveData<Pet>();
        }
        return currentPet;
    }

    private MutableLiveData<List<Pet>> pets;

    public MutableLiveData<List<Pet>> getPets() {
        if (pets == null) {
            pets = new MutableLiveData<List<Pet>>();
        }
        return pets;
    }

}
