package com.cs.calendarback.calendar.controller;


import com.cs.calendarback.calendar.dto.*;
import com.cs.calendarback.calendar.entity.Schedule;
import com.cs.calendarback.calendar.service.ScheduleService;
import com.cs.calendarback.config.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Operation(summary = "모든 일정 조회", description = "등록된 모든 일정를 조회합니다.")
    @GetMapping
    public ResponseEntity<Result<List<ScheduleResponse>>> getSchedules(@RequestBody @Valid ScheduleRequest request) {
        List<Schedule> schedules;
        if (request.categoryId() == null) {
            schedules = scheduleService.getSchedules(request.memberId());
        } else {
            schedules = scheduleService.getSchedulesAndCategory(request.memberId(), request.categoryId());
        }
        List<ScheduleResponse> response = schedules.stream().map(ScheduleResponse::from).toList();
        return ResponseEntity.ok(new Result<>(response.size(), response));
    }

    @Operation(summary = "월별 일정 조회", description = "등록된 월별 일정을 조회합니다.")
    @GetMapping("/{year}/{month}")
    public ResponseEntity<Result<List<ScheduleResponse>>> getSchedulesByYearANdMonth(
            @PathVariable int year,
            @PathVariable int month,
            @RequestBody @Valid ScheduleRequest request) {

        CreateDateRange createDateRange = CreateDateRange.monthOf(year, month);

        List<Schedule> schedules;
        if (request.categoryId() == null) {
            schedules = scheduleService.getSchedulesByYearAndMonth(createDateRange.startDateTime(), createDateRange.endDateTime(), request.memberId());
        } else {
            schedules = scheduleService.getSchedulesByYearAndMonthAndCategory(createDateRange.startDateTime(), createDateRange.endDateTime(), request.memberId(), request.categoryId());
        }
        List<ScheduleResponse> response = schedules.stream().map(ScheduleResponse::from).toList();
        return ResponseEntity.ok(new Result<>(response.size(), response));
    }

    @Operation(summary = "특정 날짜 일정 조회", description = "특정 날짜에 해당하는 일정을 조회합니다.")
    @GetMapping("/{year}/{month}/dates")
    public ResponseEntity<Result<List<ScheduleResponse>>> getSchedulesByDate(
            @PathVariable int year,
            @PathVariable int month,
            @RequestParam("searchDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate searchDate,
            @RequestBody @Valid ScheduleRequest request) {

        CreateDateRange createDateRange = CreateDateRange.dateOf(searchDate);

        List<Schedule> schedules;
        if (request.categoryId() == null) {
            schedules = scheduleService.getSearchDates(createDateRange.startDateTime(), createDateRange.endDateTime(), request.memberId());
        } else {
            schedules = scheduleService.getSearchDatesAndCategory(createDateRange.startDateTime(), createDateRange.endDateTime(), request.memberId(), request.categoryId());
        }

        List<ScheduleResponse> response = schedules.stream().map(ScheduleResponse::from).toList();
        return ResponseEntity.ok(new Result<>(response.size(), response));
    }

    @Operation(summary = "일정을 등록", description = "새로운 일정을 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "일정 등록 성공"),
    })
    @PostMapping
    public ResponseEntity<ScheduleResponse> create(@RequestBody @Valid ScheduleCreateRequest request) {
        Schedule schedule = scheduleService.create(request);
        return ResponseEntity.ok(ScheduleResponse.from(schedule));
    }

    @Operation(summary = "일정을 상세 조회", description = "일정 ID로 상세 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "일정 정보 조회 성공",
                    content = @Content(schema = @Schema(implementation = ScheduleResponse.class))),
            @ApiResponse(responseCode = "404", description = "일정을 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponse> getSchedule(@PathVariable("id") Long id) {
        Schedule schedule = scheduleService.getSchedule(id);
        return ResponseEntity.ok(ScheduleResponse.from(schedule));
    }

    @Operation(summary = "일정 수정", description = "등록된 일정 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "일정 정보 수정 성공",
                    content = @Content(schema = @Schema(implementation = ScheduleResponse.class))),
            @ApiResponse(responseCode = "404", description = "일정을 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponse> update(@PathVariable("id") Long id, @RequestBody @Valid ScheduleCreateRequest request) {
        Schedule updated = scheduleService.update(id, request);
        return ResponseEntity.ok(ScheduleResponse.from(updated));
    }

    @Operation(summary = "일정 삭제", description = "등록된 일정을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "일정 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "일정을 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        scheduleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}