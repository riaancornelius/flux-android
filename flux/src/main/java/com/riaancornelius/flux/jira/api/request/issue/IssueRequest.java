package com.riaancornelius.flux.jira.api.request.issue;

import com.riaancornelius.flux.jira.api.request.JiraSpringAndroidSpiceRequest;
import com.riaancornelius.flux.jira.domain.issue.Issue;
import org.springframework.http.HttpMethod;

/**
 * User: riaan.cornelius
 */
public class IssueRequest extends JiraSpringAndroidSpiceRequest<Issue> {
    private final String issueKey;

    public IssueRequest(String issueKey) {
        super(Issue.class);
        this.issueKey = issueKey;
    }

    @Override
    protected HttpMethod getHttpMethodType() {
        return HttpMethod.GET;
    }

    @Override
    protected String getUrlFragment() {
        return String.format("rest/api/latest/issue/%s", issueKey);
    }

    /**
     * This method generates a unique cache key for this request. In this case
     * our cache key depends just on the keyword.
     * @return
     */
    public String createCacheKey() {
        return "issue." + issueKey;
    }
}
