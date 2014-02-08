package com.riaancornelius.flux.jira.api.request.issue;

import com.riaancornelius.flux.jira.api.request.JiraSpringAndroidSpiceRequest;
import com.riaancornelius.flux.jira.domain.issue.Issue;

import org.springframework.http.HttpMethod;

/**
 * Update issues in JIRA.
 * TODO
 *
 * @author Elsabe
 */
public class UpdateIssueRequest extends JiraSpringAndroidSpiceRequest<Void> {
    public UpdateIssueRequest(Issue issue) {
        super(Void.class);
    }

    @Override
    protected HttpMethod getHttpMethodType() {
        return HttpMethod.PUT;
    }

    @Override
    protected String getUrlFragment() {
        return null;
    }
}
