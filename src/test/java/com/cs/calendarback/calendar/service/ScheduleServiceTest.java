package com.cs.calendarback.calendar.service;

import com.cs.calendarback.calendar.entity.Category;
import com.cs.calendarback.calendar.entity.Schedule;
import com.cs.calendarback.calendar.entity.enums.DefaultCategory;
import com.cs.calendarback.calendar.repository.ScheduleRepository;
import com.cs.calendarback.member.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceTest {

    @InjectMocks
    private ScheduleService scheduleService;

    @Mock
    private ScheduleRepository scheduleRepository;

    private Member member;
    private List<Schedule> schedules = new ArrayList<>();
    private List<Schedule> schedulesAndCategory = new ArrayList<>();
    private Category category;

    @BeforeEach
    void setUp() {
        member = Member.udpate(1L, "이승원", 12L, "ee@naver.com");

        category = Category.update(1L, DefaultCategory.FAMILY.getName(), member);

        schedules = List.of(
                Schedule.update(1L, "일정1", "일정 테스트1", LocalDateTime.now(), LocalDateTime.now(), member),
                Schedule.update(2L, "일정2", "일정 테스트2", LocalDateTime.now(), LocalDateTime.now(), member)
        );

        schedulesAndCategory = List.of(
                Schedule.update(1L, "일정1", "일정 테스트1", LocalDateTime.now(), LocalDateTime.now(), member, category),
                Schedule.update(2L, "일정2", "일정 테스트2", LocalDateTime.now(), LocalDateTime.now(), member, category)
        );
    }

    @Test
    void getSchedules() {
        // given
        doReturn(schedules).when(scheduleRepository).findByMemberId(member.getId());

        // when
        List<Schedule> result = scheduleService.getSchedules(member.getId());

        // then
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(2, result.size()),
                () -> assertEquals("일정1", result.get(0).getTitle()),
                () -> assertEquals(result.get(0).getMember().getId(), member.getId()),
                () -> assertEquals(result.get(0).getMember().getAuthId(), member.getAuthId())
        );
    }

    @Test
    void getSchedulesAndCategory() {
        // given
        doReturn(schedulesAndCategory).when(scheduleRepository).findByMemberIdAndCategoryId(member.getId(), category.getId());

        // when
        List<Schedule> result = scheduleService.getSchedulesAndCategory(member.getId(), category.getId());

        // then
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(2, result.size()),
                () -> assertEquals("일정1", result.get(0).getTitle()),
                () -> assertEquals(result.get(0).getMember().getId(), member.getId()),
                () -> assertEquals(result.get(0).getMember().getAuthId(), member.getAuthId()),
                () -> assertEquals("가족", result.get(0).getCategory().getName())
        );
    }

    @Test
    void getSchedulesByYearAndMonth() {
    }

    @Test
    void getSchedulesByYearAndMonthAndCategory() {
    }

    @Test
    void getSearchDates() {
    }

    @Test
    void getSearchDatesAndCategory() {
    }

    @Test
    void create() {
    }

    @Test
    void getSchedule() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}