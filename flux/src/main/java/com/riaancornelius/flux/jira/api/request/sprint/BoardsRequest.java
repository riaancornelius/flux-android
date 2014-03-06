package com.riaancornelius.flux.jira.api.request.sprint;

import com.riaancornelius.flux.jira.api.request.JiraSpringAndroidSpiceRequest;
import com.riaancornelius.flux.jira.domain.sprint.Sprints;
import com.riaancornelius.flux.jira.domain.sprint.board.Boards;

import org.springframework.http.HttpMethod;

/**
 * User: riaan.cornelius
 */
public class BoardsRequest extends JiraSpringAndroidSpiceRequest<Boards> {

    public BoardsRequest() {
        super(Boards.class);
    }

    @Override
    protected HttpMethod getHttpMethodType() {
        return HttpMethod.GET;
    }

    @Override
    protected String getUrlFragment() {
        return "rest/greenhopper/latest/rapidview";
    }

    /**
     * This method generates a unique cache key for this request. In this case
     * our cache key depends just on the keyword.
     * @return
     */
    public String createCacheKey() {
        return "RapidViewBoards";
    }
}
