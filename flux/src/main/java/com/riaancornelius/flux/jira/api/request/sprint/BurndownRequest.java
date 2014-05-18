package com.riaancornelius.flux.jira.api.request.sprint;

import com.riaancornelius.flux.jira.api.request.JiraSpringAndroidSpiceRequest;
import com.riaancornelius.flux.jira.domain.sprint.Burndown;

import org.springframework.http.HttpMethod;

/**
 * @author Elsabe
 */
public class BurndownRequest extends JiraSpringAndroidSpiceRequest<Burndown> {
    private Long boardId;
    private Long sprintId;

    public BurndownRequest(long boardId, long sprintId) {
        super(Burndown.class);
        this.boardId = boardId;
        this.sprintId = sprintId;
    }

    @Override
    protected HttpMethod getHttpMethodType() {
        return HttpMethod.GET;
    }

    @Override
    protected String getUrlFragment() {
        String url = String.format("rest/greenhopper/1.0/rapid/charts/scopechangeburndownchart?rapidViewId=%s&sprintId=%s",
                boardId, sprintId);
        return url;
    }

    public Object createCacheKey() {
        return "burndown" + boardId + sprintId;
    }
}
