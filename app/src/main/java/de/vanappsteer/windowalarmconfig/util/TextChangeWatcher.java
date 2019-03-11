package de.vanappsteer.windowalarmconfig.util;

import android.text.Editable;
import android.text.TextWatcher;

public abstract class TextChangeWatcher implements TextWatcher {

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        // not implemented
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        // not implemented
    }

    public abstract void afterTextChanged(Editable editable);
}
