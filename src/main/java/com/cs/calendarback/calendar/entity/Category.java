package com.cs.calendarback.calendar.entity;

import com.cs.calendarback.calendar.entity.enums.CategoryItem;
import com.cs.calendarback.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "category")
    private List<Schedule> schedules = new ArrayList<>();

    public Category(Long id, String name, Member member) {
        this.id = id;
        this.name = name;
        this.member = member;
    }

    public static Category create(String name, Member member) {
        return new Category(null, name, member);
    }

    public static Category update(Long id, String name, Member member) {
        return new Category(id, name, member);
    }

    public static Long getIdOrNullIfSchedule(Category category) {
        return CategoryItem.SCHEDULE.getName().equals(category.getName()) ? null : category.getId();
    }
}
