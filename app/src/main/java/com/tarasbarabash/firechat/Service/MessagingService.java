package com.tarasbarabash.firechat.Service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.tarasbarabash.firechat.Activity.ConversationActivity;
import com.tarasbarabash.firechat.R;

import java.util.Random;

import static com.tarasbarabash.firechat.Utils.Constants.INTENT_EXTRA_KEYS.CONTACT_PHONE_NUMBER;

/**
 * Created by Taras
 * 5/11/2018, 18:15.
 */

public class MessagingService extends FirebaseMessagingService {
    public static final String CHANNEL_ID = "FireChat";
    private static final String TAG = MessagingService.class.getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody());

        String sender = remoteMessage.getData().get("sender");
        Log.i(TAG, "onMessageReceived: " + sender);
        Intent intent = new Intent(this, ConversationActivity.class);
        intent.putExtra(CONTACT_PHONE_NUMBER, sender);
        intent.setAction(sender);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                new Random().nextInt(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        builder.setLights(Color.YELLOW, 500, 500);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(alarmSound);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(1, builder.build());
    }
}
