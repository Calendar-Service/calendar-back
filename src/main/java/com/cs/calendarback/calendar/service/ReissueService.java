package com.cs.calendarback.calendar.service;

import com.cs.calendarback.calendar.entity.Refresh;
import com.cs.calendarback.calendar.entity.enums.Role;
import com.cs.calendarback.calendar.repository.RefreshRepository;
import com.cs.calendarback.jwt.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.cs.calendarback.jwt.CookieUtil.createCookie;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ReissueService {

    private final RefreshRepository refreshRepository;

    private final JWTUtil jwtUtil;

    @Transactional
    public ResponseEntity<?> reissueToken(HttpServletRequest request, HttpServletResponse response) {

        String refresh = extractRefreshToken(request);

        if (refresh == null) {
            //response status code
            return new ResponseEntity<>("refresh token null", HttpStatus.BAD_REQUEST);
        }

        //expired check
        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            //response status code
            return new ResponseEntity<>("refresh token expired", HttpStatus.BAD_REQUEST);
        }

        // 토큰이 refresh인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(refresh);
        if (!category.equals("refresh")) {
            //response status code
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        Boolean isExist = refreshRepository.existsByRefresh(refresh);
        System.out.println("토큰 존재 여부 : " + isExist);
        if (!isExist) {
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        String email = jwtUtil.getEmail(refresh);
        Role role = jwtUtil.getRole(refresh);

        //make new JWT
        String newAccess = jwtUtil.createJwt("access", email, role.name(), 1800000L);
        String newRefresh = jwtUtil.createJwt("refresh", email, role.name(), 86400000L);

        refreshRepository.deleteByRefresh(refresh);

        Refresh createRefresh  = Refresh.create(email,newRefresh, 86400000L);
        refreshRepository.save(createRefresh);

        //response
        response.setHeader("access", newAccess);
        response.addCookie(createCookie("refresh", newRefresh));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private String extractRefreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refresh".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
