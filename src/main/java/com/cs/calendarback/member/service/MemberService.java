package com.cs.calendarback.member.service;

import com.cs.calendarback.login.dto.KakaoMemberResponse;
import com.cs.calendarback.member.entity.Member;
import com.cs.calendarback.config.exception.CoreException;
import com.cs.calendarback.config.exception.ErrorType;
import com.cs.calendarback.member.repository.MemberRepository;
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

    public Member getMember(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new CoreException(ErrorType.MEMBER_NOT_FOUND, id));
    }

    @Transactional
    public Long createKakaoMember(KakaoMemberResponse memberInfo) {
        Optional<Member> optionalMember = memberRepository.findByAuthId(memberInfo.getId());
        if (optionalMember.isEmpty()) {
            Member member = Member.create(memberInfo.getKakaoAccount().getProfile().getNickName(), memberInfo.getId(), null);
            member = memberRepository.save(member);
            return member.getId();
        }
        return optionalMember.get().getId();
    }
}
