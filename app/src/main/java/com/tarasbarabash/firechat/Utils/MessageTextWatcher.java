package com.tarasbarabash.firechat.Utils;

import android.text.Editable;
import android.text.TextWatcher;

import com.tarasbarabash.firechat.databinding.FragmentConversationBinding;

/**
 * Created by Taras
 * 02.05.2018, 11:41.
 */

public class MessageTextWatcher implements TextWatcher {
    private FragmentConversationBinding mBinding;

    public MessageTextWatcher(FragmentConversationBinding binding) {
        mBinding = binding;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        mBinding.sendLayout.send.setEnabled(charSequence.toString().trim().length() > 0);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
