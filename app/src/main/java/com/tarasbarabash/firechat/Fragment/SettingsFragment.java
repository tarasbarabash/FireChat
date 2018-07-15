package com.tarasbarabash.firechat.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.takisoft.fix.support.v7.preference.PreferenceFragmentCompat;
import com.tarasbarabash.firechat.Activity.BioActivity;
import com.tarasbarabash.firechat.Activity.NameActivity;
import com.tarasbarabash.firechat.Activity.UsernameActivity;
import com.tarasbarabash.firechat.Model.Profile;
import com.tarasbarabash.firechat.Preference.UserPreference;
import com.tarasbarabash.firechat.R;
import com.tarasbarabash.firechat.Utils.Constants;
import com.tarasbarabash.firechat.Utils.FirebaseUtils;

import static com.tarasbarabash.firechat.Utils.Constants.INTENT_EXTRA_KEYS.BIO_TYPE;
import static com.tarasbarabash.firechat.Utils.Constants.INTENT_EXTRA_KEYS.CONTACT_NAME;
import static com.tarasbarabash.firechat.Utils.Constants.INTENT_EXTRA_KEYS.CONTACT_PHONE_NUMBER;
import static com.tarasbarabash.firechat.Utils.Constants.INTENT_EXTRA_KEYS.USERNAME_TYPE;

/**
 * Created by Taras
 * 5/7/2018, 22:26.
 */

public class SettingsFragment extends PreferenceFragmentCompat {
    private static final String TAG = SettingsFragment.class.getSimpleName();
    private Profile mProfile;
    private String mProfilePhoneNumber;

    public static SettingsFragment newInstance(String phoneNumber) {
        Bundle args = new Bundle();
        args.putString(Constants.INTENT_EXTRA_KEYS.CONTACT_PHONE_NUMBER, phoneNumber);

        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreatePreferencesFix(@Nullable Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.profile_preferences);
        mProfilePhoneNumber = getArguments().getString(CONTACT_PHONE_NUMBER);
        if (mProfilePhoneNumber == null) {
            mProfilePhoneNumber = FirebaseUtils.getCurrentUserPhoneNumber();
        }
        FirebaseUtils.getUserMetaRef(mProfilePhoneNumber).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mProfile = dataSnapshot.getValue(Profile.class);
                PreferenceScreen preferenceScreen = getPreferenceScreen();
                for (int i = 0; i < preferenceScreen.getPreferenceCount(); i++) {
                    Preference preference = preferenceScreen.getPreference(i);
                    if (preference instanceof UserPreference)
                        setUserPreferenceSummary((UserPreference) preference, i, mProfile);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void setUserPreferenceSummary(UserPreference preference, int i, Profile profile) {
        if (getActivity() == null) return;
        switch (i) {
            case 0:
                preference.setSummary(profile.getUserName() == null ? getString(R.string.none) : profile.getAtUserName());
                preference.setOnPreferenceClickListener(new OnEditClickListener(USERNAME_TYPE));
                break;
            case 1:
                preference.setSummary(profile.getBio() == null ? getString(R.string.none) : profile.getBio());
                preference.setOnPreferenceClickListener(new OnEditClickListener(BIO_TYPE));
                break;
            case 2:
                if (!FirebaseUtils.isCurrentUser(mProfilePhoneNumber)) {
                    getPreferenceScreen().removePreference(preference);
                    break;
                }
                preference.setSummary(profile.getName());
                preference.setOnPreferenceClickListener(new OnEditClickListener(CONTACT_NAME));
                break;
        }
    }

    private class OnEditClickListener implements Preference.OnPreferenceClickListener {
        private final String mType;

        public OnEditClickListener(String type) {
            mType = type;
        }

        @Override
        public boolean onPreferenceClick(Preference preference) {
            if (!FirebaseUtils.isCurrentUser(mProfilePhoneNumber)) return false;
            Intent intent = null;
            if (mType.equals(USERNAME_TYPE)) {
                intent = new Intent(getContext(), UsernameActivity.class);
                intent.putExtra(Constants.INTENT_EXTRA_KEYS.USERNAME_TYPE, mProfile.getUserName());
            }
            else if (mType.equals(BIO_TYPE)) {
                intent = new Intent(getContext(), BioActivity.class);
                intent.putExtra(BIO_TYPE, mProfile.getBio());
            }
            else if (mType.equals(CONTACT_NAME)) {
                intent = new Intent(getContext(), NameActivity.class);
            }
            if (intent != null)
                startActivity(intent);
            return true;
        }
    }
}
