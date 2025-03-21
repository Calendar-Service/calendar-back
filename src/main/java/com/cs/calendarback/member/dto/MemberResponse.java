package com.cs.calendarback.member.dto;

import com.cs.calendarback.member.entity.Member;

import java.util.List;

public record MemberResponse(Long id, String nickName, Long authId, String email) {

    public static MemberResponse from(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getNickName(),
                member.getAuthId(),
                member.getEmail()
        );
    }
    public static List<MemberResponse> from(List<Member> members) {
        return members.stream()
                .map(MemberResponse::from)
                .toList();
    }
}
