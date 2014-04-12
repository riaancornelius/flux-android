package com.riaancornelius.flux.jira.domain.sprint.burndown;

/**
 * @author Elsabe
 */
public class Rate {
    private long start;
    private long end;
    private int rate;

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "Rate{" +
                "start=" + start +
                ", end=" + end +
                ", rate=" + rate +
                '}';
    }
}
