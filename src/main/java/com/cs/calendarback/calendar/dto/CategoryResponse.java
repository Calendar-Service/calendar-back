package com.cs.calendarback.calendar.dto;

import com.cs.calendarback.calendar.entity.Category;

public record CategoryResponse(Long categoryId, String categoryName) {

    public static CategoryResponse from(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName()
        );
    }
}
