package com.tarasbarabash.firechat.Adapter;

import android.content.Context;

import com.tarasbarabash.firechat.BR;
import com.tarasbarabash.firechat.Fragment.BaseFragment;
import com.tarasbarabash.firechat.Model.Profile;
import com.tarasbarabash.firechat.R;
import com.tarasbarabash.firechat.ViewModel.ContactsItemVM;
import com.tarasbarabash.firechat.databinding.ItemContactBinding;

import java.util.ArrayList;

/**
 * Created by Taras
 * 03.05.2018, 15:10.
 */

public class ContactsAdapter extends BaseAdapter<ItemContactBinding, Profile> {
    private BaseFragment mFragment;

    public ContactsAdapter(Context context, ArrayList<Profile> list, BaseFragment fragment) {
        super(context, list);
        mFragment = fragment;
    }

    @Override
    public BaseViewHolder<ItemContactBinding, Profile> getViewHolder(ItemContactBinding binding) {
        return new BaseViewHolder<ItemContactBinding, Profile>(binding) {
            @Override
            public void bind(Profile object) {
                super.bind(object);
                binding.setViewModel(new ContactsItemVM(binding, mFragment));
            }

            @Override
            public int getVariableName() {
                return BR.profile;
            }
        };
    }

    @Override
    public int getLayout(int viewType) {
        return R.layout.item_contact;
    }
}
