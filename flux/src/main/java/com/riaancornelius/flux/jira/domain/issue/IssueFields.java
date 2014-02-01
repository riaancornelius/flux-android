package com.riaancornelius.flux.jira.domain.issue;

import com.riaancornelius.flux.jira.domain.author.Author;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.Date;

/**
 * User: riaan.cornelius
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class IssueFields {

    private String summary;

    private IssueType issueType;

    private Date created;

    private Date updated;

    private String description;

    private Date lastViewed;

    private Priority priority;

    private Status status;

    private ArrayList<Component> components;

    private Author reporter;

    private Author assignee;

    @JsonProperty(value = "comment")
    private Comments commentList;

    @JsonProperty(value = "attachment")
    private ArrayList<Attachment> attachmentList;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public IssueType getIssueType() {
        return issueType;
    }

    public void setIssueType(IssueType issueType) {
        this.issueType = issueType;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getLastViewed() {
        return lastViewed;
    }

    public void setLastViewed(Date lastViewed) {
        this.lastViewed = lastViewed;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ArrayList<Component> getComponents() {
        return components;
    }

    public void setComponents(ArrayList<Component> components) {
        this.components = components;
    }

    public Author getReporter() {
        return reporter;
    }

    public void setReporter(Author reporter) {
        this.reporter = reporter;
    }

    public Author getAssignee() {
        return assignee;
    }

    public void setAssignee(Author assignee) {
        this.assignee = assignee;
    }

    public Comments getCommentList() {
        return commentList;
    }

    public void setCommentList(Comments commentList) {
        this.commentList = commentList;
    }

    public ArrayList<Attachment> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(ArrayList<Attachment> attachmentList) {
        this.attachmentList = attachmentList;
    }
}
