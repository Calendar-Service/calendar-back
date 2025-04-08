package com.cs.calendarback.calendar.service;

import com.cs.calendarback.calendar.entity.Category;
import com.cs.calendarback.calendar.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getCategories(Long memberId) {
        return categoryRepository.findByMemberId(memberId);
    }
}
