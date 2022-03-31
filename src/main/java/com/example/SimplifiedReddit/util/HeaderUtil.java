package com.example.SimplifiedReddit.util;

import org.springframework.http.HttpHeaders;

public class HeaderUtil {
    public static String ERROR_MESSAGE_KEY = "X-SimplifiedReddit-error";

    public static HttpHeaders createError (String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(ERROR_MESSAGE_KEY, message);
        return headers;
    }
}
