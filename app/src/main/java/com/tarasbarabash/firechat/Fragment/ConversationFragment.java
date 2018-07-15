package com.tarasbarabash.firechat.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.tarasbarabash.firechat.Activity.ConversationActivity;
import com.tarasbarabash.firechat.Activity.ResultActivity;
import com.tarasbarabash.firechat.R;
import com.tarasbarabash.firechat.ViewModel.BaseVM;
import com.tarasbarabash.firechat.ViewModel.ConversationFragmentVM;
import com.tarasbarabash.firechat.databinding.FragmentConversationBinding;

/**
 * Created by Taras
 * 01.05.2018, 11:40.
 */

public class ConversationFragment extends BaseFragment<FragmentConversationBinding> {
    public static ConversationFragment newInstance(Bundle args) {
        ConversationFragment fragment = new ConversationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_conversation;
    }

    @Override
    protected BaseVM<FragmentConversationBinding> setViewModel(FragmentConversationBinding binding) {
        ConversationFragmentVM viewModel = new ConversationFragmentVM(binding, this, getArguments());
        ((ConversationActivity) getActivity()).setListener(viewModel);
        binding.setViewModel(viewModel);
        return viewModel;
    }
}
