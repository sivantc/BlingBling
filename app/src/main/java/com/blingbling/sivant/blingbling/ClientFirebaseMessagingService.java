package com.blingbling.sivant.blingbling;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by sivan on 26/08/2017.
 */

public class ClientFirebaseMessagingService extends FirebaseMessagingService{

    private static final String TAG = "ClientFirebaseMessagingService";

    @Override
    public void onMessageReceived(RemoteMessage message) {

        String image = message.getNotification().getIcon();
        String title = message.getNotification().getTitle();
        String text = message.getNotification().getBody();
        String sound = message.getNotification().getSound();

        int id = 0;
        Object obj = message.getData().get("id");
        if (obj != null) {
            id = Integer.valueOf(obj.toString());
        }

        this.sendNotification(new ClientNotificationData(image, id, title, text, sound));
    }
    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param clientNotificationData GCM message received.
     */
    private void sendNotification(ClientNotificationData clientNotificationData) {

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(ClientNotificationData.TEXT, clientNotificationData.getTextMessage());

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = null;
        try {

            notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(URLDecoder.decode(clientNotificationData.getTitle(), "UTF-8"))
                    .setContentText(URLDecoder.decode(clientNotificationData.getTextMessage(), "UTF-8"))
                    .setAutoCancel(true)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setContentIntent(pendingIntent);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (notificationBuilder != null) {
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(clientNotificationData.getId(), notificationBuilder.build());
        } else {
           // Log.d(TAG, "notificationBuilder = null");
        }
    }


}
