package com.tarasbarabash.firechat.ViewModel;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.tarasbarabash.firechat.Adapter.ContactsAdapter;
import com.tarasbarabash.firechat.Fragment.BaseFragment;
import com.tarasbarabash.firechat.Model.Profile;
import com.tarasbarabash.firechat.R;
import com.tarasbarabash.firechat.Utils.FirebaseUtils;
import com.tarasbarabash.firechat.databinding.FragmentContactsListBinding;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Taras
 * 28.04.2018, 18:20.
 */

public class ContactsListVM extends BaseVM<FragmentContactsListBinding> {
    private static final String TAG = ContactsListVM.class.getSimpleName();

    public ContactsListVM(FragmentContactsListBinding binding, BaseFragment fragment) {
        super(binding, fragment);
        ((AppCompatActivity) getFragment().getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setToolbarText(getContext().getString(R.string.conversation_start));
        DatabaseReference userContactsRef = FirebaseUtils.getCurrentUserRef()
                .child(FirebaseUtils.USERS_DB.FIELDS.CONTACTS);
        userContactsRef.orderByChild("usingApp").equalTo(true).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> phoneNumbers = new ArrayList<>();
                ArrayList<Profile> profiles = new ArrayList<>();
                for (DataSnapshot s : dataSnapshot.getChildren())
                    phoneNumbers.add(s.getKey());
                for (String p : phoneNumbers)
                    FirebaseUtils.getUserMetaRef(p).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Profile profile = dataSnapshot.getValue(Profile.class);
                            profile.setPhoneNumber(p);
                            profiles.add(profile);
                            if (profiles.size() == phoneNumbers.size())
                                setUpRV(profiles);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: " + databaseError);
            }
        });
    }

    private void setUpRV(ArrayList<Profile> contacts) {
        Collections.sort(contacts, (c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));
        RecyclerView recyclerView = getBinding().contactRv;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ContactsAdapter contactsAdapter = new ContactsAdapter(getContext(), contacts, getFragment());
        recyclerView.setAdapter(contactsAdapter);
        if (contacts.size() == 0)
            hideProgressBar(true, true);
        else hideProgressBar(true, false);
    }

    private void hideProgressBar(boolean hide, boolean error) {
        getBinding().contactRv.setVisibility(hide && !error ? View.VISIBLE : View.GONE);
        getBinding().loading.setVisibility(hide ? View.GONE : View.VISIBLE);
        getBinding().emptyError.setVisibility(error ? View.VISIBLE : View.GONE);
    }
}
