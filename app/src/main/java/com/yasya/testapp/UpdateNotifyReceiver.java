package com.yasya.testapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by klimenko on 04.09.14.
 */
public class UpdateNotifyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper.showUpdateNotification(context);
    }
}
