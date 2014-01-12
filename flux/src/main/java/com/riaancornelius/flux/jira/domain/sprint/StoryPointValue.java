package com.riaancornelius.flux.jira.domain.sprint;

/**
 * User: riaan.cornelius
 */
public class StoryPointValue {

    private Long value;
    private String text;

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
