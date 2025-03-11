package com.cs.calendarback.calendar.service;

import com.cs.calendarback.calendar.dto.MemberRequest;
import com.cs.calendarback.calendar.entity.Member;
import com.cs.calendarback.calendar.repository.MemberRepository;
import com.cs.calendarback.config.exception.CoreException;
import com.cs.calendarback.config.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public List<Member> getMembers() {
        return memberRepository.findAll();
    }

    @Transactional
    public Member create(MemberRequest request) {
        Optional<Member> optionalMember = memberRepository.findByEmail(request.email());
        if (optionalMember.isPresent()) {
            throw new CoreException(ErrorType.EMAIL_ALREADY_EXISTS, request.email());
        }
        Member member = Member.create(request.name(), request.email(), request.password());
        return memberRepository.save(member);
    }

    public Member getMember(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new CoreException(ErrorType.MEMBER_NOT_FOUND, id));
    }
}
