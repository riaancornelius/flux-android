package com.riaancornelius.flux;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.riaancornelius.flux.domain.Settings;
import com.riaancornelius.flux.ui.MainActivity;
import com.riaancornelius.flux.ui.settings.SettingsActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by riaan.cornelius on 2013/12/10.
 */
@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {

    @Test
    public void clickingButton_shouldChangeResultsViewText() throws Exception {
        Activity activity = Robolectric.buildActivity(MainActivity.class).create().get();

        //    Button scanTicket = (Button) activity.findViewById(R.id.scan_ticket);
        //   String scanText = scanTicket.getText().toString();
        //   assertThat(scanText).isEqualTo("Scan a ticket");
    }

    @Test
    public void noUserDetailsLaunchesSettings() throws Exception {
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).create().get();
        assertThat(activity.isUserDetailsSet()).isFalse();

        Intent intent = Robolectric.shadowOf(activity).peekNextStartedActivity();
        assertThat(intent.getComponent()).isEqualTo(new ComponentName(activity, SettingsActivity.class));
    }

    @Test
    public void withUserDetailsDoesntLaunchSettings() throws Exception {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(
                Robolectric.application);
        sharedPreferences.edit().putString(Settings.JIRA_USERNAME_KEY, "testUser").commit();
        sharedPreferences.edit().putString(Settings.JIRA_PASSWORD_KEY, "testPass").commit();

        MainActivity activity = Robolectric.buildActivity(MainActivity.class).create().get();
        assertThat(activity.isUserDetailsSet()).isTrue();

        Intent intent = Robolectric.shadowOf(activity).peekNextStartedActivity();
        assertThat(intent).isNull();
    }
}
