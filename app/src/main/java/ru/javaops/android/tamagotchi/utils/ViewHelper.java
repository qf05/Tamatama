package ru.javaops.android.tamagotchi.utils;

import android.content.res.Resources;
import android.view.View;
import android.view.ViewTreeObserver;

public class ViewHelper {

    public static void executeAfterViewDrawing(final View view, final Runnable runnable) {
        ViewTreeObserver observer = view.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                runnable.run();
                ViewTreeObserver obs = view.getViewTreeObserver();
                obs.removeOnGlobalLayoutListener(this);
            }
        });
    }

    public static int pxToDp(final int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }
}
