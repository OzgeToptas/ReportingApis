package com.ozge.util;

import org.springframework.http.HttpHeaders;

public final class HttpUtils {

    public HttpUtils() {
        throw new IllegalAccessError("Final Utility Class");
    }

    public static HttpHeaders generateAuthorizationHeader(String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authToken);

        return headers;
    }

}
