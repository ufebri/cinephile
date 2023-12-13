package com.lagingoding.cinephile.ui.settings;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.lagingoding.cinephile.R;
import com.lagingoding.cinephile.model.DataMovies;
import com.lagingoding.cinephile.model.remote.Movie;
import com.lagingoding.cinephile.util.alarm.AlarmBroadcast;
import com.lagingoding.cinephile.util.base.BaseViewMovie;

import java.util.ArrayList;
import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener {

        private AlarmBroadcast alarmBroadcast = new AlarmBroadcast();
        private String reminderDailyKey, reminderUpcomingKey, intentToLanguage;

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            reminderDailyKey = getString(R.string.reminder_daily_key);
            reminderUpcomingKey = getString(R.string.remider_upcoming_key);
            intentToLanguage = getString(R.string.intent_to_language);

            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);

            findPreference(reminderDailyKey).setOnPreferenceChangeListener(this);
            findPreference(reminderUpcomingKey).setOnPreferenceChangeListener(this);
            findPreference(intentToLanguage).setIntent(intent);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String preferenceKey = preference.getKey();

            if (Objects.equals(preferenceKey, reminderDailyKey)) {
                if ((boolean) newValue) {
                    alarmBroadcast.setDailyAlarms(getActivity(), AlarmBroadcast.TYPES_DAILY,
                            "07:00:00", getString(R.string.daily_movie_message));
                } else {
                    alarmBroadcast.cancelAlarms(getActivity(), AlarmBroadcast.TYPES_DAILY);
                }

                return true;
            }

            if (Objects.equals(preferenceKey, reminderUpcomingKey)) {
                if ((boolean) newValue) {
                    alarmBroadcast.setUpComingAlarm(getActivity(), AlarmBroadcast.TYPES_UPCOMING);
                } else {
                    alarmBroadcast.cancelAlarms(getActivity(), AlarmBroadcast.TYPES_UPCOMING);
                }
                return true;
            }

            return false;
        }
    }
}