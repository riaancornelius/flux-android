package com.riaancornelius.flux.api;

import android.content.SharedPreferences;
import android.util.Log;
import com.riaancornelius.flux.domain.Settings;
import com.riaancornelius.flux.jira.api.service.JiraJacksonSpringAndroidSpiceService;
import com.riaancornelius.flux.ui.components.EncryptedEditText;
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
        settings = getSharedPreferences(Settings.JIRA_SHARED_PREFERENCES_KEY, 0);
    }

    @Override
    protected HttpAuthentication createAuthenticationHeader() {
        if (settings != null) {
            String username = settings.getString(Settings.JIRA_USERNAME_KEY, "");
            Log.d("SETTINGS", "Loaded username: " + username);
            String encodedPassword = settings.getString(Settings.JIRA_PASSWORD_KEY, "");
            Log.d("SETTINGS", "Loaded password: " + EncryptedEditText.rot13Decode(encodedPassword).toString());
            if (username != null && encodedPassword != null){
                return new HttpBasicAuthentication(
                        username,
                        EncryptedEditText.rot13Decode(encodedPassword).toString());
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
        //return "https://projectflux.atlassian.net/";
    }
}
