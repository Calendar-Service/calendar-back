package com.cs.calendarback.calendar.repository;

import com.cs.calendarback.calendar.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
