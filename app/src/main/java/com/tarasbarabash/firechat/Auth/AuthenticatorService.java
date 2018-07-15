package com.tarasbarabash.firechat.Auth;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Taras
 * 5/8/2018, 22:44.
 */

public class AuthenticatorService extends Service {
    private AccountAuthenticator mAccountAuthenticator;

    @Override
    public void onCreate() {
        mAccountAuthenticator = new AccountAuthenticator(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mAccountAuthenticator.getIBinder();
    }
}
