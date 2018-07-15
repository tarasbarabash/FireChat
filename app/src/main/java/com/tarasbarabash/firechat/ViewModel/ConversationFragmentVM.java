package com.tarasbarabash.firechat.ViewModel;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.tarasbarabash.firechat.Adapter.MessagesAdapter;
import com.tarasbarabash.firechat.Fragment.BaseFragment;
import com.tarasbarabash.firechat.Model.Message;
import com.tarasbarabash.firechat.Model.Profile;
import com.tarasbarabash.firechat.R;
import com.tarasbarabash.firechat.Utils.Constants;
import com.tarasbarabash.firechat.Utils.FirebaseUtils;
import com.tarasbarabash.firechat.Utils.MessageTextWatcher;
import com.tarasbarabash.firechat.Utils.TimeUtils;
import com.tarasbarabash.firechat.databinding.FragmentAttachmentBottomSheetBinding;
import com.tarasbarabash.firechat.databinding.FragmentConversationBinding;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Taras
 * 01.05.2018, 12:17.
 */

public class ConversationFragmentVM extends UploadVM<FragmentConversationBinding> {
    private static final String TAG = ConversationFragmentVM.class.getSimpleName();
    private String mConversationId;
    private DatabaseReference mMessagesRef;
    private DatabaseReference mSenderConversationsRef;
    private String mRecipientPhoneNumber;
    private BottomSheetDialog mBottomSheet;
    private MessagesAdapter mAdapter;


    public ConversationFragmentVM(FragmentConversationBinding binding, BaseFragment fragment, Bundle args) {
        super(binding, fragment);
        loadMessages(args);
        getBinding().sendLayout.send.setEnabled(false);
        getBinding().sendLayout.messageET.addTextChangedListener(new MessageTextWatcher(getBinding()));
        setUpRV();
    }

    private void loadMessages(Bundle args) {
        mRecipientPhoneNumber = args.getString(Constants.INTENT_EXTRA_KEYS.CONTACT_PHONE_NUMBER);
        mSenderConversationsRef = FirebaseUtils.getCurrentUserRef()
                .child(FirebaseUtils.USERS_DB.FIELDS.CONVERSATIONS)
                .child(mRecipientPhoneNumber);
        mSenderConversationsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mConversationId = dataSnapshot.getValue(String.class);
                if (mConversationId == null) {
                    updateUI(true);
                    return;
                }
                Log.i(TAG, "onDataChange: " + mConversationId);
                mMessagesRef = FirebaseUtils.getConversationsRef()
                        .child(mConversationId)
                        .child(FirebaseUtils.CONVERSATIONS_DB.FIELDS.MESSAGES);
                Log.i(TAG, "onDataChange: " + mMessagesRef);
                mMessagesRef.orderByChild(FirebaseUtils.CONVERSATIONS_DB.FIELDS.TIME)
                        .addValueEventListener(new MessagesEventListener());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setUpRV() {
        RecyclerView recyclerView = getBinding().messagesRV;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new MessagesAdapter(getContext(), null, getFragment());
        recyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                recyclerView.smoothScrollToPosition(mAdapter.getList().size());
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    private void updateUI(boolean showEmpty) {
        getBinding().emptyHistory.setVisibility(showEmpty ? View.VISIBLE : View.GONE);
        getBinding().messagesRV.setVisibility(!showEmpty ? View.VISIBLE : View.GONE);
        getBinding().progressBar.setVisibility(View.GONE);
    }

    public void onSendClick() {
        int type = Constants.MESSAGES_TYPES.TEXT;
        String text = getBinding().sendLayout.messageET.getText().toString().trim();
        getBinding().sendLayout.messageET.setText("");
        sendMessage(type, text);
    }

    private void sendMessage(int type, String text) {
        long time = TimeUtils.getTimeInMillisUTC();
        String sender = FirebaseUtils.getCurrentUserPhoneNumber();
        Message message = new Message(sender, mRecipientPhoneNumber, text, type, time);
        if (mConversationId == null) {
            mConversationId = UUID.randomUUID().toString();
            mSenderConversationsRef.setValue(mConversationId);
            FirebaseUtils.getUserRef(mRecipientPhoneNumber)
                    .child(FirebaseUtils.USERS_DB.FIELDS.CONVERSATIONS)
                    .child(FirebaseUtils.getCurrentUserPhoneNumber())
                    .setValue(mConversationId);
            DatabaseReference convRef = FirebaseUtils.getConversationsRef()
                    .child(mConversationId);
            mMessagesRef = convRef
                    .child(FirebaseUtils.CONVERSATIONS_DB.FIELDS.MESSAGES);
            mMessagesRef.addValueEventListener(new MessagesEventListener());
        }
        mMessagesRef.push().setValue(message);
    }

    public void onAttach() {
        mBottomSheet = new BottomSheetDialog(getContext());
        FragmentAttachmentBottomSheetBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(getContext()),
                R.layout.fragment_attachment_bottom_sheet,
                null,
                false
        );
        binding.setViewModel(this);
        mBottomSheet.setContentView(binding.getRoot());
        mBottomSheet.show();
    }

    @Override
    protected void uploadImage(Uri uri) {
        FirebaseUtils.getMessagesPicsStorageRef().child(uri.getLastPathSegment() + ".jpg").putFile(uri).addOnCompleteListener(task -> {
            if (!task.isSuccessful()) Log.e(TAG, "onComplete: ", task.getException());
            else {
                Uri imageUri = task.getResult().getDownloadUrl();
                Log.i(TAG, "uploadImage: " + imageUri);
                sendMessage(Constants.MESSAGES_TYPES.IMAGE, imageUri.toString());
            }
        });
        mBottomSheet.hide();
    }

    public class MessagesEventListener implements ValueEventListener {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            ArrayList<Message> messages = new ArrayList<>();
            for (DataSnapshot d : dataSnapshot.getChildren()) {
                Message message = d.getValue(Message.class);
                message.setId(d.getKey());
                messages.add(message);
            }
            if (mMessagesRef == null)
                updateUI(true);
            else
                updateUI(false);
            mAdapter.swapList(messages);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }
}
