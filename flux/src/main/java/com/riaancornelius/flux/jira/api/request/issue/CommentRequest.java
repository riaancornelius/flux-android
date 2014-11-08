package com.riaancornelius.flux.jira.api.request.issue;

import com.riaancornelius.flux.jira.api.request.JiraSpringAndroidSpiceRequest;
import com.riaancornelius.flux.jira.api.service.JiraJacksonSpringAndroidSpiceService;
import com.riaancornelius.flux.jira.domain.issue.Issue;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpMethod;

/**
 * Created by Elsabe on 2014-11-08.
 */
public class CommentRequest extends JiraSpringAndroidSpiceRequest<CommentRequest.RequestResponse> {

    private final String issueKey;

    public CommentRequest(Issue issue, String comment) {
        super(RequestResponse.class);
        this.issueKey = issue.getKey();

        RequestBody body = new RequestBody();
        body.setBody(comment);
        setRequestBody(body);
    }

    @Override
    protected HttpMethod getHttpMethodType() {
        return HttpMethod.POST;
    }

    @Override
    protected String getUrlFragment() {
        return "rest/api/latest/issue/" + issueKey + "/comment";
    }

    public static class RequestBody {
       private String body;

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RequestResponse {}
}
