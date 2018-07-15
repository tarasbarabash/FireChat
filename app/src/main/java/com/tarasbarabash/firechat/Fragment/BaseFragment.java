package com.tarasbarabash.firechat.Fragment;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tarasbarabash.firechat.ViewModel.BaseVM;

/**
 * Created by Taras
 * 05.04.2018, 13:19.
 */

public abstract class BaseFragment<T extends ViewDataBinding> extends Fragment {
    public abstract int getLayout();
    protected abstract BaseVM<T> setViewModel(T binding);
    private BaseVM<T> viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        T binding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        viewModel = setViewModel(binding);
        return binding.getRoot();
    }

    public BaseVM<T> getViewModel() {
        return viewModel;
    }
}
