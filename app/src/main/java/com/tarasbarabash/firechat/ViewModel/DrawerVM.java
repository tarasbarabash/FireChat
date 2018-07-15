package com.tarasbarabash.firechat.ViewModel;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.tarasbarabash.firechat.Activity.ContactsListActivity;
import com.tarasbarabash.firechat.Activity.SettingsActivity;
import com.tarasbarabash.firechat.Model.Profile;
import com.tarasbarabash.firechat.R;
import com.tarasbarabash.firechat.Utils.FirebaseUtils;
import com.tarasbarabash.firechat.databinding.NavViewHeaderBinding;

/**
 * Created by Taras
 * 28.04.2018, 14:16.
 */

public class DrawerVM extends BaseVM<NavViewHeaderBinding> implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = DrawerVM.class.getSimpleName();
    private DrawerLayout mDrawerLayout;

    public DrawerVM(NavViewHeaderBinding binding, Context context, Application application, Activity activity, DrawerLayout drawerLayout) {
        super(binding, context, application, activity);
        mDrawerLayout = drawerLayout;
        setUpDrawer();
    }

    private void setUpDrawer() {
        FirebaseUser user = FirebaseUtils.getUser();
        String phoneNumber = user.getPhoneNumber();
        Log.i(TAG, "setUpDrawer: " + phoneNumber);
        FirebaseUtils.getUsersRef().child(phoneNumber)
                .child(FirebaseUtils.USERS_DB.FIELDS.META)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Profile userProfile = dataSnapshot.getValue(Profile.class);
                        userProfile.setPhoneNumber(phoneNumber);
                        getBinding().setProfile(userProfile);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Class activity;
        switch (item.getItemId()) {
            case R.id.contacts:
                activity = ContactsListActivity.class;
                break;
            case R.id.settings:
                activity = SettingsActivity.class;
                break;
            default:
                activity = null;
                break;
        }
        if (activity == null) return false;
        getActivity().startActivity(new Intent(getContext(), activity));
        mDrawerLayout.closeDrawers();
        return true;
    }
}
