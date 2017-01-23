package com.dailycation.base.helper;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;



/**
 * @author hehu
 * @version 1.0 2016/6/24
 */
public class NotifyHelper {
    private static final String LOG_TAG = NotifyHelper.class.getSimpleName();
    public static void showNotification(Context context, String title, String desc, PendingIntent pendingIntent, int notificationId){
        LogHelper.d(LOG_TAG,"show status bar notification title:" + title + " desc:" + desc + " id:" + notificationId + " pendingIntent:" + pendingIntent.toString());
        NotificationCompat.Builder mBuilder = null;
        try {
            mBuilder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.mipmap.logo)
                            .setContentTitle(title)
                            .setContentText(desc)
                            .setAutoCancel(true);
        }catch(Exception e){
            e.printStackTrace();
            LogHelper.e(LOG_TAG,e.toString());
        }
        mBuilder.setContentIntent(pendingIntent);

        // Sets an ID for the notification
        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        // Builds the notification and issues it.
        mNotifyMgr.notify(notificationId, mBuilder.build());

        Vibrator vibrator;
        vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(1000);           //重复两次上面的pattern 如果只想震动一次，index设为-1

        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(context.getApplicationContext(), notification);
        r.play();
    }
}
