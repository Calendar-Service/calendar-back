package com.cs.calendarback.calendar.service;

import com.cs.calendarback.calendar.dto.CustomUserDetails;
import com.cs.calendarback.calendar.entity.Member;
import com.cs.calendarback.calendar.repository.MemberRepository;
import com.cs.calendarback.config.exception.CoreException;
import com.cs.calendarback.config.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new CoreException(ErrorType.MEMBER_NOT_FOUND, email));
        if (member != null) {
            return new CustomUserDetails(member);
        }
        return null;
    }
}
