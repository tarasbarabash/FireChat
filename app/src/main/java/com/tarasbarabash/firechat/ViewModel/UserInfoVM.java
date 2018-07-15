package com.tarasbarabash.firechat.ViewModel;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.text.TextWatcher;

import com.tarasbarabash.firechat.databinding.ActivityUserInfoBinding;

/**
 * Created by Taras
 * 5/8/2018, 12:50.
 */

public abstract class UserInfoVM extends BaseVM <ActivityUserInfoBinding>{
    private String changeType;
    private String infoText;
    private int maxLength;
    private boolean showCounter;

    public UserInfoVM(ActivityUserInfoBinding binding, Context context, Application application, Activity activity) {
        super(binding, context, application, activity);
        TextWatcher textWatcher = getTextWatcher();
        if (textWatcher != null)
            getBinding().changeInputText.addTextChangedListener(textWatcher);
    }

    protected abstract TextWatcher getTextWatcher();

    public abstract void onChangeClick();

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public String getInfoText() {
        return infoText;
    }

    public void setInfoText(String infoText) {
        this.infoText = infoText;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public boolean isShowCounter() {
        return showCounter;
    }

    public void setShowCounter(boolean showCounter) {
        this.showCounter = showCounter;
    }
}
