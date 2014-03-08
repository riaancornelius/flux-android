package com.riaancornelius.flux.jira.api.request.sprint;

import com.riaancornelius.flux.jira.api.request.JiraSpringAndroidSpiceRequest;
import com.riaancornelius.flux.jira.domain.sprint.Sprints;

import org.springframework.http.HttpMethod;

/**
 * Return all created sprints for a specific rapidView.
 * User: riaan.cornelius
 */
public class SprintsRequest extends JiraSpringAndroidSpiceRequest<Sprints> {
    private final Long rapidViewId;
    private boolean showFuture;

    /**
     * @param rapidViewId
     * @param showFutureSprint <b>true</b> if sprints that haven't been started yet should shown.
     */
    public SprintsRequest(Long rapidViewId, boolean showFutureSprint) {
        super(Sprints.class);
        this.rapidViewId = rapidViewId;
        this.showFuture = showFutureSprint;
    }

    @Override
    protected HttpMethod getHttpMethodType() {
        return HttpMethod.GET;
    }

    @Override
    protected String getUrlFragment() {
        return String.format("rest/greenhopper/latest/sprintquery/%s?includeFutureSprints=%b", rapidViewId, showFuture);
    }

    /**
     * This method generates a unique cache key for this request. In this case
     * our cache key depends just on the keyword.
     *
     * @return
     */
    public String createCacheKey() {
        return "sprints." + rapidViewId;
    }
}
