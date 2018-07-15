package com.tarasbarabash.firechat.ViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.tarasbarabash.firechat.Activity.ConversationsListActivity;
import com.tarasbarabash.firechat.Fragment.BaseFragment;
import com.tarasbarabash.firechat.R;
import com.tarasbarabash.firechat.Utils.Constants;
import com.tarasbarabash.firechat.Utils.FabNameTextWatcher;
import com.tarasbarabash.firechat.Utils.FirebaseUtils;
import com.tarasbarabash.firechat.databinding.FragmentNameBinding;

/**
 * Created by Taras
 * 29.04.2018, 20:23.
 */

public class NameVM extends BaseVM<FragmentNameBinding> {
    private static final String TAG = NameVM.class.getSimpleName();
    private boolean mIsSignUpFinish;
    private boolean mIsSignUp;

    public NameVM(FragmentNameBinding binding, BaseFragment fragment, Bundle args) {
        super(binding, fragment);
        setToolbarText(getContext().getString(R.string.name_title));
        getBinding().firstName.addTextChangedListener(new FabNameTextWatcher(getBinding(), getContext()));
        mIsSignUpFinish = args.getBoolean(Constants.INTENT_EXTRA_KEYS.SIGN_UP_FINISH);
        mIsSignUp = args.getBoolean(Constants.INTENT_EXTRA_KEYS.IS_SIGN_UP);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(!mIsSignUp);
    }

    public void onProceed() {
        String firstName = getBinding().firstName.getText().toString();
        String secondName = getBinding().secondName.getText().toString();
        DatabaseReference userRef = FirebaseUtils.getCurrentUserMetaRef();
        String name = firstName + " " + secondName;
        userRef.child(FirebaseUtils.USERS_DB.FIELDS.NAME).setValue(name.trim());
        if (mIsSignUp)
            getContext().startActivity(new Intent(getContext(), ConversationsListActivity.class));
        getFragment().getActivity().finish();
    }

    public boolean isSignUpFinish() {
        return mIsSignUpFinish;
    }
}
