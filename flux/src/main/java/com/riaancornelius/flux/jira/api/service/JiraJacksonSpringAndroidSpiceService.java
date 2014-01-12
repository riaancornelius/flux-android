package com.riaancornelius.flux.jira.api.service;

import android.app.Application;
import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpringAndroidSpiceService;
import com.octo.android.robospice.persistence.CacheManager;
import com.octo.android.robospice.persistence.exception.CacheCreationException;
import com.octo.android.robospice.persistence.springandroid.json.jackson.JacksonObjectPersisterFactory;
import com.octo.android.robospice.request.CachedSpiceRequest;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import com.riaancornelius.flux.jira.api.request.JiraSpringAndroidSpiceRequest;
import org.springframework.http.HttpAuthentication;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Set;

/**
 * User: riaan.cornelius
 */
public abstract class JiraJacksonSpringAndroidSpiceService extends SpringAndroidSpiceService {

    private RestTemplate restTemplate;

    protected abstract HttpAuthentication createAuthenticationHeader();

    protected abstract String getBaseUrl();

    @Override
    public void onCreate() {
        super.onCreate();
        restTemplate = createRestTemplate();
    }

    @Override
    public void addRequest(CachedSpiceRequest<?> request, Set<RequestListener<?>> listRequestListener) {
        if (request.getSpiceRequest() instanceof SpringAndroidSpiceRequest) {
            ((SpringAndroidSpiceRequest<?>) request.getSpiceRequest()).setRestTemplate(restTemplate);
        }
        if (request.getSpiceRequest() instanceof JiraSpringAndroidSpiceRequest) {
            ((JiraSpringAndroidSpiceRequest<?>) request.getSpiceRequest()).setHttpAuthentication(createAuthenticationHeader());
            ((JiraSpringAndroidSpiceRequest<?>) request.getSpiceRequest()).setBaseUrl(getBaseUrl());
        }

        super.addRequest(request, listRequestListener);
    }

    @Override
    public CacheManager createCacheManager(Application application) throws CacheCreationException {
        CacheManager cacheManager = new CacheManager();
        cacheManager.addPersister(new JacksonObjectPersisterFactory(application));
        return cacheManager;
    }

    @Override
    public RestTemplate createRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        // web services support json responses
        MappingJacksonHttpMessageConverter jsonConverter = new MappingJacksonHttpMessageConverter();
        final List<HttpMessageConverter<?>> listHttpMessageConverters = restTemplate.getMessageConverters();

        listHttpMessageConverters.add(jsonConverter);
        restTemplate.setMessageConverters(listHttpMessageConverters);

        return restTemplate;
    }
}
