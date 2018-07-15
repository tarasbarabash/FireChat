package com.tarasbarabash.firechat.Fragment;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.tarasbarabash.firechat.Auth.AccountGeneral;
import com.tarasbarabash.firechat.BuildConfig;
import com.tarasbarabash.firechat.R;

/**
 * Created by Taras
 * 27.04.2018, 11:15.
 */

public abstract class PermissionsFragment<T extends ViewDataBinding> extends BaseFragment<T> {
    private int requestCode;
    private OnPermissionResult mOnPermissionResult;

    public interface OnPermissionResult {
        void granted();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (this.requestCode == requestCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mOnPermissionResult.granted();
            } else denied();
        }
    }

    private void denied() {
        Snackbar.make(getView(), R.string.denied_permission, Snackbar.LENGTH_LONG).show();
        ContentResolver.setIsSyncable(AccountGeneral.getAccount(), BuildConfig.APPLICATION_ID, 0);
    }

    public void setListener(OnPermissionResult listener) {
        mOnPermissionResult = listener;
    }

    public void needPermission(String permission, int requestCode) {
        this.requestCode = requestCode;
        if (ContextCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[] {permission}, requestCode);
        }
        else mOnPermissionResult.granted();
    }
}
