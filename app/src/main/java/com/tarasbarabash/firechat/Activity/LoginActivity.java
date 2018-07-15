package com.tarasbarabash.firechat.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.tarasbarabash.firechat.Fragment.LoginFragment;
import com.tarasbarabash.firechat.Fragment.NameFragment;
import com.tarasbarabash.firechat.Utils.Constants;

public class LoginActivity extends BaseFragmentActivity {

    @Override
    public Fragment getFragment() {
        boolean finishSignUp = getIntent().
                getBooleanExtra(Constants.INTENT_EXTRA_KEYS.SIGN_UP_FINISH, false);
        if (finishSignUp)
            return NameFragment.newInstance(true, true);
        else
            return new LoginFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
