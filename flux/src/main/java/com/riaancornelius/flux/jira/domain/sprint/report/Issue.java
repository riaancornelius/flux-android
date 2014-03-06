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
public class Issue {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("key")
    private String key;
    @JsonProperty("hidden")
    private Boolean hidden;
    @JsonProperty("typeName")
    private String typeName;
    @JsonProperty("typeId")
    private String typeId;
    @JsonProperty("summary")
    private String summary;
    @JsonProperty("typeUrl")
    private String typeUrl;
    @JsonProperty("priorityUrl")
    private String priorityUrl;
    @JsonProperty("priorityName")
    private String priorityName;
    @JsonProperty("done")
    private Boolean done;
    @JsonProperty("assignee")
    private String assignee;
    @JsonProperty("assigneeName")
    private String assigneeName;
    @JsonProperty("avatarUrl")
    private String avatarUrl;
    @JsonProperty("color")
    private String color;
    @JsonProperty("epic")
    private String epic;
    @JsonProperty("columnStatistic")
    private ColumnStatistic columnStatistic;
    @JsonProperty("estimateStatistic")
    private EstimateStatistic estimateStatistic;
    @JsonProperty("statusId")
    private String statusId;
    @JsonProperty("statusName")
    private String statusName;
    @JsonProperty("statusUrl")
    private String statusUrl;
    @JsonProperty("status")
    private Status status;
    @JsonProperty("fixVersions")
    private List<Object> fixVersions = new ArrayList<Object>();
    @JsonProperty("projectId")
    private Integer projectId;
    @JsonProperty("linkedPagesCount")
    private Integer linkedPagesCount;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("key")
    public String getKey() {
        return key;
    }

    @JsonProperty("key")
    public void setKey(String key) {
        this.key = key;
    }

    @JsonProperty("hidden")
    public Boolean getHidden() {
        return hidden;
    }

    @JsonProperty("hidden")
    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    @JsonProperty("typeName")
    public String getTypeName() {
        return typeName;
    }

    @JsonProperty("typeName")
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @JsonProperty("typeId")
    public String getTypeId() {
        return typeId;
    }

    @JsonProperty("typeId")
    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    @JsonProperty("summary")
    public String getSummary() {
        return summary;
    }

    @JsonProperty("summary")
    public void setSummary(String summary) {
        this.summary = summary;
    }

    @JsonProperty("typeUrl")
    public String getTypeUrl() {
        return typeUrl;
    }

    @JsonProperty("typeUrl")
    public void setTypeUrl(String typeUrl) {
        this.typeUrl = typeUrl;
    }

    @JsonProperty("priorityUrl")
    public String getPriorityUrl() {
        return priorityUrl;
    }

    @JsonProperty("priorityUrl")
    public void setPriorityUrl(String priorityUrl) {
        this.priorityUrl = priorityUrl;
    }

    @JsonProperty("priorityName")
    public String getPriorityName() {
        return priorityName;
    }

    @JsonProperty("priorityName")
    public void setPriorityName(String priorityName) {
        this.priorityName = priorityName;
    }

    @JsonProperty("done")
    public Boolean getDone() {
        return done;
    }

    @JsonProperty("done")
    public void setDone(Boolean done) {
        this.done = done;
    }

    @JsonProperty("assignee")
    public String getAssignee() {
        return assignee;
    }

    @JsonProperty("assignee")
    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    @JsonProperty("assigneeName")
    public String getAssigneeName() {
        return assigneeName;
    }

    @JsonProperty("assigneeName")
    public void setAssigneeName(String assigneeName) {
        this.assigneeName = assigneeName;
    }

    @JsonProperty("avatarUrl")
    public String getAvatarUrl() {
        return avatarUrl;
    }

    @JsonProperty("avatarUrl")
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @JsonProperty("color")
    public String getColor() {
        return color;
    }

    @JsonProperty("color")
    public void setColor(String color) {
        this.color = color;
    }

    @JsonProperty("epic")
    public String getEpic() {
        return epic;
    }

    @JsonProperty("epic")
    public void setEpic(String epic) {
        this.epic = epic;
    }

    @JsonProperty("columnStatistic")
    public ColumnStatistic getColumnStatistic() {
        return columnStatistic;
    }

    @JsonProperty("columnStatistic")
    public void setColumnStatistic(ColumnStatistic columnStatistic) {
        this.columnStatistic = columnStatistic;
    }

    @JsonProperty("estimateStatistic")
    public EstimateStatistic getEstimateStatistic() {
        return estimateStatistic;
    }

    @JsonProperty("estimateStatistic")
    public void setEstimateStatistic(EstimateStatistic estimateStatistic) {
        this.estimateStatistic = estimateStatistic;
    }

    @JsonProperty("statusId")
    public String getStatusId() {
        return statusId;
    }

    @JsonProperty("statusId")
    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    @JsonProperty("statusName")
    public String getStatusName() {
        return statusName;
    }

    @JsonProperty("statusName")
    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    @JsonProperty("statusUrl")
    public String getStatusUrl() {
        return statusUrl;
    }

    @JsonProperty("statusUrl")
    public void setStatusUrl(String statusUrl) {
        this.statusUrl = statusUrl;
    }

    @JsonProperty("status")
    public Status getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(Status status) {
        this.status = status;
    }

    @JsonProperty("fixVersions")
    public List<Object> getFixVersions() {
        return fixVersions;
    }

    @JsonProperty("fixVersions")
    public void setFixVersions(List<Object> fixVersions) {
        this.fixVersions = fixVersions;
    }

    @JsonProperty("projectId")
    public Integer getProjectId() {
        return projectId;
    }

    @JsonProperty("projectId")
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @JsonProperty("linkedPagesCount")
    public Integer getLinkedPagesCount() {
        return linkedPagesCount;
    }

    @JsonProperty("linkedPagesCount")
    public void setLinkedPagesCount(Integer linkedPagesCount) {
        this.linkedPagesCount = linkedPagesCount;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperties(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "Issue{" +
                "id=" + id +
                ", key='" + key + '\'' +
                ", hidden=" + hidden +
                ", typeName='" + typeName + '\'' +
                ", typeId='" + typeId + '\'' +
                ", summary='" + summary + '\'' +
                ", typeUrl='" + typeUrl + '\'' +
                ", priorityUrl='" + priorityUrl + '\'' +
                ", priorityName='" + priorityName + '\'' +
                ", done=" + done +
                ", assignee='" + assignee + '\'' +
                ", assigneeName='" + assigneeName + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", color='" + color + '\'' +
                ", epic='" + epic + '\'' +
                ", columnStatistic=" + columnStatistic +
                ", estimateStatistic=" + estimateStatistic +
                ", statusId='" + statusId + '\'' +
                ", statusName='" + statusName + '\'' +
                ", statusUrl='" + statusUrl + '\'' +
                ", status=" + status +
                ", fixVersions=" + fixVersions +
                ", projectId=" + projectId +
                ", linkedPagesCount=" + linkedPagesCount +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
