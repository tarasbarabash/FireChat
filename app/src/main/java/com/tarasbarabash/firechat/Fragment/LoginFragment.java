package com.tarasbarabash.firechat.Fragment;

import android.Manifest;
import android.support.annotation.NonNull;

import com.tarasbarabash.firechat.R;
import com.tarasbarabash.firechat.ViewModel.BaseVM;
import com.tarasbarabash.firechat.ViewModel.LoginVM;
import com.tarasbarabash.firechat.databinding.FragmentLoginBinding;

public class LoginFragment extends PermissionsFragment<FragmentLoginBinding> {

    @Override
    public int getLayout() {
        return R.layout.fragment_login;
    }

    @Override
    protected BaseVM<FragmentLoginBinding> setViewModel(FragmentLoginBinding binding) {
        LoginVM viewModel = new LoginVM(binding, this);
        binding.setViewModel(viewModel);
        setListener(viewModel);
        needPermission(Manifest.permission.READ_PHONE_STATE, 101);
        return viewModel;
    }
}
