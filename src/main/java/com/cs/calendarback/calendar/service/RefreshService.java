package com.cs.calendarback.calendar.service;

import com.cs.calendarback.calendar.entity.Refresh;
import com.cs.calendarback.calendar.repository.RefreshRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class RefreshService {
    private final RefreshRepository refreshRepository;

    @Transactional
    public void refreshCreate(String email, String refresh, Long expiredMs) {
        refreshRepository.save(Refresh.create(email, refresh, expiredMs));
    }

    public Boolean existsByRefresh(String refresh) {
        return refreshRepository.existsByRefresh(refresh);
    }

    @Transactional
    public void deleteByRefresh(String refresh) {
        refreshRepository.deleteByRefresh(refresh);
    }
}
