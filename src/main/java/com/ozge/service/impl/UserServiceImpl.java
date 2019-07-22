package com.ozge.service.impl;


import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ozge.configuration.properties.UserServiceProperties;
import com.ozge.model.request.Credentials;
import com.ozge.model.request.MerchantUserRequest;
import com.ozge.model.response.AuthToken;
import com.ozge.model.response.MerchantUserInfoResponse;
import com.ozge.service.UserService;
import com.ozge.util.HttpUtils;


@Service
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final RestTemplate restTemplate;

    private final String loginUrl;

    private final String infoUrl;

    @Autowired
    public UserServiceImpl(RestTemplate restTemplate, UserServiceProperties properties) {
        this.restTemplate = restTemplate;
        loginUrl = properties.getLogin().getUrl();
        infoUrl = properties.getInfo().getUrl();
    }


    @Override
    public Optional<AuthToken> login(Credentials credentials) {

        log.info("Login service was called -> {} - ( {} - {} )", loginUrl, credentials.getEmail(), credentials.getPassword());

        AuthToken authToken = restTemplate.exchange(loginUrl, HttpMethod.POST, new HttpEntity<>(credentials), AuthToken.class).getBody();

        return Optional.of(authToken);

    }

    @Override
    public Optional<MerchantUserInfoResponse> getMerchantUserInformation(MerchantUserRequest merchantUserRequest, String authToken) {

        log.info("Getting merchant user information service was called -> {} - ( id : {} - token : {})", infoUrl, merchantUserRequest.getId(), authToken);

        MerchantUserInfoResponse merchantUserInfoResponse = restTemplate.exchange(infoUrl, HttpMethod.POST, new HttpEntity<>(merchantUserRequest, HttpUtils.generateAuthorizationHeader(authToken)), MerchantUserInfoResponse.class).getBody();

        return Optional.of(merchantUserInfoResponse);

    }

}
