package com.riaancornelius.flux.jira.domain.issue;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * User: riaan.cornelius
 */
public class StatusCategory {

    private Long id;

    private String key;

    private String name;

    private String colorName;

    @JsonProperty(value = "self")
    private String restUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getRestUrl() {
        return restUrl;
    }

    public void setRestUrl(String restUrl) {
        this.restUrl = restUrl;
    }
}
