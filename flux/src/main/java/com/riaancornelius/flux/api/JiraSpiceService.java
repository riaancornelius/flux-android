package com.riaancornelius.flux.api;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;

import com.octo.android.robospice.persistence.CacheManager;
import com.octo.android.robospice.persistence.exception.CacheCreationException;
import com.octo.android.robospice.persistence.springandroid.json.jackson.JacksonObjectPersisterFactory;
import com.riaancornelius.flux.domain.Settings;
import com.riaancornelius.flux.jira.api.service.JiraJacksonSpringAndroidSpiceService;

import org.codehaus.jackson.map.DeserializationConfig;
import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.List;

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
    public RestTemplate createRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        // web services support json responses
        MappingJacksonHttpMessageConverter jsonConverter = new MappingJacksonHttpMessageConverter();
        jsonConverter.getObjectMapper().configure(
                DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        FormHttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
        final List<HttpMessageConverter< ? >> listHttpMessageConverters = restTemplate.getMessageConverters();

        listHttpMessageConverters.add( jsonConverter );
        listHttpMessageConverters.add( formHttpMessageConverter );
        listHttpMessageConverters.add( stringHttpMessageConverter );
        restTemplate.setMessageConverters( listHttpMessageConverters );
        return restTemplate;
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

    @Override
    public CacheManager createCacheManager(Application application) throws CacheCreationException {
        CacheManager cacheManager = new CacheManager();
        JacksonObjectPersisterFactory persister = new JacksonObjectPersisterFactory(application);
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        File folder = new File(externalStorageDirectory, "flux_cache");
        persister.setCacheFolder(folder);
        cacheManager.addPersister(persister);

        return cacheManager;
    }
}
