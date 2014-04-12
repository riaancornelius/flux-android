package com.riaancornelius.flux.jira.domain.sprint.burndown;

/**
 * @author Elsabe
 */
public class BurndownChange {
    private String key;
    private Boolean added;
    private StatC statC;
    private Column column;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Boolean getAdded() {
        return added;
    }

    public void setAdded(Boolean added) {
        this.added = added;
    }

    public StatC getStatC() {
        return statC;
    }

    public void setStatC(StatC statC) {
        this.statC = statC;
    }

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    @Override
    public String toString() {
        return "BurndownChange{" +
                "key='" + key + '\'' +
                ", added=" + added +
                ", statC=" + statC +
                ", column=" + column +
                '}';
    }

    public static class StatC {
        private Double newValue;

        public Double getNewValue() {
            return newValue;
        }

        public void setNewValue(Double newValue) {
            this.newValue = newValue;
        }

        @Override
        public String toString() {
            return "StatC{" +
                    "newValue=" + newValue +
                    '}';
        }
    }

    public static class Column {
        private Boolean notDone;
        private Boolean done;
        private String newStatus;

        public Boolean getNotDone() {
            return notDone;
        }

        public void setNotDone(Boolean notDone) {
            this.notDone = notDone;
        }

        public Boolean getDone() {
            return done;
        }

        public void setDone(Boolean done) {
            this.done = done;
        }

        public String getNewStatus() {
            return newStatus;
        }

        public void setNewStatus(String newStatus) {
            this.newStatus = newStatus;
        }

        @Override
        public String toString() {
            return "Column{" +
                    "notDone=" + notDone +
                    ", done=" + done +
                    ", newStatus='" + newStatus + '\'' +
                    '}';
        }
    }
}
