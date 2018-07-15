package com.tarasbarabash.firechat.Activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.tarasbarabash.firechat.FireChat;
import com.tarasbarabash.firechat.Fragment.ConversationFragment;
import com.tarasbarabash.firechat.Fragment.SettingsFragment;
import com.tarasbarabash.firechat.R;
import com.tarasbarabash.firechat.Utils.Constants;
import com.tarasbarabash.firechat.Utils.FirebaseUtils;
import com.tarasbarabash.firechat.ViewModel.ConversationFragmentVM;
import com.tarasbarabash.firechat.ViewModel.UploadVM;
import com.tarasbarabash.firechat.databinding.ActivityDrawerBinding;
import com.tarasbarabash.firechat.databinding.FragmentConversationBinding;

/**
 * Created by Taras
 * 27.04.2018, 18:44.
 */

public class SplashActivity extends AppCompatActivity {
    private interface OnUserInfoFetched {
        void onResult(boolean exists);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final OnUserInfoFetched listener = new OnUserInfoFetchedImpl();
        if (FirebaseUtils.getUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {
            FirebaseUtils.getUsersRef().addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    listener.onResult(dataSnapshot.hasChild(FirebaseUtils.getCurrentUserPhoneNumber()));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    public class OnUserInfoFetchedImpl implements OnUserInfoFetched {
        @Override
        public void onResult(boolean exists) {
            if (exists)
                startActivity(new Intent(getApplicationContext(), ConversationsListActivity.class));
            else {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.putExtra(Constants.INTENT_EXTRA_KEYS.SIGN_UP_FINISH, true);
                startActivity(intent);
            }
            finish();
        }
    }
}
