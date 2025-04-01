package com.cs.calendarback.calendar.entity.enums;

public enum DefaultCategory {
    FRIEND("친구"),
    FAMILY("가족");

    private final String name;

    DefaultCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
