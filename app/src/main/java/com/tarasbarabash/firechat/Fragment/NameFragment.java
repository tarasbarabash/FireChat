package com.tarasbarabash.firechat.Fragment;

import android.os.Bundle;

import com.tarasbarabash.firechat.Fragment.BaseFragment;
import com.tarasbarabash.firechat.R;
import com.tarasbarabash.firechat.Utils.Constants;
import com.tarasbarabash.firechat.ViewModel.BaseVM;
import com.tarasbarabash.firechat.ViewModel.NameVM;
import com.tarasbarabash.firechat.databinding.FragmentNameBinding;

/**
 * Created by Taras
 * 29.04.2018, 20:22.
 */

public class NameFragment extends BaseFragment<FragmentNameBinding> {
    public static NameFragment newInstance(boolean signUpFinish, boolean isSignUp) {
        Bundle args = new Bundle();
        args.putBoolean(Constants.INTENT_EXTRA_KEYS.SIGN_UP_FINISH, signUpFinish);
        args.putBoolean(Constants.INTENT_EXTRA_KEYS.IS_SIGN_UP, isSignUp);

        NameFragment fragment = new NameFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_name;
    }

    @Override
    protected BaseVM<FragmentNameBinding> setViewModel(FragmentNameBinding binding) {
        NameVM nameVM = new NameVM(binding, this, getArguments());
        binding.setViewModel(nameVM);
        return nameVM;
    }
}
