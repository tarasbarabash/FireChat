package com.tarasbarabash.firechat.Activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;

import com.tarasbarabash.firechat.Fragment.ConversationFragment;
import com.tarasbarabash.firechat.R;
import com.tarasbarabash.firechat.Utils.Constants;
import com.tarasbarabash.firechat.ViewModel.ConversationActivityVM;
import com.tarasbarabash.firechat.databinding.ActivityConversationBinding;

/**
 * Created by Taras
 * 30.04.2018, 21:53.
 */

public class ConversationActivity extends ResultActivity {
    private static final String TAG = ConversationActivity.class.getSimpleName();

    @Override
    public Fragment getFragment() {
        return ConversationFragment.newInstance(getIntent().getExtras());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: " + getIntent());
        ActivityConversationBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_conversation);
        binding.setViewModel(new ConversationActivityVM(binding, this, getApplication(), this, getIntent()));
        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
