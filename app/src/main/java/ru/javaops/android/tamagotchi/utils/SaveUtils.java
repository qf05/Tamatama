package ru.javaops.android.tamagotchi.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SaveUtils {

    private static ExecutorService executor = Executors.newFixedThreadPool(10);

    private SaveUtils() {
    }

    public static ExecutorService getExecutor() {
        return executor;
    }
}
