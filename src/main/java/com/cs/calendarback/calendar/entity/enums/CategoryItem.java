package com.cs.calendarback.calendar.entity.enums;

public enum CategoryItem {
    SCHEDULE("알정"),
    FRIEND("친구"),
    FAMILY("가족");

    private final String name;

    CategoryItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
