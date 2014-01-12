package com.riaancornelius.flux.api;

import com.riaancornelius.flux.jira.api.request.JiraSpringAndroidSpiceRequest;
import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;

/**
 * User: riaan.cornelius
 */
public class JiraSpiceTestService extends JiraSpiceService {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public HttpAuthentication createAuthenticationHeader() {
        return new HttpBasicAuthentication("MockUsername", "MockPassword");
    }

    @Override
    public String getBaseUrl() {
        return "https://test.projectflux.atlassian.net/";
    }

    public void setValuesOn(JiraSpringAndroidSpiceRequest request){
        request.setRestTemplate(this.createRestTemplate());
        request.setHttpAuthentication(this.createAuthenticationHeader());
        request.setBaseUrl(this.getBaseUrl());
    }
}
