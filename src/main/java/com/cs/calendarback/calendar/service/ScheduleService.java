package com.cs.calendarback.calendar.service;

import com.cs.calendarback.calendar.dto.ScheduleCreateRequest;
import com.cs.calendarback.calendar.entity.Category;
import com.cs.calendarback.calendar.repository.CategoryRepository;
import com.cs.calendarback.member.entity.Member;
import com.cs.calendarback.calendar.entity.Schedule;
import com.cs.calendarback.member.repository.MemberRepository;
import com.cs.calendarback.calendar.repository.ScheduleRepository;
import com.cs.calendarback.config.exception.CoreException;
import com.cs.calendarback.config.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final MemberRepository memberRepository;

    private final CategoryRepository categoryRepository;

    public List<Schedule> getSchedules(Long memberId) {
        return scheduleRepository.findByMemberId(memberId);
    }

    public List<Schedule> getSchedulesAndCategory(Long memberId, Long categoryId) {
        return scheduleRepository.findByMemberIdAndCategoryId(memberId, categoryId);
    }

    public List<Schedule> getSchedulesByYearAndMonth(int year, int month, Long memberId) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDateTime startDateTime = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endDateTime = yearMonth.atEndOfMonth().atTime(23, 59, 59);
        return scheduleRepository.findSchedulesByDateRange(startDateTime, endDateTime, memberId);
    }

    public List<Schedule> getSchedulesByYearAndMonthAndCategory(int year, int month, Long memberId, Long categoryId) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDateTime startDateTime = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endDateTime = yearMonth.atEndOfMonth().atTime(23, 59, 59);
        return scheduleRepository.findSchedulesByDateRangeAndCategoryId(startDateTime, endDateTime, memberId, categoryId);
    }

    public List<Schedule> getSearchDates(LocalDate searchDate, Long memberId, Long categoryId) {
        LocalDateTime startDateTime = searchDate.atStartOfDay();
        LocalDateTime endDateTime = searchDate.atTime(23, 59, 59);
        return scheduleRepository.findSchedulesByDateRange(startDateTime, endDateTime, memberId);
    }

    public List<Schedule> getSearchDatesAndCategory(LocalDate searchDate, Long memberId, Long categoryId) {
        LocalDateTime startDateTime = searchDate.atStartOfDay();
        LocalDateTime endDateTime = searchDate.atTime(23, 59, 59);
        return scheduleRepository.findSchedulesByDateRangeAndCategoryId(startDateTime, endDateTime, memberId, categoryId);
    }

    @Transactional
    public Schedule create(ScheduleCreateRequest request) {

        Member member = memberRepository.findById(request.memberId()).orElseThrow(()-> new CoreException(ErrorType.MEMBER_NOT_FOUND, request.memberId()));
        Category category =categoryRepository.findById(request.categoryId()).orElseThrow(()->new CoreException(ErrorType.CATEGORY_NOT_FOUND, request.categoryId()));

        Schedule schedule = Schedule.create(request.title(), request.note(), request.toStartDateTime(), request.toEndDateTime(), member, category);
        return scheduleRepository.save(schedule);
    }

    public Schedule getSchedule(Long id) {
        return scheduleRepository.findById(id).orElseThrow(() -> new CoreException(ErrorType.SCHEDULE_NOT_FOUND, id));
    }

    @Transactional
    public Schedule update(Long id, ScheduleCreateRequest request) {
        Member member = memberRepository.findById(request.memberId()).orElseThrow(()-> new CoreException(ErrorType.MEMBER_NOT_FOUND, request.memberId()));
        Category category =categoryRepository.findById(request.categoryId()).orElseThrow(()->new CoreException(ErrorType.CATEGORY_NOT_FOUND, request.categoryId()));
        scheduleRepository.findById(id).orElseThrow(() -> new CoreException(ErrorType.SCHEDULE_NOT_FOUND, id));
        Schedule schedule = Schedule.update(id, request.title(), request.note(), request.toStartDateTime(), request.toEndDateTime(), member, category);
        return scheduleRepository.save(schedule);
    }

    @Transactional
    public void delete(Long id) {
        scheduleRepository.delete(scheduleRepository.findById(id).orElseThrow(() -> new CoreException(ErrorType.SCHEDULE_NOT_FOUND, id)));
    }

}
