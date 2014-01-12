package com.riaancornelius.flux.jira.domain.issue;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * User: riaan.cornelius
 */
public class Priority {

    private Long id;

    private String name;

    @JsonProperty(value = "self")
    private String restUrl;

    private String iconUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRestUrl() {
        return restUrl;
    }

    public void setRestUrl(String restUrl) {
        this.restUrl = restUrl;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}
