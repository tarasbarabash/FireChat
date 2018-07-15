package com.tarasbarabash.firechat.Service;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.tarasbarabash.firechat.Auth.AccountGeneral;
import com.tarasbarabash.firechat.Utils.FirebaseUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Taras
 * 5/8/2018, 22:47.
 */

public class ContactsSyncAdapter extends AbstractThreadedSyncAdapter {
    private static final String TAG = ContactsSyncAdapter.class.getSimpleName();

    public ContactsSyncAdapter(Context context, boolean autoInit) {
        this(context, autoInit, false);
    }

    public ContactsSyncAdapter(Context context, boolean authInit, boolean parallelSync) {
        super(context, authInit, parallelSync);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = {
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
        };
        Cursor cursor = getContext().getContentResolver().query(uri, projection, null, null, null);
        cursor.moveToPosition(0);
        while (cursor.moveToNext()) {
            final String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            final String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    .replaceAll("\\s+", "");
            Pattern pattern = Pattern.compile("[*#.\\[\\]]");
            Matcher matcher = pattern.matcher(phoneNumber);
            if (matcher.lookingAt()) continue;
            FirebaseUtils.getUserRef(FirebaseUtils.getCurrentUserPhoneNumber())
                    .child(FirebaseUtils.USERS_DB.FIELDS.CONTACTS)
                    .child(phoneNumber)
                    .child(FirebaseUtils.USERS_DB.FIELDS.NAME)
                    .setValue(name);
//            FirebaseUtils.getUsersRef().addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    boolean found = dataSnapshot.hasChild(phoneNumber);
//                    Map<String, String> contact = new HashMap<>();
//                    contact.put("usingApp", String.valueOf(found));
//                    contact.put("name", name);
//                    FirebaseUtils.getUserRef(FirebaseUtils.getCurrentUserPhoneNumber())
//                            .child(FirebaseUtils.USERS_DB.FIELDS.CONTACTS)
//                            .child(phoneNumber).setValue(contact);
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
        }
    }

    public static void performSync() {
        Bundle b = new Bundle();
        b.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        b.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        ContentResolver.requestSync(AccountGeneral.getAccount(),
                AccountGeneral.ACCOUNT_TYPE, b);
    }
}
