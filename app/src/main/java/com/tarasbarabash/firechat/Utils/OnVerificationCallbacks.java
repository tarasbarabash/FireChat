package com.tarasbarabash.firechat.Utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.tarasbarabash.firechat.Activity.BaseFragmentActivity;
import com.tarasbarabash.firechat.Activity.ConversationsListActivity;
import com.tarasbarabash.firechat.Auth.AccountGeneral;
import com.tarasbarabash.firechat.FireChat;
import com.tarasbarabash.firechat.Fragment.NameFragment;
import com.tarasbarabash.firechat.Fragment.BaseFragment;
import com.tarasbarabash.firechat.R;
import com.tarasbarabash.firechat.Service.ContactsSyncAdapter;

import static com.tarasbarabash.firechat.Utils.Constants.ACCOUNT_TYPE;

/**
 * Created by Taras
 * 27.04.2018, 20:35.
 */

public class OnVerificationCallbacks extends PhoneAuthProvider.OnVerificationStateChangedCallbacks {
    private static final String TAG = OnVerificationCallbacks.class.getSimpleName();
    private BaseFragment mFragment;
    private OnVerificationListener mListener;
    private android.support.v4.app.FragmentActivity mActivity;
    private DatabaseReference mUsersRef;
    private final FireChat mApplication;

    public interface OnVerificationListener {
        void onSentSuccess(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken);

        void onSentFailed(FirebaseException e);

        void onError();
    }

    public OnVerificationCallbacks(BaseFragment fragment, OnVerificationListener listener) {
        mFragment = fragment;
        mActivity = mFragment.getActivity();
        mListener = listener;
        mApplication = (FireChat) mActivity.getApplication();
        mUsersRef = FirebaseUtils.getUsersRef();
    }

    @Override
    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
        signIn(phoneAuthCredential);
    }

    @Override
    public void onVerificationFailed(FirebaseException e) {
        mListener.onSentFailed(e);
    }

    @Override
    public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
        mListener.onSentSuccess(s, forceResendingToken);
    }

    public void signIn(final PhoneAuthCredential credential) {
        mApplication.getFirebaseAuth().signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        onSuccess();
                        AccountGeneral.createSyncAccount(this.mActivity);
                    } else {
                        mListener.onError();
                    }
                });
    }

    private void onSuccess() {
        mUsersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(FirebaseUtils.getCurrentUserPhoneNumber())) {
                    FirebaseUtils.getCurrentUserMetaRef()
                            .child(FirebaseUtils.USERS_DB.FIELDS.DEVICE_ID)
                            .setValue(FirebaseInstanceId.getInstance().getToken()).addOnCompleteListener(task -> {
                                mActivity.startActivity(new Intent(mActivity, ConversationsListActivity.class));
                                mActivity.finish();
                            });
                } else {
                    ((BaseFragmentActivity) mActivity).replaceFragment(NameFragment.newInstance(false, true));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
