package com.tarasbarabash.firechat.ViewModel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.Patterns;
import android.view.View;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthProvider;
import com.tarasbarabash.firechat.Activity.BaseFragmentActivity;
import com.tarasbarabash.firechat.Fragment.BaseFragment;
import com.tarasbarabash.firechat.Fragment.CodeFragment;
import com.tarasbarabash.firechat.Fragment.PermissionsFragment;
import com.tarasbarabash.firechat.R;
import com.tarasbarabash.firechat.Utils.OnVerificationCallbacks;
import com.tarasbarabash.firechat.Utils.FabPhoneNumberTextWatcher;
import com.tarasbarabash.firechat.databinding.FragmentLoginBinding;

import java.util.concurrent.TimeUnit;

/**
 * Created by Taras
 * 05.04.2018, 13:11.
 */

public class LoginVM extends BaseVM<FragmentLoginBinding> implements PermissionsFragment.OnPermissionResult,
        OnVerificationCallbacks.OnVerificationListener {

    private static final String TAG = LoginVM.class.getSimpleName();

    public LoginVM(FragmentLoginBinding binding, BaseFragment fragment) {
        super(binding, fragment);
        getBinding().phoneNumber.addTextChangedListener(new FabPhoneNumberTextWatcher(getBinding().fab));
        setToolbarText(getContext().getString(R.string.phone_number_toolbar));
    }

    public void onProceed() {
        final String phoneNumber = getBinding().phoneNumber.getText().toString();
        TextInputLayout inputLayout = getBinding().pnTil;
        if (!Patterns.PHONE.matcher(phoneNumber).matches()) {
            inputLayout.setError(getContext().getResources().getString(R.string.phone_number_error));
            return;
        }
        updateUI(true);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                getFragment().getActivity(),
                new OnVerificationCallbacks(getFragment(), this)
        );
    }

    @Override
    public void granted() {
        TelephonyManager telephonyManager = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission")
        String number = telephonyManager.getLine1Number();
        if (number != null)
        getBinding().phoneNumber.setText(PhoneNumberUtils.formatNumber(number));
    }

    @Override
    public void onSentSuccess(String s, PhoneAuthProvider.ForceResendingToken token) {
        CodeFragment codeFragment = CodeFragment.newInstance(s, token, getBinding().phoneNumber.getText().toString());
        BaseFragmentActivity baseActivity = (BaseFragmentActivity) getFragment().getActivity();
        baseActivity.replaceFragment(codeFragment);
    }

    @Override
    public void onSentFailed(FirebaseException e) {
        updateUI(false);
        if (e instanceof FirebaseAuthInvalidCredentialsException) {
            getBinding().pnTil.setError(getContext().getString(R.string.invalid_phone_number_error));
        }
    }

    @Override
    public void onError() {
        Log.i(TAG, "onError: no auto auth; verification code needed to login!");
    }

    private void updateUI(boolean showProgressBar) {
        getBinding().progressBar.setVisibility(showProgressBar ? View.VISIBLE : View.GONE);
        getBinding().phoneNumberLayout.setVisibility(showProgressBar ? View.GONE : View.VISIBLE);
        getBinding().fab.setVisibility(showProgressBar ? View.GONE : View.VISIBLE);
    }
}
