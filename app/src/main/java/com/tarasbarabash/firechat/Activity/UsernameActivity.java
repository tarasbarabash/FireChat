package com.tarasbarabash.firechat.Activity;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.tarasbarabash.firechat.R;
import com.tarasbarabash.firechat.Utils.Constants;
import com.tarasbarabash.firechat.Utils.FirebaseUtils;
import com.tarasbarabash.firechat.ViewModel.UserInfoVM;
import com.tarasbarabash.firechat.databinding.ActivityUserInfoBinding;

/**
 * Created by Taras
 * 5/8/2018, 13:00.
 */

public class UsernameActivity extends UserInfoActivity {
    private static final String TAG = UsernameActivity.class.getSimpleName();
    private String mOldUserName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOldUserName = getIntent().getStringExtra(Constants.INTENT_EXTRA_KEYS.USERNAME_TYPE);
        if (mOldUserName != null)
            getBinding().changeInputText.setText(mOldUserName);
    }

    @Override
    protected UserInfoVM getViewModel() {
        UserInfoVM viewModel = new UsernameVM(getBinding(), this, getApplication(), this);
        viewModel.setChangeType(getActivityTitle());
        viewModel.setInfoText(getInfoText());
        viewModel.setMaxLength(20);
        viewModel.setShowCounter(false);
        return viewModel;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.username);
    }

    public String getInfoText() {
        return getString(R.string.username_info);
    }

    private class UsernameVM extends UserInfoVM {
        public UsernameVM(ActivityUserInfoBinding binding, Context context, Application application, Activity activity) {
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
                    if (s.toString().trim().length() < 5) {
                        getBinding().changeTil.setError(getString(R.string.username_length_error));
                        getBinding().fab.setVisibility(View.INVISIBLE);
                        return;
                    }
                    String pattern = "^[a-zA-Z0-9_]{5,}$";
                    if (s.toString().matches(pattern)) {
                        String userName = s.toString();
                        UsernameActivity.this.getBinding().changeTil.setError("");
                        FirebaseUtils.getUsernamesRef()
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.child(userName).exists()) {
                                            getBinding().changeTil.setError(getString(R.string.username_in_use_error));
                                            getBinding().fab.setVisibility(View.INVISIBLE);
                                            return;
                                        }
                                        getBinding().fab.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                    } else {
                        getBinding().changeTil.setError(getString(R.string.invalid_username_error));
                        getBinding().fab.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            };
        }

        @Override
        public void onChangeClick() {
            DatabaseReference userNamesRef = FirebaseUtils.getUsernamesRef();
            if (mOldUserName != null) {
                userNamesRef.child(mOldUserName).removeValue();
            }
            String username = getBinding().changeInputText.getText().toString();
            userNamesRef.child(username)
                    .setValue(FirebaseUtils.getCurrentUserPhoneNumber());
            FirebaseUtils.getCurrentUserMetaRef().child(FirebaseUtils.USERS_DB.FIELDS.USERNAME)
                    .setValue(username);
            finish();
        }
    }
}
