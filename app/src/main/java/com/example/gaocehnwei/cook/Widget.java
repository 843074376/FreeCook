package com.example.gaocehnwei.cook;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class Widget extends AppWidgetProvider {

    public void onReceive(Context context,Intent intent){
        super.onReceive(context,intent);Log.d("err","1");
        RemoteViews rv = new RemoteViews(context.getPackageName(),R.layout.widget);
        Bundle bundle = intent.getExtras();
        if(intent.getAction().equals("widget")){
            String fetch_2 = bundle.getString("name");

            rv.setTextViewText(R.id.book_name,fetch_2);
        }
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName componentName = new ComponentName(context,Widget.class);
        appWidgetManager.updateAppWidget(componentName,rv);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        super.onUpdate(context,appWidgetManager,appWidgetIds);
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget);
        Intent intentClick = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                intentClick, 0);
        rv.setOnClickPendingIntent(R.id.image_2002, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetIds,rv);
    }
}

