package com.cs.calendarback.calendar.entity;

import com.cs.calendarback.calendar.entity.common.BaseEntity;
import com.cs.calendarback.calendar.entity.enums.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "member")
    private List<Schedule> schedules = new ArrayList<>();

    public static Member create(String name, String email, String password, Role role) {
        return new Member(null, name, email, encodePassword(password), role, new ArrayList<>());
    }

    public static Member ofEmailAndRole(String email, Role role) {
        return new Member(null, "tempName", email, "tempPassword", role, new ArrayList<>());
    }

    private static String encodePassword(String rawPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(rawPassword);
    }

}
