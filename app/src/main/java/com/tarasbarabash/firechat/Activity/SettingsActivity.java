package com.tarasbarabash.firechat.Activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.tarasbarabash.firechat.Fragment.SettingsFragment;
import com.tarasbarabash.firechat.R;
import com.tarasbarabash.firechat.Utils.Constants;
import com.tarasbarabash.firechat.Utils.FirebaseUtils;
import com.tarasbarabash.firechat.ViewModel.SettingsVM;
import com.tarasbarabash.firechat.databinding.ActivitySettingsBinding;

/**
 * Created by Taras
 * 5/6/2018, 14:36.
 */

public class SettingsActivity extends ResultActivity {

    @Override
    public Fragment getFragment() {
        return SettingsFragment.newInstance(getPhoneNumber());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String profilePhoneNumber = getPhoneNumber();
        ActivitySettingsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_settings);
        SettingsVM settingsVM = new SettingsVM(binding, this, getApplication(), this, profilePhoneNumber);
        binding.setViewModel(settingsVM);
        super.setListener(settingsVM);
    }

    private String getPhoneNumber() {
        String profilePhoneNumber = getIntent().getStringExtra(Constants.INTENT_EXTRA_KEYS.CONTACT_PHONE_NUMBER);
        if (profilePhoneNumber == null) {
            profilePhoneNumber = FirebaseUtils.getCurrentUserPhoneNumber();
        }
        return profilePhoneNumber;
    }
}
