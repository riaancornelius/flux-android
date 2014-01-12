package com.riaancornelius.flux.jira.domain.issue;

import java.util.ArrayList;

/**
 * User: riaan.cornelius
 */
public class Comments {

    private Long startAt;

    private Long maxResults;

    private Long total;

    private ArrayList<Comment> comments;

    public Long getStartAt() {
        return startAt;
    }

    public void setStartAt(Long startAt) {
        this.startAt = startAt;
    }

    public Long getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(Long maxResults) {
        this.maxResults = maxResults;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }
}
