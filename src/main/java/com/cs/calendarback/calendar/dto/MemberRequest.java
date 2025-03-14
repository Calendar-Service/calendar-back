package com.cs.calendarback.calendar.dto;

import com.cs.calendarback.calendar.entity.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record MemberRequest(
        @NotBlank(message = "사용자 이름은 필수 입력 값입니다.")
        String name,

        @NotBlank(message = "사용자 ID는 필수 입력 값입니다.")
        @Email
        String email,

        @NotBlank(message = "패스워드는 필수 입력 값입니다.")
        String password,

        Role role
) {
}
