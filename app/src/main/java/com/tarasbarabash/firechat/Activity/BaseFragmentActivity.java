package com.tarasbarabash.firechat.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.tarasbarabash.firechat.R;

/**
 * Created by Taras
 * 04.04.2018, 13:37.
 */

public abstract class BaseFragmentActivity extends BaseActivity {

    private static final String TAG = BaseFragmentActivity.class.getSimpleName();
    private FragmentManager mFragmentManager;
    private static final int containerId = R.id.fragment_container;

    public abstract Fragment getFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFragmentManager = getSupportFragmentManager();
        Fragment fragment = mFragmentManager.findFragmentById(containerId);
        if (fragment == null) {
            fragment = getFragment();
            String tag = fragment.getClass().getSimpleName();
            mFragmentManager.beginTransaction().add(R.id.fragment_container, fragment, tag).commit();
        }
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out);
        fragmentTransaction.replace(
                containerId,
                fragment,
                fragment.getClass().getSimpleName()
        ).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
