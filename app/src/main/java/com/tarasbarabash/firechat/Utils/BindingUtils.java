package com.tarasbarabash.firechat.Utils;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.tarasbarabash.firechat.Model.Profile;
import com.tarasbarabash.firechat.R;

/**
 * Created by Taras
 * 5/7/2018, 18:41.
 */

public final class BindingUtils {
    private static final String TAG = BindingUtils.class.getSimpleName();

    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(SimpleDraweeView view, String uri) {
        Uri photoUri;
        if (uri != null)
            photoUri = Uri.parse(uri);
        else photoUri = Uri.parse("");
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(photoUri)
                .setResizeOptions(new ResizeOptions(200, 200))
                .build();
        view.setController(
                Fresco.newDraweeControllerBuilder().setOldController(view.getController())
                        .setImageRequest(imageRequest)
                        .build()
        );
    }

    @BindingAdapter({"bind:name"})
    public static void name(TextView textView, Profile profile) {
        if (profile == null) return;
        if (profile.getPhoneNumber().equals(FirebaseUtils.getCurrentUserPhoneNumber()))
            textView.setText(textView.getContext().getString(R.string.yourself_chat));
        else textView.setText(profile.getName());
    }

    @BindingAdapter({"bind:online"})
    public static void online(TextView textView, Profile profile) {
        if (profile == null) return;
        Context context = textView.getContext();
        if (profile.isOnline()) {
            textView.setText(R.string.online);
        } else {
            String s = textView.getContext().getString(R.string.last_seen,
                    TimeUtils.getLastSeenTime(profile.getLastSeen(), context));
            Log.i(TAG, "online: " + s);
            textView.setText(s);
        }
    }
}
