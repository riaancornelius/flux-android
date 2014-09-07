package com.riaancornelius.flux.jira.domain.sprint.burndown;

/**
 * @author Elsabe
 */
public class StatisticField {
    private String typeId;
    private String fieldId;
    private String id;
    private String name;
    private String renderer;
    private Boolean isValid;
    private Boolean isEnabled;

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRenderer() {
        return renderer;
    }

    public void setRenderer(String renderer) {
        this.renderer = renderer;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    @Override
    public String toString() {
        return "StatisticField{" +
                "typeId='" + typeId + '\'' +
                ", fieldId='" + fieldId + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", renderer='" + renderer + '\'' +
                ", isValid=" + isValid +
                ", isEnabled=" + isEnabled +
                '}';
    }
}
