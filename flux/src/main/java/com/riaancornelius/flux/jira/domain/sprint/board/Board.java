package com.riaancornelius.flux.jira.domain.sprint.board;

/**
 * Created by riaan.cornelius on 2014/03/06.
 */
public class Board {

    private int id;
    private String name;
    private boolean canEdit;
    private boolean sprintSupportEnabled;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

    public boolean isSprintSupportEnabled() {
        return sprintSupportEnabled;
    }

    public void setSprintSupportEnabled(boolean sprintSupportEnabled) {
        this.sprintSupportEnabled = sprintSupportEnabled;
    }
}
