package com.yasya.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class HomeActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        NotificationHelper.cancelNotification(HomeActivity.this, NotificationHelper.UPDATE_NOTIFICATION_ID);

        Button downloadButton = (Button) findViewById(R.id.btn_download);
        TextView lastDownloadDate = (TextView) findViewById(R.id.txt_download_date);

        String fileDate = Utils.getFileDate(getFilesDir().getPath());
        if (!TextUtils.isEmpty(fileDate)) {
            lastDownloadDate.setVisibility(View.VISIBLE);
            lastDownloadDate.setText(getString(R.string.text_file_was_downloaded) + " " + fileDate);
            downloadButton.setText(getString(R.string.text_update));
        }

        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isInternetAvailable(HomeActivity.this)) {
                    Intent downloadIntent = new Intent(HomeActivity.this, DownloadService.class);
                    if (Utils.isServicesRunning(HomeActivity.this)) {
                        stopService(downloadIntent);
                    }
                    startService(downloadIntent);
                    finish();
                } else {
                    Toast.makeText(HomeActivity.this, getString(R.string.info_no_internet), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
