package com.riaancornelius.flux.jira.api.request.sprint;

import com.riaancornelius.flux.jira.api.request.JiraSpringAndroidSpiceRequest;
import com.riaancornelius.flux.jira.domain.sprint.Sprints;
import org.springframework.http.HttpMethod;

/**
 * User: riaan.cornelius
 */
public class SprintsRequest extends JiraSpringAndroidSpiceRequest<Sprints> {
    private final Long rapidViewId;

    public SprintsRequest(Long rapidViewId) {
        super(Sprints.class);
        this.rapidViewId = rapidViewId;
    }

    @Override
    protected HttpMethod getHttpMethodType() {
        return HttpMethod.GET;
    }

    @Override
    protected String getUrlFragment() {
        return String.format("rest/greenhopper/latest/sprintquery/%s", rapidViewId);
    }

    /**
     * This method generates a unique cache key for this request. In this case
     * our cache key depends just on the keyword.
     * @return
     */
    public String createCacheKey() {
        return "sprints." + rapidViewId;
    }
}
