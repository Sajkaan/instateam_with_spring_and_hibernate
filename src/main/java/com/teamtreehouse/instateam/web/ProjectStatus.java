package com.teamtreehouse.instateam.web;

public enum ProjectStatus {
    ACTIVE("Active", "#72c38d", "active"),
    ARCHIVED("Archived", "bbbab9", "archived"),
    NOT_STARTED("Not started", "white", "not-started");

    private final String statusDescription;
    private final String color;
    private final String styleClass;

    ProjectStatus(String statusDescription, String color, String styleClass) {
        this.statusDescription = statusDescription;
        this.color = color;
        this.styleClass = styleClass;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public String getColor() {
        return color;
    }

    public String getStyleClass() {
        return styleClass;
    }
}
