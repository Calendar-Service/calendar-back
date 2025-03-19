package com.cs.calendarback.jwt;

import jakarta.servlet.http.Cookie;

public class CookieUtil {

    private CookieUtil() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24 * 60 * 60);
        // cookie.setSecure(true);
        // cookie.setPath("/");
        cookie.setHttpOnly(true);
        return cookie;
    }

}
