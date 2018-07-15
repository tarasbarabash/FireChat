package com.tarasbarabash.firechat.Fragment;

import android.databinding.ViewDataBinding;
import android.support.v4.app.Fragment;

import com.tarasbarabash.firechat.R;
import com.tarasbarabash.firechat.ViewModel.BaseVM;
import com.tarasbarabash.firechat.ViewModel.ContactsListVM;
import com.tarasbarabash.firechat.databinding.FragmentContactsListBinding;

/**
 * Created by Taras
 * 28.04.2018, 18:17.
 */

public class ContactsListFragment extends BaseFragment<FragmentContactsListBinding> {
    @Override
    public int getLayout() {
        return R.layout.fragment_contacts_list;
    }

    @Override
    protected BaseVM<FragmentContactsListBinding> setViewModel(FragmentContactsListBinding binding) {
        return new ContactsListVM(binding, this);
    }
}
