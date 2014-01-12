package com.riaancornelius.flux.jira.domain.issue;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;

/**
 * User: riaan.cornelius
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Issue {

    private Long id;

    private String key;

    @JsonProperty(value = "self")
    private String restUrl;

    private IssueFields fields;

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

    public String getRestUrl() {
        return restUrl;
    }

    public void setRestUrl(String restUrl) {
        this.restUrl = restUrl;
    }

    public IssueFields getFields() {
        return fields;
    }

    public void setFields(IssueFields fields) {
        this.fields = fields;
    }
}
