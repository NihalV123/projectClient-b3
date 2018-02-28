package a123.vaidya.nihal.foodcrunchclient.Helper;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.net.Uri;
import android.os.Build;

import a123.vaidya.nihal.foodcrunchclient.R;

public class NotificationHelper extends ContextWrapper {
    private static final String FOODCRUNCH_CHANNEL_ID = "a123.vaidya.nihal.foodcrunchclient.FOOD-CRUNCH";
    private static final String FOODCRUNCH_CHANNEL_NAME = "Food Cunch";
    private NotificationManager manager;
    public NotificationHelper(Context base) {
        super(base);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            //only work for oreo 8.0 and above
            createChannel();

    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel foodcrunchChannel = new NotificationChannel(FOODCRUNCH_CHANNEL_ID,FOODCRUNCH_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT);
        foodcrunchChannel.enableLights(true);//change to false if problems
        foodcrunchChannel.enableVibration(true);
        foodcrunchChannel.setLockscreenVisibility(android.app.Notification.VISIBILITY_PRIVATE);
        getManager().createNotificationChannel(foodcrunchChannel);
    }

    public NotificationManager getManager() {
        if(manager == null)
            manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        return manager;
    }

    @TargetApi(Build.VERSION_CODES.O)
    public android.app.Notification.Builder foodcrunchChannelNotification(String title, String body,
                                                                          PendingIntent contextIntent, Uri sounddUri)
    {
        return new android.app.Notification.Builder(getApplicationContext(),FOODCRUNCH_CHANNEL_ID)
                .setContentIntent(contextIntent)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setSound(sounddUri)
                .setAutoCancel(false);
    }
}
