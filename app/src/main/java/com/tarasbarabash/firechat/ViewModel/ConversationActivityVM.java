package com.tarasbarabash.firechat.ViewModel;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.tarasbarabash.firechat.Activity.ContactsListActivity;
import com.tarasbarabash.firechat.Activity.ConversationsListActivity;
import com.tarasbarabash.firechat.Activity.SettingsActivity;
import com.tarasbarabash.firechat.Model.Profile;
import com.tarasbarabash.firechat.Utils.Constants;
import com.tarasbarabash.firechat.Utils.FirebaseUtils;
import com.tarasbarabash.firechat.databinding.ActivityConversationBinding;

/**
 * Created by Taras
 * 01.05.2018, 11:42.
 */

public class ConversationActivityVM extends BaseVM<ActivityConversationBinding> {
    private static final String TAG = ConversationActivityVM.class.getSimpleName();
    private String mPhoneNumber;

    public ConversationActivityVM(ActivityConversationBinding binding,
                                  Context context,
                                  Application application,
                                  Activity activity,
                                  Intent intent) {
        super(binding, context, application, activity);
        mPhoneNumber = intent.getStringExtra(Constants.INTENT_EXTRA_KEYS.CONTACT_PHONE_NUMBER);
        if (mPhoneNumber == null) {
            getActivity().startActivity(new Intent(getContext(), ConversationsListActivity.class));
            getActivity().finish();
            return;
        }
        loadProfileInfo();
    }

    private void loadProfileInfo() {
        FirebaseUtils.getUserMetaRef(mPhoneNumber).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Profile profile = dataSnapshot.getValue(Profile.class);
                profile.setPhoneNumber(mPhoneNumber);
                getBinding().setProfile(profile);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void onProfileClick(Profile profile) {
        Intent intent = new Intent(getContext(), SettingsActivity.class);
        intent.putExtra(Constants.INTENT_EXTRA_KEYS.CONTACT_PHONE_NUMBER, profile.getPhoneNumber());
        getActivity().startActivity(intent);
    }
}
