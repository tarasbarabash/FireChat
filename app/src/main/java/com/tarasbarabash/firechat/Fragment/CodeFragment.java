package com.tarasbarabash.firechat.Fragment;

import android.os.Bundle;

import com.google.firebase.auth.PhoneAuthProvider;
import com.tarasbarabash.firechat.R;
import com.tarasbarabash.firechat.ViewModel.BaseVM;
import com.tarasbarabash.firechat.ViewModel.CodeVM;
import com.tarasbarabash.firechat.databinding.FragmentCodeBinding;

/**
 * Created by Taras
 * 27.04.2018, 13:42.
 */

public class CodeFragment extends BaseFragment<FragmentCodeBinding> {
    public static CodeFragment newInstance(String verificationId,
                                           PhoneAuthProvider.ForceResendingToken token,
                                           String phoneNumber) {
        CodeFragment codeFragment = new CodeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("verificationId", verificationId);
        bundle.putParcelable("token", token);
        bundle.putString("phoneNumber", phoneNumber);
        codeFragment.setArguments(bundle);
        return codeFragment;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_code;
    }

    @Override
    protected BaseVM<FragmentCodeBinding> setViewModel(FragmentCodeBinding binding) {
        CodeVM codeVM = new CodeVM(binding, this, getArguments());
        binding.setViewModel(codeVM);
        return codeVM;
    }
}
