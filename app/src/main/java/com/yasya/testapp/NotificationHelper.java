package com.yasya.testapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

/**
 * Created by klimenko on 04.09.14.
 */
public class NotificationHelper {

    public static final int DOWNLOAD_NOTIFICATION_ID = 1;
    public static final int UPDATE_NOTIFICATION_ID = 1;

    public static void update(Context context, int currProgress) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(context.getString(R.string.text_file_download))
                .setContentText(context.getString(R.string.text_download_in_progress))
                .setSmallIcon(R.drawable.ic_launcher);
        if (currProgress == 100) {
            builder.setContentText(context.getString(R.string.text_download_complete))
                    .setProgress(0, 0, false);
        } else {
            builder.setProgress(100, currProgress, false);
        }

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(DOWNLOAD_NOTIFICATION_ID, builder.build());
    }

    public static void showUpdateNotification(Context context) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context).setSmallIcon(R.drawable.ic_launcher);

        RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.notification_update);
        contentView.setImageViewResource(R.id.notif_image, R.drawable.ic_launcher);
        contentView.setTextViewText(R.id.notif_title, context.getString(R.string.text_update_available));
        contentView.setTextViewText(R.id.notif_text, context.getString(R.string.text_can_update));

        PendingIntent updatePendingIntent = PendingIntent.getService(context, 0, new Intent(context, DownloadService.class), PendingIntent.FLAG_ONE_SHOT);
        contentView.setOnClickPendingIntent(R.id.btn_update, updatePendingIntent);

        PendingIntent openActivityPendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, HomeActivity.class), PendingIntent.FLAG_ONE_SHOT);
        contentView.setOnClickPendingIntent(R.id.lyt_notif_main, openActivityPendingIntent);

        builder.setContent(contentView);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(UPDATE_NOTIFICATION_ID, builder.build());
    }

    public static void cancelNotification(Context context, int id) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(id);
    }
}
