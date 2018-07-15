package com.tarasbarabash.firechat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.tarasbarabash.firechat.Activity.ConversationActivity;
import com.tarasbarabash.firechat.BR;
import com.tarasbarabash.firechat.Model.Conversation;
import com.tarasbarabash.firechat.Model.Message;
import com.tarasbarabash.firechat.Model.Profile;
import com.tarasbarabash.firechat.R;
import com.tarasbarabash.firechat.Utils.Constants;
import com.tarasbarabash.firechat.Utils.FirebaseUtils;
import com.tarasbarabash.firechat.ViewModel.ConversationsListVM;
import com.tarasbarabash.firechat.databinding.ItemConversationBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Taras
 * 03.05.2018, 14:54.
 */

public class ConversationsAdapter extends BaseAdapter<ItemConversationBinding, Message> {
    private static final String TAG = ConversationsAdapter.class.getSimpleName();
    private OnNewListListener mListener;

    public interface OnNewListListener {
        void onNewList(boolean isZeroSized);
    }

    public ConversationsAdapter(Context context, ArrayList<Message> list,
                                OnNewListListener listListener) {
        super(context, list);
        mListener = listListener;
    }

    @Override
    public BaseViewHolder<ItemConversationBinding, Message> getViewHolder(final ItemConversationBinding binding) {
        return new BaseViewHolder<ItemConversationBinding, Message>(binding) {
            @Override
            public void bind(Message message) {
                FirebaseUtils.getCurrentUserRef()
                        .child(FirebaseUtils.USERS_DB.FIELDS.CONVERSATIONS)
                        .orderByValue()
                        .equalTo(message.getConvId()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String userId = dataSnapshot.getChildren().iterator().next().getKey();
                        FirebaseUtils.getUserMetaRef(userId).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Conversation conversation = new Conversation(dataSnapshot.getValue(Profile.class), message, userId);
                                binding.setConversation(conversation);
                                binding.setViewModel(ConversationsAdapter.this);
                                binding.executePendingBindings();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public int getVariableName() {
                return BR.conversation;
            }
        };
    }

    @Override
    public int getLayout(int viewType) {
        return R.layout.item_conversation;
    }

    public void onConversationSelected(View view, Conversation conversation) {
        Intent intent = new Intent(getContext(), ConversationActivity.class);
        intent.putExtra(Constants.INTENT_EXTRA_KEYS.CONTACT_PHONE_NUMBER, conversation.getPhoneNumber());
        getContext().startActivity(intent);
    }

    public ArrayList<Message> swapList(ArrayList<Message> messages) {
        ArrayList<Message> oldList = getList();
        mListener.onNewList(messages.isEmpty());
        setList(messages);
        Collections.sort(getList(), (o1, o2) -> Long.compare(o2.getTime(), o1.getTime()));
        notifyDataSetChanged();
        return oldList;
    }
}
