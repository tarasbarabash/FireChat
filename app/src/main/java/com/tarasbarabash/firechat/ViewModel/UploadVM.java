package com.tarasbarabash.firechat.ViewModel;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.tarasbarabash.firechat.Activity.ResultActivity;
import com.tarasbarabash.firechat.Fragment.BaseFragment;
import com.tarasbarabash.firechat.R;
import com.tarasbarabash.firechat.Utils.IntentUtils;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;

import id.zelory.compressor.Compressor;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Taras
 * 5/9/2018, 14:17.
 */

public abstract class UploadVM<B extends ViewDataBinding> extends BaseVM<B>
        implements ResultActivity.OnActivityResult {
    private static final String TAG = UploadVM.class.getSimpleName();
    private static final int PHOTO_RC = 74;
    private static final int GALLERY_RC = 43;
    private Uri mPhotoUri;

    public UploadVM(B binding, BaseFragment fragment) {
        super(binding, fragment);
    }

    public UploadVM(B binding, Context context, Application application, Activity activity) {
        super(binding, context, application, activity);
    }

    @Override
    public void onResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == RESULT_OK) {
            if (requestCode == PHOTO_RC) {
                if (this instanceof ConversationFragmentVM) {
                    Log.i(TAG, "onResult: " + mPhotoUri);
                    uploadImage(mPhotoUri);
                    return;
                }
                CropImage.activity(mPhotoUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(getActivity());
            }
            if (requestCode == GALLERY_RC) {
                mPhotoUri = intent.getData();
                if (this instanceof ConversationFragmentVM) {
                    Log.i(TAG, "onResult: " + mPhotoUri);
                    uploadImage(mPhotoUri);
                    return;
                }
                Uri uri = intent.getData();
                CropImage.activity(uri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(getActivity());
            }
            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(intent);
                Uri uri = result.getUri();
                uri = compressImage(uri, 500, 500, 75);
                uploadImage(uri);
            }
        }
    }

    protected abstract void uploadImage(Uri uri);

    public void onCamera() {
        mPhotoUri = IntentUtils.getPhotoUri(getActivity().getApplicationContext());
        Intent intent = IntentUtils.takePicture(mPhotoUri);
        getActivity().startActivityForResult(intent, PHOTO_RC);
    }

    public void onGallery() {
        if (mPhotoUri == null)
            IntentUtils.getPhotoUri(getActivity().getApplicationContext());
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        getActivity().startActivityForResult(Intent.createChooser(intent,
                getContext().getString(R.string.select_gallery_pic)), GALLERY_RC);
    }

    public Uri compressImage(Uri imageUrl, int maxWidth, int maxHeight, int quality) {
        try {
            imageUrl = Uri.parse(new Compressor(getContext())
                    .setMaxWidth(maxWidth)
                    .setMaxHeight(maxHeight)
                    .setQuality(quality)
                    .setCompressFormat(Bitmap.CompressFormat.WEBP)
                    .compressToFile(new File(imageUrl.getPath())).toURI().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageUrl;
    }
}
