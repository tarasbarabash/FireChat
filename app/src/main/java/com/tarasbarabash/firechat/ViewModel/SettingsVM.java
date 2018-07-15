package com.tarasbarabash.firechat.ViewModel;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.design.widget.BottomSheetDialog;
import android.util.Log;
import android.view.LayoutInflater;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.stfalcon.frescoimageviewer.ImageViewer;
import com.tarasbarabash.firechat.Activity.ConversationActivity;
import com.tarasbarabash.firechat.Activity.SettingsActivity;
import com.tarasbarabash.firechat.Model.Profile;
import com.tarasbarabash.firechat.R;
import com.tarasbarabash.firechat.Utils.Constants;
import com.tarasbarabash.firechat.Utils.FirebaseUtils;
import com.tarasbarabash.firechat.Utils.IntentUtils;
import com.tarasbarabash.firechat.databinding.ActivitySettingsBinding;
import com.tarasbarabash.firechat.databinding.FragmentSettingsPhotoBottomSheetBinding;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;

import id.zelory.compressor.Compressor;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Taras
 * 5/6/2018, 16:30.
 */

public class SettingsVM extends UploadVM<ActivitySettingsBinding> {
    private static final String TAG = SettingsVM.class.getSimpleName();
    private boolean hasImage = false;
    private BottomSheetDialog mBottomSheet;
    private final String mPhoneNumber;
    private Profile mProfile;

    public SettingsVM(ActivitySettingsBinding binding, Context context,
                      Application application, Activity activity, String phoneNumber) {
        super(binding, context, application, activity);
        mPhoneNumber = phoneNumber;
        updateProfileUI();
    }

    private void updateProfileUI() {
        FirebaseUtils.getUserMetaRef(mPhoneNumber)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mProfile = dataSnapshot.getValue(Profile.class);
                        mProfile.setPhoneNumber(mPhoneNumber);
                        hasImage = mProfile.getPhotoUrl() != null;
                        getBinding().setProfile(mProfile);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    public void onBackClick() {
        getActivity().finish();
    }

    public void onImageClick() {
        if (!FirebaseUtils.isCurrentUser(mPhoneNumber)) {
            onOpen();
            return;
        }
        mBottomSheet = new BottomSheetDialog(getContext());
        FragmentSettingsPhotoBottomSheetBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(getContext()),
                R.layout.fragment_settings_photo_bottom_sheet,
                null,
                false
        );
        binding.setViewModel(this);
        mBottomSheet.setContentView(binding.getRoot());
        mBottomSheet.show();
    }

    public void uploadImage(Uri uri) {
        FirebaseUtils.getCurrentUserPicStorageRef().putFile(uri).addOnCompleteListener(task -> {
            if (!task.isSuccessful()) Log.e(TAG, "onComplete: ", task.getException());
            else {
                Uri imageUri = task.getResult().getDownloadUrl();
                Log.i(TAG, "uploadImage: " + imageUri);
                FirebaseUtils.getUserMetaRef(FirebaseUtils.getCurrentUserPhoneNumber())
                        .child(FirebaseUtils.USERS_DB.FIELDS.PHOTO_URL)
                        .setValue(imageUri.toString());
            }
        });
        mBottomSheet.hide();
    }

    public void onDelete() {
        FirebaseUtils.getCurrentUserPicStorageRef().delete().addOnCompleteListener(task -> {
            if (task.isSuccessful())
                FirebaseUtils.getCurrentUserMetaRef()
                        .child(FirebaseUtils.USERS_DB.FIELDS.PHOTO_URL)
                        .removeValue();
            else Log.e(TAG, "onDelete: ", task.getException());
        });
        mBottomSheet.hide();
    }

    public void onOpen() {
        if (mBottomSheet != null) mBottomSheet.hide();
        String[] uris = new String[]{mProfile.getPhotoUrl()};
        new ImageViewer.Builder<>(getContext(), uris)
                .setStartPosition(0)
                .show();
    }

    public void onStartChat() {
        Intent intent = new Intent(getContext(), ConversationActivity.class);
        intent.putExtra(Constants.INTENT_EXTRA_KEYS.CONTACT_PHONE_NUMBER, mPhoneNumber);
        getContext().startActivity(intent);
    }

    public boolean isHasImage() {
        return hasImage;
    }

    public boolean isCurrentUser() {
        return FirebaseUtils.isCurrentUser(mPhoneNumber);
    }
}
