package com.tarasbarabash.firechat.Utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.tarasbarabash.firechat.BuildConfig;
import com.tarasbarabash.firechat.Provider.ChatFileProvider;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Taras
 * 5/6/2018, 19:03.
 */

public class IntentUtils {
    public static Intent takePicture(Uri uri) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        return intent;
    }

    public static Uri getPhotoUri(Context context) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
        String name = format.format(new Date()) + ".jpg";
        File photo = new File(Environment.getExternalStorageDirectory(),  name);
        return ChatFileProvider.getUriForFile(context,
                BuildConfig.APPLICATION_ID + ".Provider.ChatFileProvider",
                photo);
    }
}
