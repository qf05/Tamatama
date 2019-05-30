package ru.javaops.android.tamagotchi.utils;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewTreeObserver;

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

    public static void setParametersRv(Context context, RecyclerView rv) {
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        rv.setLayoutManager(llm);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv.getContext(),
                llm.getOrientation());
        rv.addItemDecoration(dividerItemDecoration);
    }
}
