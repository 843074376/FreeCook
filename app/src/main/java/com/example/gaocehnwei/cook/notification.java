package com.example.gaocehnwei.cook;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

public class notification extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent)
    {
        Log.d("err","noti1");
        if(intent.getAction().equals("notification")){
            Log.d("err","noti2");
            Bundle bundle = intent.getExtras();
            String fetch_2 = bundle.getString("name");
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification.Builder builder = new Notification.Builder(context);
            builder .setContentText(fetch_2)
                    .setPriority(Notification.PRIORITY_DEFAULT)
                    .setContentTitle("点我跳回菜谱")
                    .setTicker("你有一条新消息")
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(true)
                    .setSmallIcon(R.mipmap.eat2);
            Intent mIntent = new Intent(context,MainActivity.class);
            PendingIntent mPendingIntent = PendingIntent.getActivity(context,0,mIntent,0);
            builder.setContentIntent(mPendingIntent);
            Notification notify = builder.build();
            manager.notify(0,notify);
        }
    }
}
