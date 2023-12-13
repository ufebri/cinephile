package com.lagingoding.cinephile.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.lagingoding.cinephile.R;
import com.lagingoding.cinephile.ui.MainActivity;

import java.util.Objects;

/**
 * Implementation of App Widget functionality.
 */
public class FavoriteWidget extends AppWidgetProvider {

    public static final String EXTRA_ITEM = "EXTRA_ITEM_FAVORITE";
    public static final String TOAST_ACTION_FAVORITE = "TOAST_ACTION_FAVORITE";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent intent = new Intent(context, FavoriteWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        //RemoteView
        RemoteViews remoteViews = new RemoteViews(
                context.getPackageName(), R.layout.favorite_widget);
        remoteViews.setRemoteAdapter(R.id.sv_favorite_widget, intent);
        remoteViews.setEmptyView(R.id.sv_favorite_widget, R.id.tv_favorite_widget_empty_data);

        //Action Toast
        Intent intentToast = new Intent(context, FavoriteWidget.class);
        intentToast.setAction(FavoriteWidget.TOAST_ACTION_FAVORITE);
        intentToast.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

        //set Pending intent
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                intentToast,
                PendingIntent.FLAG_UPDATE_CURRENT);

        //set RemoteView PendingIntent
        remoteViews.setPendingIntentTemplate(
                R.id.sv_favorite_widget,
                pendingIntent);

        appWidgetManager.notifyAppWidgetViewDataChanged(
                appWidgetId,
                R.id.sv_favorite_widget);

        appWidgetManager.updateAppWidget(
                appWidgetId,
                remoteViews);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        if (Objects.requireNonNull(intent.getAction()).equals(TOAST_ACTION_FAVORITE)) {

            Intent intentToMain = new Intent(context, MainActivity.class);
            context.startActivity(intentToMain);

        } else if (intent.getAction().equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            ComponentName componentName = new ComponentName(
                    context.getPackageName(),
                    FavoriteWidget.class.getName());

            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(componentName);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.sv_favorite_widget);
        }
        super.onReceive(context, intent);
    }
}

