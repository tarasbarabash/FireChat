package com.tarasbarabash.firechat.Adapter;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.util.Log;

import com.android.databinding.library.baseAdapters.BR;
import com.google.firebase.auth.FirebaseAuth;
import com.stfalcon.frescoimageviewer.ImageViewer;
import com.tarasbarabash.firechat.Fragment.BaseFragment;
import com.tarasbarabash.firechat.Model.Message;
import com.tarasbarabash.firechat.R;
import com.tarasbarabash.firechat.Utils.Constants;
import com.tarasbarabash.firechat.Utils.FirebaseUtils;
import com.tarasbarabash.firechat.ViewModel.MessageVM;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Taras
 * 03.05.2018, 15:27.
 */

public class MessagesAdapter extends BaseAdapter<ViewDataBinding, Message> {
    private static final String TAG = MessagesAdapter.class.getSimpleName();
    private boolean showDate;
    private static final int INCOMING = 1;
    private static final int OUTGOING = 2;
    private static final int IMAGE = 3;
    private static final int TEXT = 5;
    private BaseFragment mFragment;

    public MessagesAdapter(Context context, ArrayList<Message> list, BaseFragment fragment) {
        super(context, list);
        mFragment = fragment;
    }

    @Override
    public int getItemViewType(int position) {
        Message message = getList().get(position);
        if (position >= 1) {
            Message prMessage = getList().get(position - 1);
            Calendar mesDay = Calendar.getInstance();
            Calendar prMesDay = Calendar.getInstance();
            mesDay.setTimeInMillis(message.getTime());
            prMesDay.setTimeInMillis(prMessage.getTime());
            showDate = (mesDay.get(Calendar.DAY_OF_YEAR) - prMesDay.get(Calendar.DAY_OF_YEAR)) >= 1;
        } else showDate = true;
        boolean isIncoming = !FirebaseUtils.isCurrentUser(message.getSender());
        int mesType = message.getType() == Constants.MESSAGES_TYPES.TEXT ? TEXT : IMAGE;
        return isIncoming ? INCOMING + mesType : OUTGOING + mesType;
    }

    @Override
    public BaseViewHolder<ViewDataBinding, Message> getViewHolder(ViewDataBinding binding) {
        return new BaseViewHolder<ViewDataBinding, Message>(binding) {
            @Override
            public int getVariableName() {
                return BR.message;
            }

            @Override
            public void bind(Message object) {
                binding.setVariable(BR.adapter, MessagesAdapter.this);
                binding.setVariable(BR.viewModel, new MessageVM(binding, mFragment));
                super.bind(object);
            }
        };
    }

    @Override
    public int getLayout(int viewType) {
            switch (viewType) {
                case INCOMING + TEXT:
                    return R.layout.item_incoming_message;
                case INCOMING + IMAGE:
                    return R.layout.item_incoming_image;
                case OUTGOING + TEXT:
                    return R.layout.item_outgoing_message;
                case OUTGOING + IMAGE:
                    return R.layout.item_outgoing_image;
            }
        return 0;
    }

    public boolean isShowDate() {
        return showDate;
    }


    public void onOpen(Message message) {
        String[] uris = new String[]{message.getText()};
        new ImageViewer.Builder<>(getContext(), uris)
                .setStartPosition(0)
                .show();
    }
}
