package com.cs.calendarback.calendar.service;

import com.cs.calendarback.calendar.dto.ScheduleRequest;
import com.cs.calendarback.calendar.entity.Member;
import com.cs.calendarback.calendar.entity.Schedule;
import com.cs.calendarback.calendar.repository.MemberRepository;
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

    public List<Schedule> getSchedules() {
        return scheduleRepository.findAll();
    }

    @Transactional
    public Schedule create(ScheduleRequest request) {
        Member member = memberRepository.findById(request.memberId()).orElseThrow(() -> new CoreException(ErrorType.SCHEDULE_NOT_FOUND, request.memberId()));
        Schedule schedule = Schedule.create(request.title(), request.note(), request.startDateTime(), request.endDateTime(), member);
        return scheduleRepository.save(schedule);
    }

    public Schedule getSchedule(Long id) {
        return scheduleRepository.findById(id).orElseThrow(() -> new CoreException(ErrorType.SCHEDULE_NOT_FOUND, id));
    }

    public List<Schedule> getSearchDates(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return scheduleRepository.findSchedulesByDateRange(startDateTime, endDateTime);
    }

    @Transactional
    public Schedule update(Long id, ScheduleRequest request) {
        scheduleRepository.findById(id).orElseThrow(() -> new CoreException(ErrorType.SCHEDULE_NOT_FOUND, id));
        Member member = memberRepository.findById(request.memberId()).orElseThrow(() -> new CoreException(ErrorType.SCHEDULE_NOT_FOUND, request.memberId()));

        Schedule schedule = Schedule.update(id, request.title(), request.note(), request.startDateTime(), request.endDateTime(), member);
        return scheduleRepository.save(schedule);
    }

    public void delete(Long id) {
        scheduleRepository.delete(scheduleRepository.findById(id).orElseThrow(() -> new CoreException(ErrorType.SCHEDULE_NOT_FOUND, id)));
    }

}
