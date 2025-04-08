package com.cs.calendarback.calendar.service;

import com.cs.calendarback.calendar.dto.ScheduleCreateRequest;
import com.cs.calendarback.calendar.entity.Category;
import com.cs.calendarback.calendar.repository.CategoryRepository;
import com.cs.calendarback.calendar.repository.ScheduleCustomRepository;
import com.cs.calendarback.member.entity.Member;
import com.cs.calendarback.calendar.entity.Schedule;
import com.cs.calendarback.member.repository.MemberRepository;
import com.cs.calendarback.calendar.repository.ScheduleRepository;
import com.cs.calendarback.config.exception.CoreException;
import com.cs.calendarback.config.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final MemberRepository memberRepository;

    private final CategoryRepository categoryRepository;

    public List<Schedule> getSchedules(Long memberId, Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new CoreException(ErrorType.CATEGORY_NOT_FOUND, categoryId));
        Long resolvedCategoryId = Category.resolveCategoryId(category);

        return scheduleRepository.findSchedules(memberId, resolvedCategoryId);
    }

    public List<Schedule> getSchedulesByDateRange(LocalDateTime startDateTime, LocalDateTime endDateTime, Long memberId, Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new CoreException(ErrorType.CATEGORY_NOT_FOUND, categoryId));
        Long resolvedCategoryId = Category.resolveCategoryId(category);

        return scheduleRepository.findSchedulesByDateRange(startDateTime, endDateTime, memberId, resolvedCategoryId);
    }

    @Transactional
    public Schedule create(ScheduleCreateRequest request) {

        Member member = memberRepository.findById(request.memberId()).orElseThrow(() -> new CoreException(ErrorType.MEMBER_NOT_FOUND, request.memberId()));
        Category category = categoryRepository.findById(request.categoryId()).orElseThrow(() -> new CoreException(ErrorType.CATEGORY_NOT_FOUND, request.categoryId()));

        Schedule schedule = Schedule.create(request.title(), request.note(), request.toStartDateTime(), request.toEndDateTime(), member, category);
        return scheduleRepository.save(schedule);
    }

    public Schedule getSchedule(Long id) {
        return scheduleRepository.findById(id).orElseThrow(() -> new CoreException(ErrorType.SCHEDULE_NOT_FOUND, id));
    }

    @Transactional
    public Schedule update(Long id, ScheduleCreateRequest request) {
        Member member = memberRepository.findById(request.memberId()).orElseThrow(() -> new CoreException(ErrorType.MEMBER_NOT_FOUND, request.memberId()));
        Category category = categoryRepository.findById(request.categoryId()).orElseThrow(() -> new CoreException(ErrorType.CATEGORY_NOT_FOUND, request.categoryId()));
        scheduleRepository.findById(id).orElseThrow(() -> new CoreException(ErrorType.SCHEDULE_NOT_FOUND, id));
        Schedule schedule = Schedule.update(id, request.title(), request.note(), request.toStartDateTime(), request.toEndDateTime(), member, category);
        return scheduleRepository.save(schedule);
    }

    @Transactional
    public void delete(Long id) {
        scheduleRepository.delete(scheduleRepository.findById(id).orElseThrow(() -> new CoreException(ErrorType.SCHEDULE_NOT_FOUND, id)));
    }

}
