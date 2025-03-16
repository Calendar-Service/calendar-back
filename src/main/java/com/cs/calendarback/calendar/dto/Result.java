package com.cs.calendarback.calendar.dto;

public record Result<T>(int count, T items) {
}
