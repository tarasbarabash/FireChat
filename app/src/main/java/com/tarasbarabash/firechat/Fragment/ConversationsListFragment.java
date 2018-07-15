package com.tarasbarabash.firechat.Fragment;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.tarasbarabash.firechat.R;
import com.tarasbarabash.firechat.ViewModel.BaseVM;
import com.tarasbarabash.firechat.ViewModel.ConversationsListVM;
import com.tarasbarabash.firechat.databinding.FragmentConversationsListBinding;

public class ConversationsListFragment extends PermissionsFragment<FragmentConversationsListBinding> {
    private ConversationsListVM mViewModel;

    @Override
    public int getLayout() {
        return R.layout.fragment_conversations_list;
    }

    @Override
    protected BaseVM<FragmentConversationsListBinding> setViewModel(FragmentConversationsListBinding binding) {
        mViewModel = new ConversationsListVM(binding, this);
        binding.setViewModel(mViewModel);
        setListener(mViewModel);
        needPermission(Manifest.permission.READ_CONTACTS, 103);
        return mViewModel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                mViewModel.logout();
                return true;
            default:
                super.onOptionsItemSelected(item);
                return false;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.dialogs_list_menu, menu);
    }
}
