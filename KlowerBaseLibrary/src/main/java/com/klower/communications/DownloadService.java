package com.klower.communications;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;


import com.klower.R;

import java.io.File;
import java.text.SimpleDateFormat;

public class DownloadService extends IntentService {

    private NotificationManager manager;

    Notification notification;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    String filePath;

    String urlToDownload;

    String fileName;

    String fileType;

    String path;

    DownLoadClient client;

    int appicon;

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        fileName = intent.getStringExtra("fileName");
        fileType = intent.getStringExtra("fileType");
        appicon = intent.getIntExtra("appicon", 0);
        path = Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/";
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        urlToDownload = intent.getStringExtra("url");
        Log.d("progress", "url: " + urlToDownload);
        client = new DownLoadClient(this, handler);
        showNotification(0);
        client.startDownLoad(urlToDownload, path, fileName + fileType);
    }

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case DownLoadClient.DOWNLOAD_DOING:
                    float percent = (float) client.getDownLoadSize()
                            / client.getFileSize();
                    int progress = (int) (percent * 100);
//				Log.d("progress", "Progress: " + progress);
                    if (progress > 0 && progress % 10 == 0) {
                        showNotification(progress);
                    }
                    break;
                case DownLoadClient.DOWNLOAD_NO_NETWORK:
                case DownLoadClient.DOWNLOAD_ERROR:
                    manager.cancel(110);
                    Toast.makeText(DownloadService.this, "下载失败", Toast.LENGTH_LONG)
                            .show();
                    break;
            }

        }

        ;
    };


    /**
     * @param progress
     * @return void
     * @Description: Show Notification By susutem
     */
    @SuppressWarnings("deprecation")
    private void showNotification(int progress) {
        if(appicon == 0){
            appicon = R.drawable.download;
        }
        Intent intent;
        if (progress == 100) {
            Uri uri = Uri.fromFile(new File(path, fileName + ".apk"));
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri,
                    "application/vnd.android.package-archive");
        } else {
            intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        PendingIntent pd = PendingIntent.getActivity(DownloadService.this, 0,
                intent, 0);
        String progressTxt;
        if (progress < 100) {
            progressTxt = "下载进度: " + progress + "%";
        } else {
            progressTxt = "下载完成,点击安装!";
        }
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(progressTxt)
                .setSmallIcon(appicon)
                .setLargeIcon(
                        BitmapFactory.decodeResource(getResources(),
                                appicon))
                .setContentIntent(pd).setAutoCancel(true).setDefaults(Notification.DEFAULT_SOUND)
                .getNotification();
        if (progress == 100) {
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
        } else {
            notification.flags |= Notification.FLAG_NO_CLEAR;
        }

        manager.notify(110, notification);

    }


}
