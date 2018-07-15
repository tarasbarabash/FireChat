package com.tarasbarabash.firechat.Activity;

import android.support.v4.app.Fragment;

import com.tarasbarabash.firechat.Fragment.NameFragment;

/**
 * Created by Taras
 * 5/8/2018, 16:35.
 */

public class NameActivity extends BaseFragmentActivity {
    @Override
    public Fragment getFragment() {
        return NameFragment.newInstance(false, false);
    }
}
