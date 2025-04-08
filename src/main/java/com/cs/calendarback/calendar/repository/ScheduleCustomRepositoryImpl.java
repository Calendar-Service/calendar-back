package com.cs.calendarback.calendar.repository;

import com.cs.calendarback.calendar.entity.Schedule;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.cs.calendarback.calendar.entity.QSchedule.schedule;

@Repository
@RequiredArgsConstructor
public class ScheduleCustomRepositoryImpl implements ScheduleCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Schedule> findSchedulesByDateRange(LocalDateTime startDateTime, LocalDateTime endDateTime, Long memberId, Long categoryId) {

        return jpaQueryFactory
                .selectFrom(schedule)
                .leftJoin(schedule.member).fetchJoin()
                .leftJoin(schedule.category).fetchJoin()
                .where(
                        startDateTimeGoe(startDateTime),
                        endDateTimeLoe(endDateTime),
                        memberIdEq(memberId),
                        categoryIdEq(categoryId)
                ).fetch();

    }

    @Override
    public List<Schedule> findSchedules(Long memberId, Long categoryId) {
        return jpaQueryFactory
                .selectFrom(schedule)
                .leftJoin(schedule.member).fetchJoin()
                .leftJoin(schedule.category).fetchJoin()
                .where(
                        memberIdEq(memberId),
                        categoryIdEq(categoryId)
                ).fetch();
    }

    private BooleanExpression memberIdEq(Long memberId) {
        return memberId == null ? null : schedule.member.id.eq(memberId);
    }

    private BooleanExpression startDateTimeGoe(LocalDateTime startDateTime) {
        return startDateTime == null ? null : schedule.startDateTime.goe(startDateTime);
    }

    private BooleanExpression endDateTimeLoe(LocalDateTime endDateTime) {
        return endDateTime == null ? null : schedule.endDateTime.loe(endDateTime);
    }

    private BooleanExpression categoryIdEq(Long categoryId) {
        return categoryId == null ? null : schedule.category.id.eq(categoryId);
    }
}
