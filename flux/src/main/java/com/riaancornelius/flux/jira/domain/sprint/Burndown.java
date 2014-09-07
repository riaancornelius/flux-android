package com.riaancornelius.flux.jira.domain.sprint;

import com.riaancornelius.flux.jira.domain.sprint.burndown.BurndownChange;
import com.riaancornelius.flux.jira.domain.sprint.burndown.StatisticField;
import com.riaancornelius.flux.jira.domain.sprint.burndown.WorkRateData;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Elsabe
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Burndown {
    private long startTime;
    private long endTime;
    private long completeTime;
    private long now;

    private WorkRateData workRateData;
    private StatisticField statisticField;

    private Map<Long, ChangeList> changes;

    public Burndown() {
        changes = new HashMap<Long, ChangeList>();
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(long completeTime) {
        this.completeTime = completeTime;
    }

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
    }

    public WorkRateData getWorkRateData() {
        return workRateData;
    }

    public void setWorkRateData(WorkRateData workRateData) {
        this.workRateData = workRateData;
    }

    public StatisticField getStatisticField() {
        return statisticField;
    }

    public void setStatisticField(StatisticField statisticField) {
        this.statisticField = statisticField;
    }

    public Map<Long, ChangeList> getChanges() {
        return changes;
    }

    public void setChanges(Map<Long, ChangeList> changes) {
        this.changes = changes;
    }


    @Override
    public String toString() {
        return "Burndown{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", completeTime=" + completeTime +
                ", now=" + now +
                ", workRateData=" + workRateData +
                ", statisticField=" + statisticField +
                ", changes=" + changes +
                '}';
    }

    public static class ChangeList extends ArrayList<BurndownChange> {
    }
}
