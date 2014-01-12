package com.riaancornelius.flux.jira.domain.issue;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * User: riaan.cornelius
 */
public class IssueType {

    private Long id;

    @JsonProperty(value = "self")
    private String restUrl;

    private String description;

    private String iconUrl;

    private String name;

    private boolean subtask;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRestUrl() {
        return restUrl;
    }

    public void setRestUrl(String restUrl) {
        this.restUrl = restUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSubtask() {
        return subtask;
    }

    public void setSubtask(boolean subtask) {
        this.subtask = subtask;
    }
}
