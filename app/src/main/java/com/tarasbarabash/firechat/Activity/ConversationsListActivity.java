package com.tarasbarabash.firechat.Activity;

import android.support.v4.app.Fragment;

import com.tarasbarabash.firechat.Fragment.ConversationsListFragment;

/**
 * Created by Taras
 * 27.04.2018, 21:14.
 */

public class ConversationsListActivity extends DrawerActivity {

    @Override
    public Fragment getFragment() {
        return new ConversationsListFragment();
    }
}
