package com.riaancornelius.flux.jira.domain.issue;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * User: riaan.cornelius
 */
public class Component {

    private Long id;

    private String name;

    private String description;

    @JsonProperty(value = "self")
    private String restUrl;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRestUrl() {
        return restUrl;
    }

    public void setRestUrl(String restUrl) {
        this.restUrl = restUrl;
    }
}
