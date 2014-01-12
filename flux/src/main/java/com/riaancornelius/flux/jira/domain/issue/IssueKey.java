package com.riaancornelius.flux.jira.domain.issue;

/**
 * User: riaan.cornelius
 */
public class IssueKey {

    private String key;

    private boolean added;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isAdded() {
        return added;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }
}
