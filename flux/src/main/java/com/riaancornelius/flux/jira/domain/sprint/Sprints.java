package com.riaancornelius.flux.jira.domain.sprint;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.ArrayList;

/**
 * User: riaan.cornelius
 */
@JsonIgnoreProperties(ignoreUnknown = true)

public class Sprints {
    private static final long serialVersionUID = 8192333539004718470L;

    private ArrayList<Sprint> sprints;
    private long rapidViewId;

    public ArrayList<Sprint> getSprints() {
        return sprints;
    }

    public void setSprints(ArrayList<Sprint> sprints) {
        this.sprints = sprints;
    }

    public long getRapidViewId() {
        return rapidViewId;
    }

    public void setRapidViewId(long rapidViewId) {
        this.rapidViewId = rapidViewId;
    }

    @JsonIgnore
    public Sprint getFirstActiveSprint(){
        for(Sprint sprint : getSprints()){
            if ("ACTIVE".equals(sprint.getState())) {
                return sprint;
            }
        }
        return null;
    }
}
