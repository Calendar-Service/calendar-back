package com.cs.calendarback.calendar.controller;


import com.cs.calendarback.calendar.dto.CalendarRequest;
import com.cs.calendarback.calendar.dto.CalendarResponse;
import com.cs.calendarback.calendar.dto.SearchDate;
import com.cs.calendarback.calendar.entity.Calendar;
import com.cs.calendarback.calendar.service.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/calendars")
public class CalendarController {

    private final CalendarService calendarService;

    @GetMapping
    public ResponseEntity<List<CalendarResponse>> getCalendars() {
        List<Calendar> calendars = calendarService.getCalendars();
        return ResponseEntity.ok(CalendarResponse.from(calendars));
    }

    @PostMapping
    public ResponseEntity<CalendarResponse> create(@RequestBody CalendarRequest request) {
        Calendar calendar = calendarService.create(request);
        return ResponseEntity.ok(CalendarResponse.from(calendar));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CalendarResponse> getCalendar(@PathVariable("id") Long id) {
        Calendar calendar = calendarService.getCalendar(id);
        return ResponseEntity.ok(CalendarResponse.from(calendar));
    }

    @GetMapping("/dates")
    public ResponseEntity<List<CalendarResponse>> getSearchDates(@RequestBody SearchDate request) {
        List<Calendar> calendars = calendarService.getSearchDates(request.getStartOfDay(), request.getEndOfDay());
        return ResponseEntity.ok(CalendarResponse.from(calendars));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CalendarResponse> update(@PathVariable("id") Long id, @RequestBody CalendarRequest request) {
        Calendar updated = calendarService.update(id, request);
        return ResponseEntity.ok(CalendarResponse.from(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        calendarService.delete(id);
        return ResponseEntity.noContent().build();
    }
}