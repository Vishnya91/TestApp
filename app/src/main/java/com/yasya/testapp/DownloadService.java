package com.yasya.testapp;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by klimenko on 04.09.14.
 */
public class DownloadService extends Service {

    private DownloadTask mDownloadTask;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Utils.deleteFile(getFilesDir().getPath());
        mDownloadTask = new DownloadTask();
        mDownloadTask.execute(getFilesDir() + "/file_" + System.currentTimeMillis() + ".txt");

        return START_STICKY;
    }

    class DownloadTask extends AsyncTask<String, Integer, File> {

        @Override
        protected File doInBackground(String... paths) {
            File file = null;
            try {
                URL url = new URL("http://blog.cometdocs.com/wp-content/uploads/android.png");
                URLConnection connection = url.openConnection();
                connection.connect();

                int fileLength = connection.getContentLength();

                InputStream input = new BufferedInputStream(url.openStream());
                file = new File(paths[0]);
                OutputStream output = new FileOutputStream(file);

                byte data[] = new byte[1024];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    Thread.sleep(1000);
                    publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return file;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            NotificationHelper.update(getApplicationContext(), values[0]);
        }

        @Override
        protected void onPostExecute(File file) {
            super.onPostExecute(file);
            stopSelf();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDownloadTask.cancel(true);
    }
}
