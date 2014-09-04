package com.yasya.testapp;

import android.app.ActivityManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by klimenko on 04.09.14.
 */
public class Utils {

    public static boolean isInternetAvailable(Context context) {
        final ConnectivityManager manager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo info = manager.getActiveNetworkInfo();

        if (info == null || !info.isConnected()) {
            return false;
        }
        if (info.isRoaming()) {
            return false;
        }
        return true;
    }

    public static boolean isServicesRunning(Context context) {

        ActivityManager manager = (ActivityManager) context.getSystemService(android.content.Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            String dataService = DownloadService.class.getName();

            if (dataService.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static String getFileDate(String path) {
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles.length > 0) {
            String fileName = listOfFiles[0].getName();
            long downloadDate = Long.parseLong(fileName.substring(5, fileName.length()-4));
            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date now = new Date(downloadDate);
            return sdfDate.format(now);
        } else {
            return null;
        }
    }

    public static void deleteFile(String path) {
        File folder = new File(path);
        for (File file : folder.listFiles()) {
            file.delete();
        }
    }
}
