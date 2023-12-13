package com.lagingoding.cinephile.util.alarm;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.lagingoding.cinephile.BuildConfig;
import com.lagingoding.cinephile.R;
import com.lagingoding.cinephile.model.DataMovies;
import com.lagingoding.cinephile.model.remote.Movie;
import com.lagingoding.cinephile.ui.MainActivity;
import com.lagingoding.cinephile.util.network.NetworkClient;
import com.lagingoding.cinephile.util.network.NetworkService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlarmBroadcast extends BroadcastReceiver {

    public static final String EXTRA_TYPES = "type";
    public static final String EXTRA_MESSAGES = "message";
    public static final String TYPES_DAILY = "DailyAlarms";
    public static final String TYPES_UPCOMING = "UpcomingAlarms";

    private final int NOTIF_CODE_DAILY = 1;
    private final int NOTIF_CODE_UPCOMING = 2;

    public static ArrayList<Movie> list = new ArrayList<>();

    public AlarmBroadcast() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String type = intent.getStringExtra(EXTRA_TYPES);
        String message = intent.getStringExtra(EXTRA_MESSAGES);

        int notifIds = type.equalsIgnoreCase(TYPES_DAILY) ? NOTIF_CODE_DAILY : NOTIF_CODE_UPCOMING;
        String title = context.getResources().getString(R.string.app_name);

        if (notifIds == NOTIF_CODE_DAILY) {
            showAlarmNotifications(context, title, message, notifIds);
        } else {
            if (list != null) {
                showAlarmNotifications(context, title, message, notifIds);
            } else {
                fetchData(context);
            }
        }
    }

    private void fetchData(Context context) {
        String actualDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        NetworkService service = NetworkClient.createService(NetworkService.class);
        Call<DataMovies> call = service.getUpcoming(BuildConfig.API_KEY, "en-EN", actualDate, actualDate);
        call.enqueue(new Callback<DataMovies>() {
            @Override
            public void onResponse(Call<DataMovies> call, Response<DataMovies> response) {
                list.addAll(response.body().getMovies());
                setUpComingAlarm(context, TYPES_UPCOMING);
            }

            @Override
            public void onFailure(Call<DataMovies> call, Throwable t) {

            }
        });
    }


    private void showAlarmNotifications(Context context, String title, String message, int notifIds) {
        String CHANNEL_ID = "Channel_1";
        String CHANNEL_NAME = "AlarmManager channel";

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, notifIds, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);

            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000});
            builder.setChannelId(CHANNEL_ID);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();
        if (notificationManager != null) {
            notificationManager.notify(notifIds, notification);
        }
    }

    public void setDailyAlarms(Context context, String type, String time, String messages) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmBroadcast.class);
        intent.putExtra(EXTRA_MESSAGES, messages);
        intent.putExtra(EXTRA_TYPES, type);

        String timeArray[] = time.split(":");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIF_CODE_DAILY, intent, 0);

        assert alarmManager != null;
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    public void setUpComingAlarm(final Context context, final String type) {
        if (list.size() != 0) {
            int index = new Random().nextInt(list.size());
            String originalTitle = list.get(index).getOriginalTitle();
            String message = originalTitle + " Now Release";

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, AlarmBroadcast.class);
            intent.putExtra(EXTRA_MESSAGES, message);
            intent.putExtra(EXTRA_TYPES, type);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 21);
            calendar.set(Calendar.MINUTE, 25);
            calendar.set(Calendar.SECOND, 0);

            PendingIntent pendingIntent =
                    PendingIntent.getBroadcast(
                            context,
                            NOTIF_CODE_UPCOMING,
                            intent,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );

            assert alarmManager != null;
            alarmManager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent);
        } else {
            fetchData(context);
        }
    }

    public void cancelAlarms(Context context, String type) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmBroadcast.class);

        int requestCode = type.equalsIgnoreCase(TYPES_DAILY) ? NOTIF_CODE_DAILY : NOTIF_CODE_UPCOMING;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);

        assert alarmManager != null;
        alarmManager.cancel(pendingIntent);
    }


}
