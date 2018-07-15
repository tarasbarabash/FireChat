package com.tarasbarabash.firechat.Utils;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;

import com.tarasbarabash.firechat.R;
import com.tarasbarabash.firechat.databinding.FragmentNameBinding;

/**
 * Created by Taras
 * 29.04.2018, 22:05.
 */

public class FabNameTextWatcher implements TextWatcher {
    private FragmentNameBinding mFragmentNameBinding;
    private Context mContext;

    public FabNameTextWatcher(FragmentNameBinding fragmentNameBinding, Context context) {
        mFragmentNameBinding = fragmentNameBinding;
        mContext = context;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.length() <= 0) {
            mFragmentNameBinding.nameTil.setError(mContext.getString(R.string.required_error));
            mFragmentNameBinding.fab.hide();
        } else {
            mFragmentNameBinding.nameTil.setError("");
            mFragmentNameBinding.fab.show();
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
