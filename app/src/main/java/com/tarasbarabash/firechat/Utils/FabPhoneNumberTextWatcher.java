package com.tarasbarabash.firechat.Utils;

import android.support.design.widget.FloatingActionButton;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.util.Patterns;

/**
 * Created by Taras
 * 27.04.2018, 19:38.
 */

public class FabPhoneNumberTextWatcher extends PhoneNumberFormattingTextWatcher {
    private FloatingActionButton mFloatingActionButton;

    public FabPhoneNumberTextWatcher(FloatingActionButton floatingActionButton) {
        mFloatingActionButton = floatingActionButton;
    }

    @Override
    public void afterTextChanged(Editable editable) {
        super.afterTextChanged(editable);
        if (Patterns.PHONE.matcher(editable.toString()).matches()) {
            mFloatingActionButton.show();
        } else mFloatingActionButton.hide();
    }
}
