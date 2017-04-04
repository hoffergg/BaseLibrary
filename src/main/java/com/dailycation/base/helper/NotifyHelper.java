package com.dailycation.base.helper;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;

import com.dailycation.base.BaseApplication;
import com.dailycation.base.R;


/**
 * @author hehu
 * @version 1.0 2016/6/24
 */
public class NotifyHelper {
    private static final String LOG_TAG = NotifyHelper.class.getSimpleName();
    private static Vibrator vibrator;

    public static void showNotification(Context context, String title, String desc, int icon,PendingIntent pendingIntent, int notificationId){
        LogHelper.d(LOG_TAG,"show status bar notification title:" + title + " desc:" + desc + " id:" + notificationId + " pendingIntent:" + pendingIntent.toString());
        NotificationCompat.Builder mBuilder = null;
        try {
            mBuilder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(icon)
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

        vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(1000);           //重复两次上面的pattern 如果只想震动一次，index设为-1

        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(context.getApplicationContext(), notification);
        r.play();
    }

    public static void alarm(){
        BaseApplication application = BaseApplication.getInstance();
        vibrator = (Vibrator)application.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(1000);           //重复两次上面的pattern 如果只想震动一次，index设为-1

        MediaPlayer m = new MediaPlayer();
        try {
            AssetFileDescriptor descriptor = application.getAssets().openFd("alarm.mp3");
            m.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            descriptor.close();
            m.prepare();
            m.setVolume(1f, 1f);
            m.setLooping(false);
            m.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
