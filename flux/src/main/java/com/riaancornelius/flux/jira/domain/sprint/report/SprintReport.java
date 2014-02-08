package com.riaancornelius.flux.jira.domain.sprint.report;

import com.riaancornelius.flux.jira.domain.sprint.Sprint;
import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * User: riaan.cornelius
 */
public class SprintReport {

    @JsonProperty("contents")
    private Contents contents;
    @JsonProperty("sprint")
    private Sprint sprint;
    @JsonProperty("supportsPages")
    private Boolean supportsPages;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    @JsonProperty("contents")
    public Contents getContents() {
        return contents;
    }

    @JsonProperty("contents")
    public void setContents(Contents contents) {
        this.contents = contents;
    }

    @JsonProperty("sprint")
    public Sprint getSprint() {
        return sprint;
    }

    @JsonProperty("sprint")
    public void setSprint(Sprint sprint) {
        this.sprint = sprint;
    }

    @JsonProperty("supportsPages")
    public Boolean getSupportsPages() {
        return supportsPages;
    }

    @JsonProperty("supportsPages")
    public void setSupportsPages(Boolean supportsPages) {
        this.supportsPages = supportsPages;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperties(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
