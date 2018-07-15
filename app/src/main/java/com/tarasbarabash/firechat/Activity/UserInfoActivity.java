package com.tarasbarabash.firechat.Activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tarasbarabash.firechat.R;
import com.tarasbarabash.firechat.ViewModel.UserInfoVM;
import com.tarasbarabash.firechat.databinding.ActivityUserInfoBinding;

/**
 * Created by Taras
 * 5/8/2018, 12:38.
 */

public abstract class UserInfoActivity extends AppCompatActivity {

    private ActivityUserInfoBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getActivityTitle());
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_info);
        mBinding.setViewModel(getViewModel());
    }

    protected abstract UserInfoVM getViewModel();

    protected abstract String getActivityTitle();

    public ActivityUserInfoBinding getBinding() {
        return mBinding;
    }
}
