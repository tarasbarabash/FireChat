package com.tarasbarabash.firechat.Activity;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;

import com.google.firebase.database.DatabaseReference;
import com.tarasbarabash.firechat.R;
import com.tarasbarabash.firechat.Utils.Constants;
import com.tarasbarabash.firechat.Utils.FirebaseUtils;
import com.tarasbarabash.firechat.ViewModel.UserInfoVM;
import com.tarasbarabash.firechat.databinding.ActivityUserInfoBinding;

/**
 * Created by Taras
 * 5/8/2018, 15:03.
 */

public class BioActivity extends UserInfoActivity {
    private String mOldBio;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOldBio = getIntent().getStringExtra(Constants.INTENT_EXTRA_KEYS.BIO_TYPE);
        if (mOldBio != null)
            getBinding().changeInputText.setText(mOldBio);
    }

    @Override
    protected UserInfoVM getViewModel() {
        UserInfoVM viewModel = new BioVM(getBinding(), this, getApplication(), this);
        viewModel.setShowCounter(true);
        viewModel.setMaxLength(70);
        viewModel.setChangeType(getActivityTitle());
        viewModel.setInfoText(getString(R.string.bio_info));
        return viewModel;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.bio);
    }

    private class BioVM extends UserInfoVM {
        public BioVM(ActivityUserInfoBinding binding, Context context, Application application, Activity activity) {
            super(binding, context, application, activity);
        }

        @Override
        protected TextWatcher getTextWatcher() {
            return new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.toString().trim().length() > 0) {
                        getBinding().fab.show();
                        getBinding().changeTil.setError("");
                    }
                    if (s.toString().trim().length() > 70) {
                        getBinding().fab.hide();
                        getBinding().changeTil.setError(getString(R.string.bio_max_length));
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            };
        }

        @Override
        public void onChangeClick() {
            String bio = getBinding().changeInputText.getText().toString();
            DatabaseReference bioRef = FirebaseUtils.getCurrentUserMetaRef()
                    .child(FirebaseUtils.USERS_DB.FIELDS.BIO);
            if (!bio.isEmpty()) bioRef.setValue(bio);
            else bioRef.removeValue();
            finish();
        }
    }
}
