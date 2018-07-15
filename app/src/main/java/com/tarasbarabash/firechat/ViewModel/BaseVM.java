package com.tarasbarabash.firechat.ViewModel;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;

import com.tarasbarabash.firechat.FireChat;
import com.tarasbarabash.firechat.Fragment.BaseFragment;

/**
 * Created by Taras
 * 05.04.2018, 13:11.
 */

public class BaseVM<T extends ViewDataBinding> {
    private BaseFragment mFragment;
    private Context mContext;
    private Activity mActivity;
    private T mBinding;
    private FireChat mApplication;

    public BaseVM(T binding, BaseFragment fragment) {
        mBinding = binding;
        mFragment = fragment;
        mContext = fragment.getContext();
        mActivity = fragment.getActivity();
        mApplication = (FireChat) fragment.getActivity().getApplication();
    }

    public BaseVM(T binding, Context context, Application application, Activity activity) {
        mBinding = binding;
        mContext = context;
        mActivity = activity;
        mApplication = (FireChat) application;
    }

    public Activity getActivity() {
        return mActivity;
    }

    public Context getContext() {
        return mContext;
    }

    public T getBinding() {
        return mBinding;
    }

    public BaseFragment getFragment() {
        return mFragment;
    }

    public void setToolbarText(String text) {
        ((AppCompatActivity) mFragment.getActivity()).getSupportActionBar().setTitle(text);
    }

    public FireChat getApplication() {
        return mApplication;
    }
}
