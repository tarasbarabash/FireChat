package com.tarasbarabash.firechat.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.tarasbarabash.firechat.Utils.FirebaseUtils;
import com.tarasbarabash.firechat.Utils.TimeUtils;

/**
 * Created by Taras
 * 5/7/2018, 17:54.
 */

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getSimpleName();

    @Override
    protected void onResume() {
        super.onResume();

        if (FirebaseUtils.getUser() == null) return;
        FirebaseUtils.getCurrentUserMetaRef().child(FirebaseUtils.USERS_DB.FIELDS.ONLINE)
                .setValue(true);
        FirebaseUtils.getCurrentUserMetaRef().child(FirebaseUtils.USERS_DB.FIELDS.ONLINE)
                .onDisconnect().setValue(false);
        FirebaseUtils.getCurrentUserMetaRef().child(FirebaseUtils.USERS_DB.FIELDS.LAST_SEEN)
                .onDisconnect().setValue(TimeUtils.getTimeInMillisUTC());
    }
}
