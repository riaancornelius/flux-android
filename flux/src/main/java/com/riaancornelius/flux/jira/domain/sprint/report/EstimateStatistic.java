
package com.riaancornelius.flux.jira.domain.sprint.report;

import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

@Generated("com.googlecode.jsonschema2pojo")
public class EstimateStatistic {

    @JsonProperty("statFieldId")
    private String statFieldId;
    @JsonProperty("statFieldValue")
    private StatFieldValue statFieldValue;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("statFieldId")
    public String getStatFieldId() {
        return statFieldId;
    }

    @JsonProperty("statFieldId")
    public void setStatFieldId(String statFieldId) {
        this.statFieldId = statFieldId;
    }

    @JsonProperty("statFieldValue")
    public StatFieldValue getStatFieldValue() {
        return statFieldValue;
    }

    @JsonProperty("statFieldValue")
    public void setStatFieldValue(StatFieldValue statFieldValue) {
        this.statFieldValue = statFieldValue;
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
