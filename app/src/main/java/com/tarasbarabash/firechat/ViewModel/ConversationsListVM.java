package com.tarasbarabash.firechat.ViewModel;

import android.content.ContentResolver;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.tarasbarabash.firechat.Activity.ContactsListActivity;
import com.tarasbarabash.firechat.Activity.LoginActivity;
import com.tarasbarabash.firechat.Adapter.ConversationsAdapter;
import com.tarasbarabash.firechat.Auth.AccountGeneral;
import com.tarasbarabash.firechat.BuildConfig;
import com.tarasbarabash.firechat.Fragment.BaseFragment;
import com.tarasbarabash.firechat.Fragment.PermissionsFragment;
import com.tarasbarabash.firechat.Model.Message;
import com.tarasbarabash.firechat.R;
import com.tarasbarabash.firechat.Service.ContactsSyncAdapter;
import com.tarasbarabash.firechat.Utils.FirebaseUtils;
import com.tarasbarabash.firechat.databinding.FragmentConversationsListBinding;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Taras
 * 28.04.2018, 14:06.
 */

public class ConversationsListVM extends BaseVM<FragmentConversationsListBinding>
        implements PermissionsFragment.OnPermissionResult, ConversationsAdapter.OnNewListListener {
    private static final String TAG = ConversationsListVM.class.getSimpleName();
    private DatabaseReference mUserRef;
    private ArrayList<Message> mMessages;
    private ConversationsAdapter mConversationsAdapter;

    public ConversationsListVM(FragmentConversationsListBinding binding, BaseFragment fragment) {
        super(binding, fragment);
        setToolbarText(getContext().getString(R.string.chats_toolbar_title));
        loadConversations();
        setupRV();
    }

    private void loadConversations() {
        mUserRef = FirebaseUtils.getCurrentUserRef();
        DatabaseReference conversationsRef = mUserRef
                .child(FirebaseUtils.USERS_DB.FIELDS.CONVERSATIONS);
        conversationsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mMessages = new ArrayList<>();
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    String convId = snap.getValue(String.class);
                    FirebaseUtils.getConversationsRef().child(convId)
                            .child(FirebaseUtils.CONVERSATIONS_DB.FIELDS.MESSAGES)
                            .orderByChild(FirebaseUtils.CONVERSATIONS_DB.FIELDS.TIME)
                            .limitToLast(1).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Message message = dataSnapshot.getChildren().iterator().next().getValue(Message.class);
                            String cId = dataSnapshot.getRef().getParent().getKey();
                            message.setConvId(cId);
                            for (int i = 0; i < mMessages.size(); i++)
                                if (mMessages.get(i).getConvId().equals(cId)) mMessages.remove(i);
                            mMessages.add(message);
                            mConversationsAdapter.swapList(mMessages);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void logout() {
        FirebaseUtils.getCurrentUserMetaRef().child(FirebaseUtils.USERS_DB.FIELDS.DEVICE_ID).removeValue();
        getApplication().getFirebaseAuth().signOut();
        getContext().startActivity(new Intent(getContext(), LoginActivity.class));
        getFragment().getActivity().finish();
    }

    public void onStartConversation() {
        getContext().startActivity(new Intent(getContext(), ContactsListActivity.class));
    }

    @Override
    public void granted() {
        ContentResolver.setIsSyncable(AccountGeneral.getAccount(), BuildConfig.APPLICATION_ID, 2);
        ContactsSyncAdapter.performSync();
        hideSyncBar(true);
    }

    private void hideSyncBar(boolean hide) {
        getBinding().syncProgressbar.setVisibility(hide ? View.GONE : View.VISIBLE);
        getBinding().recyclerView.setVisibility(hide ? View.VISIBLE : View.GONE);
        getBinding().startConversation.setVisibility(hide ? View.VISIBLE : View.GONE);
    }

    private void hideEmptyView(boolean hide) {
        getBinding().emptyText.setVisibility(hide ? View.GONE : View.VISIBLE);
    }

    private void setupRV() {
        RecyclerView recyclerView = getBinding().recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mConversationsAdapter = new ConversationsAdapter(getContext(), mMessages, this);
        recyclerView.setAdapter(mConversationsAdapter);
    }

    @Override
    public void onNewList(boolean isZeroSized) {
        hideEmptyView(!isZeroSized);
    }
}
