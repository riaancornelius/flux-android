package com.riaancornelius.flux.api;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import com.riaancornelius.flux.domain.Settings;
import com.riaancornelius.flux.jira.api.service.JiraJacksonSpringAndroidSpiceService;
import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;

/**
 * User: riaan.cornelius
 */
public class JiraSpiceService extends JiraJacksonSpringAndroidSpiceService {

    protected SharedPreferences settings;

    @Override
    public void onCreate() {
        super.onCreate();
        settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }

    @Override
    protected HttpAuthentication createAuthenticationHeader() {
        if (settings != null) {
            String username = settings.getString(Settings.JIRA_USERNAME_KEY, "");
            Log.d("SETTINGS", "Loaded username: " + username);
            String password = settings.getString(Settings.JIRA_PASSWORD_KEY, "");
            Log.d("SETTINGS", "Loaded password: " + password);
            if (username != null && password != null){
                return new HttpBasicAuthentication(
                        username,
                        password);
            }
        }
        return null;
    }

    @Override
    protected String getBaseUrl() {
        if (settings != null) {
            Log.d("SETTINGS", "Loaded baseUrl: " + settings.getString(Settings.JIRA_BASE_URL_KEY, ""));
            return settings.getString(Settings.JIRA_BASE_URL_KEY, "");
        }
        Log.d("SETTINGS", "Settings was null");
        return null;
    }
}
