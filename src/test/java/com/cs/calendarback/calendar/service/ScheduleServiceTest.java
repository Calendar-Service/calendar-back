package com.cs.calendarback.calendar.service;

import com.cs.calendarback.calendar.dto.CreateDateRange;
import com.cs.calendarback.calendar.dto.ScheduleCreateRequest;
import com.cs.calendarback.calendar.entity.Category;
import com.cs.calendarback.calendar.entity.Schedule;
import com.cs.calendarback.calendar.entity.enums.DefaultCategory;
import com.cs.calendarback.calendar.repository.CategoryRepository;
import com.cs.calendarback.calendar.repository.ScheduleRepository;
import com.cs.calendarback.member.entity.Member;
import com.cs.calendarback.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceTest {

    @InjectMocks
    private ScheduleService scheduleService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ScheduleRepository scheduleRepository;

    private int year;
    private int month;
    private Member member;
    private List<Schedule> schedules = new ArrayList<>();
    private List<Schedule> schedulesAndCategory = new ArrayList<>();
    private List<Category> categories;
    private Category category;
    private LocalDate seachDate;
    private Schedule schedule;
    private Schedule scheduleUpdate;

    private ScheduleCreateRequest request;
    private ScheduleCreateRequest updateRequest;

    @BeforeEach
    void setUp() {
        year = LocalDateTime.now().getYear();
        month = LocalDateTime.now().getMonthValue();
        seachDate = LocalDate.now();

        member = Member.update(1L, "이승원", 12L, "ee@naver.com");

        categories = List.of(
                Category.update(1L, DefaultCategory.FAMILY.getName(), member),
                Category.update(2L, DefaultCategory.FRIEND.getName(), member)
        );

        schedules = List.of(
                Schedule.update(1L, "일정1", "일정 테스트1", LocalDateTime.now(), LocalDateTime.now(), member, categories.get(0)),
                Schedule.update(2L, "일정2", "일정 테스트2", LocalDateTime.now(), LocalDateTime.now(), member, categories.get(1))
        );

        schedulesAndCategory = List.of(
                Schedule.update(1L, "일정1", "일정 테스트1", LocalDateTime.now(), LocalDateTime.now(), member, categories.get(0))
        );

        category = Category.update(1L, DefaultCategory.FAMILY.getName(), member);
        schedule = Schedule.update(1L, "일정1", "일정 테스트1", LocalDateTime.now(), LocalDateTime.now(), member, categories.get(0));
        scheduleUpdate = Schedule.update(1L, "일정 수정", "일정 노트 수정", LocalDateTime.now(), LocalDateTime.now(), member, categories.get(0));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        request = new ScheduleCreateRequest(
                "일정1",
                "일정 테스트1",
                LocalDateTime.now().format(formatter), // LocalDateTime -> String 변환
                LocalDateTime.now().plusHours(1).format(formatter), // LocalDateTime -> String 변환
                member.getId(),
                null
        );

        updateRequest = new ScheduleCreateRequest(
                "일정 수정",
                "일정 노트 수정",
                LocalDateTime.now().format(formatter), // LocalDateTime -> String 변환
                LocalDateTime.now().plusHours(1).format(formatter), // LocalDateTime -> String 변환
                member.getId(),
                1L
        );
    }


    @Test
    @DisplayName("모든 스케줄 조회")
    void getSchedules() {
        // given
        doReturn(schedulesAndCategory).when(scheduleRepository).findSchedules(member.getId(), categories.get(0).getId());

        // when
        List<Schedule> result = scheduleService.getSchedules(member.getId(), categories.get(0).getId());

        // then
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(1, result.size()),
                () -> assertEquals("일정1", result.get(0).getTitle()),
                () -> assertEquals(result.get(0).getMember().getId(), member.getId()),
                () -> assertEquals(result.get(0).getMember().getAuthId(), member.getAuthId()),
                () -> assertEquals(result.get(0).getCategory().getName(), categories.get(0).getName())
        );
    }


    @Test
    @DisplayName("년월 스케줄 조회")
    void getSchedulesByYearAndMonth() {
        // given
        CreateDateRange createDateRange = CreateDateRange.monthOf(year, month);
        doReturn(schedulesAndCategory).when(scheduleRepository).findSchedulesByDateRange(createDateRange.startDateTime(), createDateRange.endDateTime(), member.getId(), categories.get(0).getId());

        // when
        List<Schedule> result = scheduleService.getSchedulesByDateRange(createDateRange.startDateTime(), createDateRange.endDateTime(), member.getId(), categories.get(0).getId());

        // then
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(1, result.size()),
                () -> assertEquals("일정1", result.get(0).getTitle()),
                () -> assertEquals(result.get(0).getMember().getId(), member.getId()),
                () -> assertEquals(result.get(0).getMember().getAuthId(), member.getAuthId()),
                () -> assertEquals(result.get(0).getStartDateTime().getYear(), year),
                () -> assertEquals(result.get(result.size() - 1).getStartDateTime().getMonthValue(), month),
                () -> assertEquals(result.get(0).getCategory().getName(), categories.get(0).getName())
        );


    }

    @Test
    @DisplayName("특정날짜로 스케줄 조회")
    void getSearchDates() {
        // given
        CreateDateRange createDateRange = CreateDateRange.dateOf(seachDate);
        doReturn(schedulesAndCategory).when(scheduleRepository).findSchedulesByDateRange(createDateRange.startDateTime(), createDateRange.endDateTime(), member.getId(), categories.get(0).getId());

        // when
        List<Schedule> result = scheduleService.getSchedulesByDateRange(createDateRange.startDateTime(), createDateRange.endDateTime(), member.getId(), categories.get(0).getId());
        // then
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(1, result.size()),
                () -> assertEquals("일정1", result.get(0).getTitle()),
                () -> assertEquals(result.get(0).getMember().getId(), member.getId()),
                () -> assertEquals(result.get(0).getMember().getAuthId(), member.getAuthId()),
                () -> assertEquals(result.get(0).getStartDateTime().getYear(), year),
                () -> assertEquals(result.get(result.size() - 1).getStartDateTime().getMonthValue(), month),
                () -> assertEquals(result.get(0).getCategory().getName(), categories.get(0).getName())
        );
    }

    @Test
    @DisplayName("스케줄 생성")
    void create() {
        // given
        doReturn(Optional.of(member)).when(memberRepository).findById(member.getId());
        doReturn(Optional.of(category)).when(categoryRepository).findById(category.getId());
        doReturn(schedule).when(scheduleRepository).save(any(Schedule.class));

        // when
        Schedule result = scheduleService.create(request);

        // then
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(schedule.getTitle(), result.getTitle()),
                () -> assertEquals(schedule.getCategory().getName(), result.getCategory().getName())
        );
    }

    @Test
    @DisplayName("모든 스케줄 정보 조회")
    void getSchedule() {
        // given
        doReturn(Optional.of(schedule)).when(scheduleRepository).findById(member.getId());

        // when
        Schedule result = scheduleService.getSchedule(member.getId());

        // then
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals("일정1", result.getTitle()),
                () -> assertEquals(result.getMember().getId(), member.getId()),
                () -> assertEquals(result.getMember().getAuthId(), member.getAuthId()),
                () -> assertEquals(result.getStartDateTime().getYear(), year),
                () -> assertEquals(result.getCategory().getName(), categories.get(0).getName())
        );
    }

    @Test
    @DisplayName("스케줄 수정")
    void update() {
        // given
        doReturn(Optional.of(member)).when(memberRepository).findById(member.getId());
        doReturn(Optional.of(category)).when(categoryRepository).findById(category.getId());
        doReturn(Optional.of(scheduleUpdate)).when(scheduleRepository).findById(schedule.getId());
        doReturn(scheduleUpdate).when(scheduleRepository).save(any(Schedule.class));

        // when
        Schedule result = scheduleService.update(schedule.getId(), updateRequest);

        // then
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(scheduleUpdate.getTitle(), result.getTitle()),
                () -> assertEquals(scheduleUpdate.getNote(), result.getNote())
        );
    }

    @Test
    @DisplayName("스케줄 삭제")
    void delete() {
        // given
        doReturn(Optional.of(schedule)).when(scheduleRepository).findById(schedule.getId());

        // when
        scheduleService.delete(schedule.getId());

        // then
        verify(scheduleRepository).delete(schedule);
    }
}