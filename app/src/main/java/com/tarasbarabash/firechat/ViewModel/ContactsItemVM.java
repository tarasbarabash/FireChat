package com.tarasbarabash.firechat.ViewModel;

import android.content.Intent;
import android.view.View;

import com.tarasbarabash.firechat.Activity.ConversationActivity;
import com.tarasbarabash.firechat.Fragment.BaseFragment;
import com.tarasbarabash.firechat.Model.Profile;
import com.tarasbarabash.firechat.databinding.ItemContactBinding;

import static com.tarasbarabash.firechat.Utils.Constants.INTENT_EXTRA_KEYS.CONTACT_PHONE_NUMBER;

/**
 * Created by Taras
 * 30.04.2018, 21:44.
 */

public class ContactsItemVM extends BaseVM<ItemContactBinding> {
    public ContactsItemVM(ItemContactBinding binding, BaseFragment fragment) {
        super(binding, fragment);
    }

    public void onContactClicked(View view, Profile profile) {
        Intent intent = new Intent(view.getContext(), ConversationActivity.class);
        intent.putExtra(CONTACT_PHONE_NUMBER, profile.getPhoneNumber());
        view.getContext().startActivity(intent);
        getFragment().getActivity().finish();
    }
}
