package com.riaancornelius.flux.jira.domain.sprint;

import com.riaancornelius.flux.jira.JsonDateDeserializer;
import com.riaancornelius.flux.jira.JsonDateSerializer;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.ArrayList;
import java.util.Date;

/**
 * User: riaan.cornelius
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Sprint {

    private long id;

    private String name;

    private String state;

    private long linkedPagesCount;

    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date startDate;

    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date endDate;

    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date completedDate;

    @JsonIgnore
    private ArrayList remoteLinks;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getLinkedPagesCount() {
        return linkedPagesCount;
    }

    public void setLinkedPagesCount(long linkedPagesCount) {
        this.linkedPagesCount = linkedPagesCount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(Date completedDate) {
        this.completedDate = completedDate;
    }

    public ArrayList getRemoteLinks() {
        return remoteLinks;
    }

    public void setRemoteLinks(ArrayList remoteLinks) {
        this.remoteLinks = remoteLinks;
    }
}
