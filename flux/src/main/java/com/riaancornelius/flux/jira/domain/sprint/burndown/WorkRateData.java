package com.riaancornelius.flux.jira.domain.sprint.burndown;

import java.util.List;

/**
 * @author Elsabe
 */
public class WorkRateData {
    private String timezone;
    private List<Rate> rates;

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }

    @Override
    public String toString() {
        return "WorkRateData{" +
                "timezone='" + timezone + '\'' +
                ", rates=" + rates +
                '}';
    }
}
