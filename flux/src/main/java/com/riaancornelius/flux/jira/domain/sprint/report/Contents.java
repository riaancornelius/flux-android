
package com.riaancornelius.flux.jira.domain.sprint.report;

import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Generated("com.googlecode.jsonschema2pojo")
public class Contents {

    @JsonProperty("completedIssues")
    private List<Issue> completedIssues = new ArrayList<Issue>();
    @JsonProperty("incompletedIssues")
    private List<Issue> incompletedIssues = new ArrayList<Issue>();
    @JsonProperty("puntedIssues")
    private List<Issue> puntedIssues = new ArrayList<Issue>();
    @JsonProperty("completedIssuesEstimateSum")
    private EstimateSum completedIssuesEstimateSum;
    @JsonProperty("incompletedIssuesEstimateSum")
    private EstimateSum incompletedIssuesEstimateSum;
    @JsonProperty("allIssuesEstimateSum")
    private EstimateSum allIssuesEstimateSum;
    @JsonProperty("puntedIssuesEstimateSum")
    private EstimateSum puntedIssuesEstimateSum;
    @JsonProperty("issueKeysAddedDuringSprint")
    private IssueKeysAddedDuringSprint issueKeysAddedDuringSprint;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("completedIssues")
    public List<Issue> getCompletedIssues() {
        return completedIssues;
    }

    @JsonProperty("completedIssues")
    public void setCompletedIssues(List<Issue> completedIssues) {
        this.completedIssues = completedIssues;
    }

    @JsonProperty("incompletedIssues")
    public List<Issue> getIncompletedIssues() {
        return incompletedIssues;
    }

    @JsonProperty("incompletedIssues")
    public void setIncompletedIssues(List<Issue> incompletedIssues) {
        this.incompletedIssues = incompletedIssues;
    }

    @JsonProperty("puntedIssues")
    public List<Issue> getPuntedIssues() {
        return puntedIssues;
    }

    @JsonProperty("puntedIssues")
    public void setPuntedIssues(List<Issue> puntedIssues) {
        this.puntedIssues = puntedIssues;
    }

    @JsonProperty("completedIssuesEstimateSum")
    public EstimateSum getCompletedIssuesEstimateSum() {
        return completedIssuesEstimateSum;
    }

    @JsonProperty("completedIssuesEstimateSum")
    public void setCompletedIssuesEstimateSum(EstimateSum completedIssuesEstimateSum) {
        this.completedIssuesEstimateSum = completedIssuesEstimateSum;
    }

    @JsonProperty("incompletedIssuesEstimateSum")
    public EstimateSum getIncompletedIssuesEstimateSum() {
        return incompletedIssuesEstimateSum;
    }

    @JsonProperty("incompletedIssuesEstimateSum")
    public void setIncompletedIssuesEstimateSum(EstimateSum incompletedIssuesEstimateSum) {
        this.incompletedIssuesEstimateSum = incompletedIssuesEstimateSum;
    }

    @JsonProperty("allIssuesEstimateSum")
    public EstimateSum getAllIssuesEstimateSum() {
        return allIssuesEstimateSum;
    }

    @JsonProperty("allIssuesEstimateSum")
    public void setAllIssuesEstimateSum(EstimateSum allIssuesEstimateSum) {
        this.allIssuesEstimateSum = allIssuesEstimateSum;
    }

    @JsonProperty("puntedIssuesEstimateSum")
    public EstimateSum getPuntedIssuesEstimateSum() {
        return puntedIssuesEstimateSum;
    }

    @JsonProperty("puntedIssuesEstimateSum")
    public void setPuntedIssuesEstimateSum(EstimateSum puntedIssuesEstimateSum) {
        this.puntedIssuesEstimateSum = puntedIssuesEstimateSum;
    }

    @JsonProperty("issueKeysAddedDuringSprint")
    public IssueKeysAddedDuringSprint getIssueKeysAddedDuringSprint() {
        return issueKeysAddedDuringSprint;
    }

    @JsonProperty("issueKeysAddedDuringSprint")
    public void setIssueKeysAddedDuringSprint(IssueKeysAddedDuringSprint issueKeysAddedDuringSprint) {
        this.issueKeysAddedDuringSprint = issueKeysAddedDuringSprint;
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
