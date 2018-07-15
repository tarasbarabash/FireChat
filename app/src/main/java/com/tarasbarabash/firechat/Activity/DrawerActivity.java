package com.tarasbarabash.firechat.Activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.tarasbarabash.firechat.R;
import com.tarasbarabash.firechat.ViewModel.DrawerVM;
import com.tarasbarabash.firechat.databinding.ActivityDrawerBinding;
import com.tarasbarabash.firechat.databinding.NavViewHeaderBinding;

/**
 * Created by Taras
 * 28.04.2018, 13:15.
 */

public abstract class DrawerActivity extends BaseFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDrawerBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_drawer);
        DrawerLayout drawerLayout = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        NavViewHeaderBinding headerBinding = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.nav_view_header, navigationView, false);
        DrawerVM viewModel = new DrawerVM(headerBinding, getBaseContext(), getApplication(), this, drawerLayout);
        navigationView.addHeaderView(headerBinding.getRoot());
        headerBinding.setViewmodel(viewModel);
        Toolbar toolbar = binding.activityBase.toolbar;
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(viewModel);
    }
}
