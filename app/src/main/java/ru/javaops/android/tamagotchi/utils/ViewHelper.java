package ru.javaops.android.tamagotchi.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHelper {

    private ViewHelper() {
    }

    public static void executeAfterViewDrawing(final View v, final Runnable cb) {
        ViewTreeObserver vto = v.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                cb.run();
                ViewTreeObserver obs = v.getViewTreeObserver();
                obs.removeOnGlobalLayoutListener(this);
            }
        });
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static void setParametersRv(Context context, RecyclerView rv, boolean horizontal) {
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        if (horizontal) {
            llm = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        }
        rv.setLayoutManager(llm);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv.getContext(),
                llm.getOrientation());
        rv.addItemDecoration(dividerItemDecoration);
    }

    @SuppressLint("ShowToast")
    public static void makeMessage(Context context, int messageId) {
        makeMessage(Toast.makeText(context, messageId, Toast.LENGTH_SHORT));
    }

    @SuppressLint("ShowToast")
    public static void makeMessage(Context context, String message) {
        makeMessage(Toast.makeText(context, message, Toast.LENGTH_SHORT));
    }

    private static void makeMessage(Toast toast) {
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
