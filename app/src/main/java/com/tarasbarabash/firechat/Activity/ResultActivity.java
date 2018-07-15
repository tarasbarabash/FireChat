package com.tarasbarabash.firechat.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Taras
 * 5/9/2018, 14:14.
 */

public abstract class ResultActivity extends BaseFragmentActivity {
    private OnActivityResult mListener;

    public void setListener(OnActivityResult listener) {
        mListener = listener;
    }

    public interface OnActivityResult {
        void onResult(int requestCode, int resultCode, Intent intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mListener.onResult(requestCode, resultCode, data);
    }
}
