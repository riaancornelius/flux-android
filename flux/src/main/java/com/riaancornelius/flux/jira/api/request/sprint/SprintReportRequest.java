package com.riaancornelius.flux.jira.api.request.sprint;

import com.riaancornelius.flux.jira.api.request.JiraSpringAndroidSpiceRequest;
import com.riaancornelius.flux.jira.domain.sprint.report.SprintReport;
import org.springframework.http.*;

/**
 * User: riaan.cornelius
 */
public class SprintReportRequest extends JiraSpringAndroidSpiceRequest<SprintReport> {
    private final Long rapidViewId;
    private final Long sprintId;

    public SprintReportRequest(Long rapidViewId, Long sprintId) {
        super(SprintReport.class);
        this.rapidViewId = rapidViewId;
        this.sprintId = sprintId;
    }

    @Override
    protected final HttpMethod getHttpMethodType(){
        return HttpMethod.GET;
    }

    @Override
    protected final String getUrlFragment(){
        String url = String.format(
                "rest/greenhopper/latest/rapid/charts/sprintreport?rapidViewId=%s&sprintId=%s",
                rapidViewId, sprintId);
        return url;
    }

    /**
     * This method generates a unique cache key for this request. In this case
     * our cache key depends just on the keyword.
     * @return
     */
    public String createCacheKey() {
        return "sprintReport." + rapidViewId + "." + sprintId;
    }
}
