package ru.javaops.android.tamagotchi.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorUtils {

    private static final ExecutorService executor = Executors.newFixedThreadPool(10);

    private ExecutorUtils() {
    }

    public static ExecutorService getExecutor() {
        return executor;
    }
}
