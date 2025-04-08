package com.cs.calendarback.calendar.controller;

import com.cs.calendarback.calendar.dto.CategoryResponse;
import com.cs.calendarback.calendar.dto.Result;
import com.cs.calendarback.calendar.entity.Category;
import com.cs.calendarback.calendar.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "회원의 카테고리 조회", description = "회원의 모든 카테고리를 조회합니다.")
    @GetMapping
    public ResponseEntity<Result<List<CategoryResponse>>> getSchedules(@RequestParam Long memberId) {
        List<Category> categories = categoryService.getCategories(memberId);
        List<CategoryResponse> response = categories.stream().map(CategoryResponse::from).toList();
        return ResponseEntity.ok(new Result<>(response.size(), response));
    }
}
