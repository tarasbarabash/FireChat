package com.tarasbarabash.firechat.Auth;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;

import com.tarasbarabash.firechat.Service.ContactsSyncAdapter;

/**
 * Created by Taras
 * 5/8/2018, 22:40.
 */

public class AccountGeneral {
    public static final String ACCOUNT_TYPE = "com.tarasbarabash.firechat";
    public static final String ACCOUNT_NAME = "FireChat";

    public static Account getAccount() {
        return new Account(ACCOUNT_NAME, ACCOUNT_TYPE);
    }

    public static void createSyncAccount(Context c) {
        boolean created = false;
        Account account = getAccount();
        AccountManager manager = (AccountManager)c.getSystemService(Context.ACCOUNT_SERVICE);

        if (manager.addAccountExplicitly(account, null, null)) {
            final String AUTHORITY = ACCOUNT_TYPE;
            final long SYNC_FREQUENCY = 60 * 60;
            ContentResolver.setIsSyncable(account, AUTHORITY, 1);
            ContentResolver.setSyncAutomatically(account, AUTHORITY, true);
            ContentResolver.addPeriodicSync(account, AUTHORITY, new Bundle(), SYNC_FREQUENCY);
            created = true;
        }

        if (created) {
            ContactsSyncAdapter.performSync();
        }
    }
}
