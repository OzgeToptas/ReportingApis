package com.ozge.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.ozge.model.request.Credentials;
import com.ozge.model.response.AuthToken;

public class TestUtils {

    public static HttpHeaders generateAuthorizationHeader(String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authToken);

        return headers;
    }

    public static String generateAuthorizationTokenWithValidCredentials(RestTemplate restTemplate, String loginUrl, String email, String password) {
        Credentials credentials = new Credentials();
        credentials.setEmail(email);
        credentials.setPassword(password);
        return restTemplate.exchange(loginUrl, HttpMethod.POST, new HttpEntity<>(credentials), AuthToken.class).getBody().getToken();
    }

}
