package com.riaancornelius.flux.jira.api.request.user;

import com.riaancornelius.flux.jira.api.request.JiraSpringAndroidSpiceRequest;
import com.riaancornelius.flux.jira.domain.author.Author;
import com.riaancornelius.flux.jira.domain.author.AuthorList;

import org.springframework.http.HttpMethod;

import java.util.List;

/**
 * @author Elsabe
 */
public class UserRequest extends JiraSpringAndroidSpiceRequest<AuthorList> {
    private String query;

    public UserRequest(String queryString) {
        super(AuthorList.class);
        this.query = queryString;
    }

    @Override
    protected HttpMethod getHttpMethodType() {
        return HttpMethod.GET;
    }

    @Override
    protected String getUrlFragment() {
        return "rest/api/2/user/search?username="+query;
    }

    public String getCacheKey() {
        return "users"+query;
    }
}
