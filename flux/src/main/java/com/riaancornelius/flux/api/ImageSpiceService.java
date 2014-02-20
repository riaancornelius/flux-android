package com.riaancornelius.flux.api;

import android.app.Application;

import com.octo.android.robospice.SpringAndroidSpiceService;
import com.octo.android.robospice.persistence.CacheManager;
import com.octo.android.robospice.persistence.exception.CacheCreationException;
import com.octo.android.robospice.persistence.springandroid.json.jackson.JacksonObjectPersisterFactory;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by Elsabe on 2014/02/19.
 */
public class ImageSpiceService extends SpringAndroidSpiceService {
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

    @Override
    public CacheManager createCacheManager(Application application) throws CacheCreationException {
        CacheManager cacheManager = new CacheManager();
        cacheManager.addPersister(new JacksonObjectPersisterFactory(application));
        return cacheManager;
    }
}
