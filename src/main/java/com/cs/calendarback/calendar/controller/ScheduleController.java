package com.cs.calendarback.calendar.controller;


import com.cs.calendarback.calendar.dto.ScheduleRequest;
import com.cs.calendarback.calendar.dto.ScheduleResponse;
import com.cs.calendarback.calendar.dto.SearchDate;
import com.cs.calendarback.calendar.entity.Schedule;
import com.cs.calendarback.calendar.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/Schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping
    public ResponseEntity<List<ScheduleResponse>> getSchedules() {
        List<Schedule> Schedules = scheduleService.getSchedules();
        return ResponseEntity.ok(ScheduleResponse.from(Schedules));
    }

    @PostMapping
    public ResponseEntity<ScheduleResponse> create(@RequestBody ScheduleRequest request) {
        Schedule calendar = scheduleService.create(request);
        return ResponseEntity.ok(ScheduleResponse.from(calendar));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponse> getSchedule(@PathVariable("id") Long id) {
        Schedule calendar = scheduleService.getSchedule(id);
        return ResponseEntity.ok(ScheduleResponse.from(calendar));
    }

    @GetMapping("/dates")
    public ResponseEntity<List<ScheduleResponse>> getSearchDates(@RequestBody SearchDate request) {
        List<Schedule> Schedules = scheduleService.getSearchDates(request.getStartOfDay(), request.getEndOfDay());
        return ResponseEntity.ok(ScheduleResponse.from(Schedules));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponse> update(@PathVariable("id") Long id, @RequestBody ScheduleRequest request) {
        Schedule updated = scheduleService.update(id, request);
        return ResponseEntity.ok(ScheduleResponse.from(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        scheduleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}