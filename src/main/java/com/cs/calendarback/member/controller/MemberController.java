package com.cs.calendarback.member.controller;


import com.cs.calendarback.member.entity.Member;
import com.cs.calendarback.config.exception.ErrorResponse;
import com.cs.calendarback.member.dto.MemberRequest;
import com.cs.calendarback.member.dto.MemberResponse;
import com.cs.calendarback.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "모든 사용자 조회", description = "등록된 모든 사용자를 조회합니다.")
    @GetMapping
    public ResponseEntity<List<MemberResponse>> getMembers() {
        List<Member> members = memberService.getMembers();
        return ResponseEntity.ok(MemberResponse.from(members));
    }

    @Operation(summary = "사용자를 상세 조회", description = "사용자 ID로 상세 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "사용자 정보 조회 성공",
                    content = @Content(schema = @Schema(implementation = MemberResponse.class))),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> getMember(@PathVariable("id") Long id) {
        Member member = memberService.getMember(id);
        return ResponseEntity.ok(MemberResponse.from(member));
    }

}