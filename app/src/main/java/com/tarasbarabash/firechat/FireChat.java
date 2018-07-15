package com.tarasbarabash.firechat;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tarasbarabash.firechat.Utils.FirebaseUtils;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Taras
 * 26.04.2018, 15:34.
 */

public class FireChat extends MultiDexApplication {
    private FirebaseAuth mFirebaseAuth;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseAuth = FirebaseAuth.getInstance();
        databaseReference.keepSynced(true);
    }

    public FirebaseAuth getFirebaseAuth() {
        return mFirebaseAuth;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
