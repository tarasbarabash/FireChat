package com.tarasbarabash.firechat.ViewModel;

import android.databinding.ViewDataBinding;
import android.view.View;
import android.widget.Toast;

import com.tarasbarabash.firechat.Fragment.BaseFragment;
import com.tarasbarabash.firechat.Model.Message;

import java.util.ArrayList;

/**
 * Created by Taras
 * 5/5/2018, 14:43.
 */

public class MessageVM extends BaseVM<ViewDataBinding> {
    public MessageVM(ViewDataBinding binding, BaseFragment fragment) {
        super(binding, fragment);
    }

    public void onMessageLongClick(Message message) {
        Toast.makeText(getContext(), message.getText(), Toast.LENGTH_LONG).show();
    }
}
