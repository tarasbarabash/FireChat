package com.tarasbarabash.firechat.Activity;

import android.support.v4.app.Fragment;

import com.tarasbarabash.firechat.Fragment.ContactsListFragment;

/**
 * Created by Taras
 * 28.04.2018, 18:15.
 */

public class ContactsListActivity extends BaseFragmentActivity {
    @Override
    public Fragment getFragment() {
        return new ContactsListFragment();
    }
}
