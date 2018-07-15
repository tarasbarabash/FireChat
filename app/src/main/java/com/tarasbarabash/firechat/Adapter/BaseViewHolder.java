package com.tarasbarabash.firechat.Adapter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Taras
 * 03.05.2018, 14:52.
 */

public abstract class BaseViewHolder<B extends ViewDataBinding, O> extends RecyclerView.ViewHolder {
    private B binding;

    public BaseViewHolder(B binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(O object) {
        binding.setVariable(getVariableName(), object);
        binding.executePendingBindings();
    }

    public abstract int getVariableName();
}
