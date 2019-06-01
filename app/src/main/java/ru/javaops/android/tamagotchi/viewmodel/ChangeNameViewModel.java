package ru.javaops.android.tamagotchi.viewmodel;

import android.app.Application;
import android.text.Html;
import android.text.Spanned;

import androidx.annotation.NonNull;

import ru.javaops.android.tamagotchi.R;

import static androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY;

public class ChangeNameViewModel extends BasePetViewModel {

    private String newName;

    public ChangeNameViewModel(@NonNull Application application) {
        super(application);
    }

    public void setNewName(CharSequence s) {
        newName = s.toString();
    }

    public Spanned getThisName() {
        if (getPetData().getValue() != null) {
            String text = getApplication().getString(R.string.this_name, getPetData().getValue().getName());
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                return Html.fromHtml(text, FROM_HTML_MODE_LEGACY);
            } else {
                return Html.fromHtml(text);
            }
        }
        return null;
    }

    public String getNewName() {
        return newName;
    }
}
