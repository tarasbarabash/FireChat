package com.tarasbarabash.firechat.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Taras
 * 5/8/2018, 22:52.
 */

public class SyncService extends Service {
    private static final Object LOCK = new Object();
    private static ContactsSyncAdapter syncAdapter;


    @Override
    public void onCreate() {
        synchronized (LOCK) {
            syncAdapter = new ContactsSyncAdapter(this, false);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return syncAdapter.getSyncAdapterBinder();
    }

}
