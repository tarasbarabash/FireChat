package com.tarasbarabash.firechat.ViewModel;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.tarasbarabash.firechat.Fragment.BaseFragment;
import com.tarasbarabash.firechat.R;
import com.tarasbarabash.firechat.Utils.OnVerificationCallbacks;
import com.tarasbarabash.firechat.databinding.FragmentCodeBinding;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by Taras
 * 27.04.2018, 13:46.
 */

public class CodeVM extends BaseVM<FragmentCodeBinding> implements TextWatcher,
        OnVerificationCallbacks.OnVerificationListener {
    private static final String TAG = CodeVM.class.getSimpleName();
    private int mTimeout = 60 * 1000;
    private int mRun = 0;

    private PhoneAuthProvider.ForceResendingToken mToken;
    private String mId;
    private OnVerificationCallbacks mCallbacks;
    private String mPhoneNumber;
    private Timer mTimer;
    private Handler mHandler;
    private Runnable mResendCodeRunnable;

    public CodeVM(FragmentCodeBinding binding, BaseFragment fragment, Bundle args) {
        super(binding, fragment);
        mToken = args.getParcelable("token");
        mId = args.getString("verificationId");
        mPhoneNumber = args.getString("phoneNumber");
        getBinding().verificationCode.addTextChangedListener(this);
        setToolbarText(getContext().getString(R.string.verification_code));
        updateResendText();
        mCallbacks = new OnVerificationCallbacks(getFragment(), this);
    }

    private void updateResendText() {
        final TextView resendTimer = getBinding().resendText;
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                FragmentActivity activity = getFragment().getActivity();
                if (activity == null) return;
                activity.runOnUiThread(() -> {
                    mRun++;
                    int period = mTimeout - mRun * 1000;
                    resendTimer.setText(getContext().getResources().getString(R.string.activation_code_resend, String.valueOf(period / 1000)));
                });
            }
        }, 0, 1000);
        mHandler = new Handler();
        mResendCodeRunnable = () -> {
            mTimer.cancel();
            FragmentActivity activity = getFragment().getActivity();
            if (activity == null) return;
            activity.runOnUiThread(() -> getBinding().resendButton.setEnabled(true));
        };
        mHandler.postDelayed(mResendCodeRunnable, mTimeout);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editable.length() == 6) {
            String code = getBinding().verificationCode.getText().toString().trim();
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mId, code);
            mCallbacks.signIn(credential);
            showProgress(true);
        }
        if (editable.length() < 6) {
            CharSequence error = getBinding().vcTil.getError();
            if (error == null || !error.toString().isEmpty())
                getBinding().vcTil.setError("");
        }
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void resendCode() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(mPhoneNumber,
                60,
                TimeUnit.SECONDS,
                getFragment().getActivity(),
                mCallbacks,
                mToken);
    }

    @Override
    public void onSentSuccess(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
        Snackbar.make(getBinding().getRoot().getRootView(), R.string.resend_success, Snackbar.LENGTH_LONG).show();
        getBinding().resendButton.setEnabled(false);
        mRun = 0;
        updateResendText();
    }

    @Override
    public void onSentFailed(FirebaseException e) {
        Log.e(TAG, "onSentFailed: ", e);
    }

    @Override
    public void onError() {
        getBinding().vcTil.setError(getContext().getString(R.string.invalid_code));
        showProgress(false);
    }

    public void showProgress(boolean show) {
        getBinding().progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        getBinding().phoneNumberLayout.setVisibility(show ? View.GONE : View.VISIBLE);
    }
}
