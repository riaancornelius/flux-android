package com.riaancornelius.flux.jira.domain.sprint;

import com.riaancornelius.flux.jira.domain.sprint.burndown.StatisticField;
import com.riaancornelius.flux.jira.domain.sprint.burndown.WorkRateData;

/**
 * @author Elsabe
 */
public class Burndown {
    private long startTime;
    private long endTime;
    private long completeTime;
    private long now;

    private WorkRateData workRateData;
    private StatisticField statisticField;

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
}
