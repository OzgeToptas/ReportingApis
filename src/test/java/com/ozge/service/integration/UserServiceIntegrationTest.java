package com.ozge.service.integration;


import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.ozge.configuration.properties.UserServiceProperties;
import com.ozge.model.request.Credentials;
import com.ozge.model.request.MerchantUserRequest;
import com.ozge.model.response.AuthToken;
import com.ozge.model.response.MerchantUserInfoResponse;
import com.ozge.service.UserService;
import com.ozge.util.TestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceIntegrationTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserServiceProperties properties;

    private final String email = "demo@bumin.com.tr";
    private final String password = "cjaiU8CV";


    @Test
    public void loginWithValidCredentialsShouldReturnAuthorizationToken() throws Exception {
    	Credentials credentials = new Credentials();
        credentials.setEmail(email);
        credentials.setPassword(password);

        Optional<AuthToken> optional = userService.login(credentials);

        assertTrue("Fault [expected true]", optional.isPresent());
        assertNotNull("Fault [expected 'Authorization Token' not null]", optional.get());
    }

    @Test
    public void loginWithInvalidCredentialsShouldThrowInternalServerErrorException() throws Exception {
        final String email = "demo@demo.com";
        final String password = "lkj123asd";
        final String expectedExceptionMessage = "500 Internal Server Error";

        Credentials credentials = new Credentials();
        credentials.setEmail(email);
        credentials.setPassword(password);

        expectedException.expect(HttpServerErrorException.class);
        expectedException.expectMessage(expectedExceptionMessage);

        try {
            userService.login(credentials);
            fail("HttpServerErrorException must be thrown");
        } catch (Exception exp) {
            assertThat("Fault [expected 'Exception Message' asserts]",
                    exp.getMessage(),
                    is(expectedExceptionMessage));
            throw exp;
        }
    }

    @Test
    public void getMerchantUserInformationWithValidMerchantUserIdentifierAndAuthorizationTokenShouldReturnMerchantUserInformation() throws Exception {
        final String authToken = TestUtils.generateAuthorizationTokenWithValidCredentials(restTemplate, properties.getLogin().getUrl(), email, password);

        MerchantUserRequest merchantUserRequest = new MerchantUserRequest();
        merchantUserRequest.setId(53);

        Optional<MerchantUserInfoResponse> optional = userService.getMerchantUserInformation(merchantUserRequest, authToken);

        assertTrue("Fault [expected true]", optional.isPresent());
        assertEquals("Fault [expected 'Status' equals]",
                "APPROVED",
                optional.get().getStatus());
        assertNotNull("Fault [expected 'Merchant User Name' not null]",
                optional.get().getMerchantUser().getName());
        assertNotNull("Fault [expected 'Merchant User Id' not null]",
                optional.get().getMerchantUser().getId());
    }

    @Test
    public void getMerchantUserInformationWithInvalidTokenShouldThrowUnauthorizedException() throws Exception {
        final String authToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJtZXJjaGFudFVzZXJJZCI6NTMsInJvbGUiOiJhZG1pbiIsIm1lcmNoYW50SWQiOjMsInN1Yk1lcmNoYW50SWRzIjpbMyw3NCw5MywxMTkxLDExMSwxMzcsMTM4LDE0MiwxNDUsMTQ2LDE1MywzMzQsMTc1LDE4NCwyMjAsMjIxLDIyMiwyMjMsMjk0LDMyMiwzMjMsMzI3LDMyOSwzMzAsMzQ5LDM5MCwzOTEsNDU1LDQ1Niw0NzksNDg4LDU2MywxMTQ5LDU3MCwxMTM4LDExNTYsMTE1NywxMTU4LDExNzldLCJ0aW1lc3RhbXAiOjE1MDQxMDg3NzN9.Jt5JVXoEEkck4M9fbmDOaykhMpoq-x-D40rY-7Hv_fQ";
        final String expectedExceptionMessage = "401 Unauthorized";

        MerchantUserRequest merchantUserRequest = new MerchantUserRequest();
        merchantUserRequest.setId(53);

        expectedException.expect(HttpClientErrorException.class);
        expectedException.expectMessage(expectedExceptionMessage);

        try {
            userService.getMerchantUserInformation(merchantUserRequest, authToken);
            fail("HttpClientErrorException must be thrown");
        } catch (Exception exp) {
            assertThat("Fault [expected 'Exception Message' asserts]",
                    exp.getMessage(),
                    is(expectedExceptionMessage));
            throw exp;
        }
    }

    @Test
    public void getMerchantUserInformationWithInvalidMerchantUserIdentifierAndValidAuthorizationTokenShouldThrowInternalServerErrorException() throws Exception {
        final String authToken = TestUtils.generateAuthorizationTokenWithValidCredentials(restTemplate, properties.getLogin().getUrl(), email, password);
        final String expectedExceptionMessage = "500 Internal Server Error";

        MerchantUserRequest merchantUserRequest = new MerchantUserRequest();
        merchantUserRequest.setId(1);

        expectedException.expect(HttpServerErrorException.class);
        expectedException.expectMessage(expectedExceptionMessage);

        try {
            userService.getMerchantUserInformation(merchantUserRequest, authToken);
            fail("HttpServerErrorException must be thrown");
        } catch (Exception exp) {
            assertThat("Fault [expected 'Exception Message' asserts]",
                    exp.getMessage(),
                    is(expectedExceptionMessage));
            throw exp;
        }
    }

}
