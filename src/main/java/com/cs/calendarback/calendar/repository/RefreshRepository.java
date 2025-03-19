package com.cs.calendarback.calendar.repository;

import com.cs.calendarback.calendar.entity.Refresh;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshRepository extends JpaRepository<Refresh, Long> {

    Boolean existsByRefresh(String refresh);

    void deleteByRefresh(String refresh);
}
