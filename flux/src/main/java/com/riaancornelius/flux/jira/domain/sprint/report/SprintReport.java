package com.riaancornelius.flux.jira.domain.sprint.report;

import com.riaancornelius.flux.jira.domain.issue.Issue;
import com.riaancornelius.flux.jira.domain.issue.IssueKey;
import com.riaancornelius.flux.jira.domain.sprint.Sprint;
import com.riaancornelius.flux.jira.domain.sprint.StoryPointValue;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.ArrayList;

/**
 * User: riaan.cornelius
 */
public class SprintReport {

    private Contents contents;

    private Sprint sprint;

    private boolean supportsPages;

    public Contents getContents() {
        return contents;
    }

    public void setContents(Contents contents) {
        this.contents = contents;
    }

    public Sprint getSprint() {
        return sprint;
    }

    public void setSprint(Sprint sprint) {
        this.sprint = sprint;
    }

    public boolean isSupportsPages() {
        return supportsPages;
    }

    public void setSupportsPages(boolean supportsPages) {
        this.supportsPages = supportsPages;
    }

    public class Contents {

        @JsonIgnore
        private ArrayList<Issue> completedIssues;

        @JsonIgnore
        private ArrayList<Issue> incompletedIssues;

        @JsonIgnore
        private ArrayList<Issue> puntedIssues;

        private StoryPointValue completedIssuesEstimateSum;

        private StoryPointValue incompletedIssuesEstimateSum;

        private StoryPointValue allIssuesEstimateSum;

        private StoryPointValue puntedIssuesEstimateSum;

        @JsonIgnore
        private ArrayList<IssueKey> issueKeysAddedDuringSprint;

        public ArrayList<Issue> getCompletedIssues() {
            return completedIssues;
        }

        public void setCompletedIssues(ArrayList<Issue> completedIssues) {
            this.completedIssues = completedIssues;
        }

        public ArrayList<Issue> getIncompletedIssues() {
            return incompletedIssues;
        }

        public void setIncompletedIssues(ArrayList<Issue> incompletedIssues) {
            this.incompletedIssues = incompletedIssues;
        }

        public ArrayList<Issue> getPuntedIssues() {
            return puntedIssues;
        }

        public void setPuntedIssues(ArrayList<Issue> puntedIssues) {
            this.puntedIssues = puntedIssues;
        }

        public StoryPointValue getCompletedIssuesEstimateSum() {
            return completedIssuesEstimateSum;
        }

        public void setCompletedIssuesEstimateSum(StoryPointValue completedIssuesEstimateSum) {
            this.completedIssuesEstimateSum = completedIssuesEstimateSum;
        }

        public StoryPointValue getIncompletedIssuesEstimateSum() {
            return incompletedIssuesEstimateSum;
        }

        public void setIncompletedIssuesEstimateSum(StoryPointValue incompletedIssuesEstimateSum) {
            this.incompletedIssuesEstimateSum = incompletedIssuesEstimateSum;
        }

        public StoryPointValue getAllIssuesEstimateSum() {
            return allIssuesEstimateSum;
        }

        public void setAllIssuesEstimateSum(StoryPointValue allIssuesEstimateSum) {
            this.allIssuesEstimateSum = allIssuesEstimateSum;
        }

        public StoryPointValue getPuntedIssuesEstimateSum() {
            return puntedIssuesEstimateSum;
        }

        public void setPuntedIssuesEstimateSum(StoryPointValue puntedIssuesEstimateSum) {
            this.puntedIssuesEstimateSum = puntedIssuesEstimateSum;
        }

        public ArrayList<IssueKey> getIssueKeysAddedDuringSprint() {
            return issueKeysAddedDuringSprint;
        }

        public void setIssueKeysAddedDuringSprint(ArrayList<IssueKey> issueKeysAddedDuringSprint) {
            this.issueKeysAddedDuringSprint = issueKeysAddedDuringSprint;
        }
    }
}
