package com.riaancornelius.flux.jira.api.request.issue;

import android.util.Log;

import com.riaancornelius.flux.jira.api.request.JiraSpringAndroidSpiceRequest;
import com.riaancornelius.flux.jira.domain.author.Author;
import com.riaancornelius.flux.jira.domain.issue.Issue;
import com.riaancornelius.flux.jira.domain.issue.IssueFields;

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

        Request requestBody = new Request();
        requestBody.fields.assignee.name = issue.getFields().getAssignee().getKey();

        Log.d("UpdateIssueRequest", "Request body = " + requestBody);
        setRequestBody(requestBody);
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

    /**
     * Private classes for request.
     */
    private class Request {
        public Fields getFields() {
            return fields;
        }

        public void setFields(Fields fields) {
            this.fields = fields;
        }

        private Fields fields = new Fields();
    }

    private class Fields {
        public Assignee getAssignee() {
            return assignee;
        }

        public void setAssignee(Assignee assignee) {
            this.assignee = assignee;
        }

        private Assignee assignee = new Assignee();
    }

    private class Assignee {
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private String name;
    }
}
