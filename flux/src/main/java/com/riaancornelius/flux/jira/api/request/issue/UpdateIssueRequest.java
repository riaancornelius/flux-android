package com.riaancornelius.flux.jira.api.request.issue;

import android.util.Log;

import com.riaancornelius.flux.jira.api.request.JiraSpringAndroidSpiceRequest;
import com.riaancornelius.flux.jira.domain.issue.Issue;

import org.springframework.http.HttpMethod;

/**
 * Update issues in JIRA.
 * TODO
 *
 * @author Elsabe
 */
public class UpdateIssueRequest extends JiraSpringAndroidSpiceRequest<String> {
    private Issue issue;

    public UpdateIssueRequest(Issue issue) {
        super(String.class);
        //TODO this doesn;t work...
        String requestBody = "{\"fields\":{\"assignee\"{\"key\":\"" + issue.getFields().getAssignee().getKey() + "\"}}}";
        Log.d("UpdateIssueRequest", "Request body = " + requestBody);
        this.issue = issue;
    }

    @Override
    protected HttpMethod getHttpMethodType() {
        return HttpMethod.PUT;
    }

    @Override
    protected String getUrlFragment() {
        return "rest/api/latest/issue/"+issue.getKey();
    }
}
