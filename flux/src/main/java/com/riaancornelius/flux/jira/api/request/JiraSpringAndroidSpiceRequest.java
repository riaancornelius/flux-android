package com.riaancornelius.flux.jira.api.request;

import android.util.Log;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

/**
 * User: riaan.cornelius
 */
public abstract class JiraSpringAndroidSpiceRequest<RESULT> extends SpringAndroidSpiceRequest<RESULT> {

    protected HttpAuthentication httpAuthentication;

    protected String baseUrl;

    public JiraSpringAndroidSpiceRequest(Class<RESULT> clazz) {
        super(clazz);
    }

    @Override
    public RESULT loadDataFromNetwork() throws Exception {
        RestTemplate restTemplate = getRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAuthorization(getHttpAuthentication());

        HttpEntity<String> entity = new HttpEntity<String>(headers);

        Log.i("REST", "restTemplate: " + restTemplate);
        Log.i("REST", "Url = " + buildUrl());
        ResponseEntity<RESULT> responseEntity = restTemplate.exchange(buildUrl(), getHttpMethodType(), entity, getResultType());
        Log.i("REST", "responseEntity: " + responseEntity);
        Log.i("REST", "responseEntity status: " + responseEntity.getStatusCode().getReasonPhrase());
        Log.i("REST", "responseEntity status: " + responseEntity.getBody());

        return responseEntity.getBody();
    }

    protected String buildUrl() {
        return getBaseUrl() + getUrlFragment();
    }

    public HttpAuthentication getHttpAuthentication() {
        return httpAuthentication;
    }

    public void setHttpAuthentication(HttpAuthentication httpAuthentication) {
        this.httpAuthentication = httpAuthentication;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    protected abstract HttpMethod getHttpMethodType();

    /**
     * Get the last part of the url that specifies the rest service to call with all it's parameters populated.
     * <p/>
     * Let's say you want to call:
     * https://projectflux.atlassian.net/rest/greenhopper/latest/rapid/charts/sprintreport?rapidViewId=1&sprintId=3
     * <p/>
     * The base url would be 'https://projectflux.atlassian.net/' and this method should return:
     * 'rest/greenhopper/latest/rapid/charts/sprintreport?rapidViewId=1&sprintId=3'
     *
     * @return The url without baseUrl and with all parameters expanded and escaped if necessary.
     */
    protected abstract String getUrlFragment();

}
