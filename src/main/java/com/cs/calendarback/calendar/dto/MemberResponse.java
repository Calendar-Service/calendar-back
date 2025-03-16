package com.cs.calendarback.calendar.dto;

import com.cs.calendarback.calendar.entity.Member;

import java.util.List;

public record MemberResponse(Long id, String name, String email) {

    public static MemberResponse from(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getName(),
                member.getEmail()
        );
    }
    public static List<MemberResponse> from(List<Member> members) {
        return members.stream()
                .map(MemberResponse::from)
                .toList();
    }
}
